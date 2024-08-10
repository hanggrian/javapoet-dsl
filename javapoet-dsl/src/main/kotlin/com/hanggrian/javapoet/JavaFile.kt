@file:OptIn(ExperimentalContracts::class)

package com.hanggrian.javapoet

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
public fun buildJavaFile(
    packageName: String,
    configuration: JavaFileBuilder.() -> Unit,
): JavaFile {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return JavaFileBuilder(packageName)
        .apply(configuration)
        .build()
}

/** Wrapper of [JavaFile.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDsl
public class JavaFileBuilder(private val packageName: String) {
    private var comments: MutableList<Pair<String, Array<*>>> = mutableListOf()
    private var imports: MutableMap<Any, MutableSet<String>> = mutableMapOf()
    private var skipJavaLangImports: Boolean = false
    private var indentString: String = "  "

    public val typeSpecs: MutableList<TypeSpec> = mutableListOf()

    public val types: TypeSpecHandler =
        object : TypeSpecHandler {
            override fun add(type: TypeSpec) {
                typeSpecs.add(type)
            }
        }

    /** Invokes DSL to configure [TypeSpec] collection. */
    public fun types(configuration: TypeSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        TypeSpecHandlerScope
            .of(types)
            .configuration()
    }

    public fun addComment(format: String, vararg args: Any) {
        comments += format to arrayOf(*args)
    }

    public fun addStaticImport(constant: Enum<*>) {
        imports[constant] = mutableSetOf()
    }

    public fun addStaticImport(type: ClassName, vararg names: String) {
        when (type) {
            in imports -> imports[type]!! += names
            else -> imports[type] = mutableSetOf(*names)
        }
    }

    public fun addStaticImport(type: KClass<*>, vararg names: String): Unit =
        addStaticImport(type.name, *names)

    public inline fun <reified T> addStaticImport(vararg names: String): Unit =
        addStaticImport(T::class.name, *names)

    public var isSkipJavaLangImports: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            skipJavaLangImports = value
        }

    public var indent: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            indentString = value
        }

    public var indentSize: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            indent = buildString { repeat(value) { append(' ') } }
        }

    public fun build(): JavaFile =
        JavaFile
            .builder(packageName, typeSpecs.single())
            .apply {
                comments.forEach { (format, args) -> addFileComment(format, *args) }
                imports.forEach { (type, names) ->
                    when (type) {
                        is Enum<*> -> addStaticImport(type)
                        is ClassName -> addStaticImport(type, *names.toTypedArray())
                    }
                }
                skipJavaLangImports(skipJavaLangImports)
                indent(indentString)
            }.build()
}
