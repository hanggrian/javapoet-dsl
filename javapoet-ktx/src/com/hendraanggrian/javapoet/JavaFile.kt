package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.collections.TypeSpecList
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import kotlin.reflect.KClass

/**
 * Builds new [JavaFile],
 * by populating newly created [JavaFileBuilder] using provided [builderAction] and then building it.
 */
inline fun buildJavaFile(
    packageName: String,
    builderAction: JavaFileBuilder.() -> Unit
): JavaFile = JavaFileBuilder(packageName).apply(builderAction).build()

/** Wrapper of [JavaFile.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDslMarker
class JavaFileBuilder(private val packageName: String) : TypeSpecList(ArrayList()) {
    private var comments: MutableList<Pair<String, Array<*>>>? = null
    private var imports: MutableMap<Any, MutableSet<String>>? = null
    private var isSkipJavaLangImports: Boolean? = null
    private var indentString: String? = null

    /** Add comment like [String.format]. */
    fun addComment(format: String, vararg args: Any) {
        if (comments == null) {
            comments = mutableListOf()
        }
        comments!! += format to arrayOf(*args)
    }

    /** Set comment with simple string, cancelling all changes made with [addComment]. */
    var comment: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            comments = mutableListOf(value to emptyArray<Any>())
        }

    /** Add static import. */
    fun addStaticImport(constant: Enum<*>) {
        if (imports == null) {
            imports = mutableMapOf()
        }
        imports!![constant] = mutableSetOf()
    }

    /** Add static import. */
    fun addStaticImport(type: ClassName, vararg names: String) {
        if (imports == null) {
            imports = mutableMapOf()
        }
        when (type) {
            in imports!! -> imports!![type]!! += names
            else -> imports!![type] = mutableSetOf(*names)
        }
    }

    /** Add static import. */
    fun addStaticImport(type: Class<*>, vararg names: String) {
        if (imports == null) {
            imports = mutableMapOf()
        }
        when (type) {
            in imports!! -> imports!![type]!! += names
            else -> imports!![type] = mutableSetOf(*names)
        }
    }

    /** Add static import. */
    fun addStaticImport(type: KClass<*>, vararg names: String): Unit = addStaticImport(type.java, *names)

    /** Add static import with reified function. */
    inline fun <reified T> addStaticImport(vararg names: String): Unit = addStaticImport(T::class.java, *names)

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
    inline var indentSize: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            indent = buildString { repeat(value) { append(' ') } }
        }

    /** Returns native spec. */
    fun build(): JavaFile {
        check(isNotEmpty()) { "No type found in this JavaFile" }
        check(size == 1) { "Only 1 type is expected in JavaFile" }
        return JavaFile.builder(packageName, first())
            .apply {
                comments?.forEach { (format, args) -> addFileComment(format, *args) }
                imports?.forEach { (type, names) ->
                    when (type) {
                        is Enum<*> -> addStaticImport(type)
                        is ClassName -> addStaticImport(type, *names.toTypedArray())
                        is Class<*> -> addStaticImport(type, *names.toTypedArray())
                    }
                }
                isSkipJavaLangImports?.let { skipJavaLangImports(it) }
                indentString?.let { indent(it) }
            }
            .build()
    }
}
