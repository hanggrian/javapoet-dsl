@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.TypedSpecBuilder
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec

/** Returns a java file with custom initialization block. */
inline fun buildJavaFile(packageName: String, builder: JavaFileBuilder.() -> Unit): JavaFile =
    JavaFileBuilder(packageName)
        .apply(builder)
        .build()

class JavaFileBuilder(private val packageName: String) : TypedSpecBuilder {
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

    override fun `class`(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildTypeSpec(name, builder)
    }

    override fun `class`(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildTypeSpec(className, builder)
    }

    override fun `interface`(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildInterfaceTypeSpec(name, builder)
    }

    override fun `interface`(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildInterfaceTypeSpec(className, builder)
    }

    override fun `enum`(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildEnumTypeSpec(name, builder)
    }

    override fun `enum`(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildEnumTypeSpec(className, builder)
    }

    override fun anonymousClass(typeArgumentsFormat: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildAnonymousTypeSpec(typeArgumentsFormat, *args, builder = builder)
    }

    override fun anonymousClass(typeArguments: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildAnonymousTypeSpec(typeArguments, builder)
    }

    override fun annotationInterface(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildAnnotationTypeSpec(name, builder)
    }

    override fun annotationInterface(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildAnnotationTypeSpec(className, builder)
    }

    fun build(): JavaFile = JavaFile.builder(packageName, checkNotNull(type) { "A `class` must be initialized" })
        .apply {
            comments.forEach { (format, args) ->
                addFileComment(format, *args)
            }
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