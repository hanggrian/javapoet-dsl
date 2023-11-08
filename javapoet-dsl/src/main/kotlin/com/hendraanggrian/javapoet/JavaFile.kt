@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/**
 * Builds new [JavaFile] by populating newly created [JavaFileBuilder] using provided
 * [configuration].
 */
inline fun buildJavaFile(packageName: String, configuration: JavaFileBuilder.() -> Unit): JavaFile {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return JavaFileBuilder(packageName).apply(configuration).build()
}

/** Wrapper of [JavaFile.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetSpecDsl
class JavaFileBuilder(
    private val packageName: String,
) : TypeSpecHandler {
    private var comments: MutableList<Pair<String, Array<*>>> = mutableListOf()
    private var imports: MutableMap<Any, MutableSet<String>> = mutableMapOf()
    private var isSkipJavaLangImports: Boolean = false
    private var indentString: String = "  "

    override val types: MutableList<TypeSpec> = mutableListOf()

    fun comment(format: String, vararg args: Any) {
        comments += format to arrayOf(*args)
    }

    fun staticImport(constant: Enum<*>) {
        imports[constant] = mutableSetOf()
    }

    fun staticImport(type: ClassName, vararg names: String) {
        when (type) {
            in imports -> imports[type]!! += names
            else -> imports[type] = mutableSetOf(*names)
        }
    }

    fun staticImport(type: Class<*>, vararg names: String): Unit =
        staticImport(type.asClassName(), *names)

    fun staticImport(type: KClass<*>, vararg names: String): Unit =
        staticImport(type.asClassName(), *names)

    inline fun <reified T> staticImport(vararg names: String): Unit =
        staticImport(T::class.asClassName(), *names)

    var skipJavaLangImports: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            isSkipJavaLangImports = value
        }

    var indent: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            indentString = value
        }

    var indentSize: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            indent = buildString { repeat(value) { append(' ') } }
        }

    fun build(): JavaFile =
        JavaFile.builder(packageName, types.single())
            .apply {
                comments.forEach { (format, args) -> addFileComment(format, *args) }
                imports.forEach { (type, names) ->
                    when (type) {
                        is Enum<*> -> addStaticImport(type)
                        is ClassName -> addStaticImport(type, *names.toTypedArray())
                    }
                }
                skipJavaLangImports(isSkipJavaLangImports)
                indent(indentString)
            }
            .build()
}
