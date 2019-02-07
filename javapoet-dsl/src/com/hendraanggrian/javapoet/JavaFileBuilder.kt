@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.Typable
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec

/** Returns a java file with custom initialization block. */
inline fun buildJavaFile(packageName: String, builder: JavaFileBuilder.() -> Unit): JavaFile =
    JavaFileBuilder(packageName)
        .apply(builder)
        .build()

class JavaFileBuilder(private val packageName: String) : Typable {
    private var type: TypeSpec? = null
    private val comments: MutableList<Pair<String, Array<Any>>> = mutableListOf()
    private val staticImports: MutableList<Pair<Any, Array<String>>> = mutableListOf()
    private var isSkipJavaLangImports: Boolean = false
    private var indent: Int = 2

    fun comment(format: String, vararg args: Any) {
        comments += format to arrayOf(*args)
    }

    fun staticImport(type: ClassName, vararg names: String) {
        staticImports += type to arrayOf(*names)
    }

    fun staticImport(type: Class<*>, vararg names: String) {
        staticImports += type to arrayOf(*names)
    }

    inline fun <reified T> staticImport(vararg names: String) = staticImport(T::class.java, *names)

    override fun type(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildTypeSpec(name, builder)
    }

    override fun type(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildTypeSpec(className, builder)
    }

    override fun interfaceType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildInterfaceTypeSpec(name, builder)
    }

    override fun interfaceType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildInterfaceTypeSpec(className, builder)
    }

    override fun enumType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildEnumTypeSpec(name, builder)
    }

    override fun enumType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildEnumTypeSpec(className, builder)
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

    override fun annotationType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        type = buildAnnotationTypeSpec(className, builder)
    }

    fun build(): JavaFile = JavaFile.builder(packageName, checkNotNull(type) { "A type must be initialized" })
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