package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec

/** Returns a java file with custom initialization block. */
inline fun buildJavaFile(packageName: String, builder: JavaFileBuilder.() -> Unit): JavaFile =
    JavaFileBuilder(packageName)
        .apply(builder)
        .build()

class JavaFileBuilder @PublishedApi internal constructor(private val packageName: String) : TypableSpecBuilder {
    private var type: TypeSpec? = null
    private val comments: MutableList<Pair<String, Array<Any>>> = mutableListOf()
    private val staticImports: MutableList<Pair<Any, Array<String>>> = mutableListOf()
    private var isSkipJavaLangImports: Boolean = false
    private var indent: Int = 2

    /** Add a comment to this file. */
    fun comment(format: String, vararg args: Any) {
        comments += format to arrayOf(*args)
    }

    /** Add a comment to this file. */
    var comment: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) = comment(value)

    /** Add static imports to this file. */
    fun staticImports(type: ClassName, vararg names: String) {
        staticImports += type to arrayOf(*names)
    }

    /** Add static imports to this file. */
    fun staticImports(type: Class<*>, vararg names: String) {
        staticImports += type to arrayOf(*names)
    }

    /** Add static imports to this file. */
    inline fun <reified T> staticImports(vararg names: String) = staticImports(T::class.java, *names)

    override fun type(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildTypeSpec(name, builder)
    }

    override fun type(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildTypeSpec(name, builder)
    }

    override fun interfaceType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildInterfaceTypeSpec(name, builder)
    }

    override fun interfaceType(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildInterfaceTypeSpec(name, builder)
    }

    override fun enumType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildEnumTypeSpec(name, builder)
    }

    override fun enumType(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildEnumTypeSpec(name, builder)
    }

    override fun anonymousType(typeArgumentsFormat: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildAnonymousTypeSpec(typeArgumentsFormat, *args, builder = builder)
    }

    override fun anonymousType(typeArguments: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildAnonymousTypeSpec(typeArguments, builder)
    }

    override fun annotationType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildAnnotationTypeSpec(name, builder)
    }

    override fun annotationType(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildAnnotationTypeSpec(name, builder)
    }

    fun build(): JavaFile = JavaFile.builder(packageName, checkNotNull(type) { "A `type` must be initialized" })
        .apply {
            comments.forEach { (format, args) -> addFileComment(format, *args) }
            staticImports.forEach { (type, names) ->
                when (type) {
                    is ClassName -> addStaticImport(type, *names)
                    is Class<*> -> addStaticImport(type, *names)
                }
            }
            skipJavaLangImports(isSkipJavaLangImports)
            indent(buildString {
                repeat(indent) {
                    append(" ")
                }
            })
        }
        .build()
}