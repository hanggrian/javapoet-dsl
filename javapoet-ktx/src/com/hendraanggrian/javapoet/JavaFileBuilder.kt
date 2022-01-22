package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.collections.TypeSpecList
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import kotlin.reflect.KClass

/**
 * Builds new [JavaFile],
 * by populating newly created [JavaFileBuilder] using provided [configuration].
 */
fun buildJavaFile(packageName: String, configuration: JavaFileBuilder.() -> Unit): JavaFile =
    JavaFileBuilder(packageName).apply(configuration).build()

/** Wrapper of [JavaFile.Builder], providing DSL support as a replacement to Java builder. */
@SpecMarker
class JavaFileBuilder internal constructor(private val packageName: String) : TypeSpecList(ArrayList()) {
    private var comments: MutableList<Pair<String, Array<*>>> = mutableListOf()
    private var imports: MutableMap<Any, MutableSet<String>> = mutableMapOf()
    private var isSkipJavaLangImports: Boolean = false
    private var indentString: String = "  "

    /** Add comment like [String.format]. */
    fun addComment(format: String, vararg args: Any) {
        comments += format to arrayOf(*args)
    }

    /** Set comment with simple string, cancelling all changes made with [addComment]. */
    var comment: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            comments = mutableListOf(value to emptyArray<Any>())
        }

    /** Add static import from [Enum]. */
    fun addStaticImport(constant: Enum<*>) {
        imports[constant] = mutableSetOf()
    }

    /** Add static import from [ClassName]. */
    fun addStaticImport(type: ClassName, vararg names: String) {
        when (type) {
            in imports -> imports[type]!! += names
            else -> imports[type] = mutableSetOf(*names)
        }
    }

    /** Add static import from [Class]. */
    fun addStaticImport(type: Class<*>, vararg names: String): Unit = addStaticImport(type.asClassName(), *names)

    /** Add static import from [KClass]. */
    fun addStaticImport(type: KClass<*>, vararg names: String): Unit = addStaticImport(type.asClassName(), *names)

    /** Add static import with reified [T]. */
    inline fun <reified T> addStaticImport(vararg names: String): Unit = addStaticImport(T::class.asClassName(), *names)

    /** Set to true to skip java imports. */
    var skipJavaLangImports: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            isSkipJavaLangImports = value
        }

    /** Set indent text. */
    var indent: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            indentString = value
        }

    /** Convenient method to set [indent] with space the length of [indentSize]. */
    var indentSize: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            indent = buildString { repeat(value) { append(' ') } }
        }

    /** Returns native spec. */
    fun build(): JavaFile {
        check(size == 1) { "JavaFile must have exactly 1 type." }
        return JavaFile.builder(packageName, first())
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
}
