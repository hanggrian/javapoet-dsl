@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.TypeSpecManager
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec

/** Returns a java file with custom initialization block. */
inline fun buildJavaFile(packageName: String, builder: JavaFileBuilder.() -> Unit): JavaFile =
    JavaFileBuilderImpl(packageName)
        .apply(builder)
        .build()

interface JavaFileBuilder : TypeSpecManager {

    val packageName: String

    var type: TypeSpec?

    val comments: MutableList<Pair<String, Array<Any>>>

    val staticImports: MutableList<Pair<Any, Array<String>>>

    var isSkipJavaLangImports: Boolean

    var indent: Int

    fun comment(format: String, vararg args: Any) {
        comments += format to arrayOf(*args)
    }

    fun staticImport(type: ClassName, vararg names: String) {
        staticImports += type to arrayOf(*names)
    }

    fun staticImport(type: Class<*>, vararg names: String) {
        staticImports += type to arrayOf(*names)
    }

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

@PublishedApi
internal class JavaFileBuilderImpl(override val packageName: String) : JavaFileBuilder {

    override var type: TypeSpec? = null

    override val comments: MutableList<Pair<String, Array<Any>>> = mutableListOf()

    override val staticImports: MutableList<Pair<Any, Array<String>>> = mutableListOf()

    override var isSkipJavaLangImports: Boolean = false

    override var indent: Int = 2
}