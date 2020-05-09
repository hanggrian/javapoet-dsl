package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.TypeSpecContainer
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import kotlin.reflect.KClass

/**
 * Builds a new [JavaFile],
 * by populating newly created [JavaFileBuilder] using provided [builderAction] and then building it.
 */
inline fun buildJavaFile(packageName: String, builderAction: JavaFileBuilder.() -> Unit): JavaFile =
    JavaFileBuilder(packageName).apply(builderAction).build()

/** Wrapper of [JavaFile.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDslMarker
class JavaFileBuilder @PublishedApi internal constructor(private val packageName: String) : TypeSpecContainer() {
    private var typeSpec: TypeSpec? = null
    private var comments: MutableList<Pair<String, Array<*>>>? = null
    private var imports: MutableMap<Any, MutableSet<String>>? = null
    private var isSkipJavaLangImports: Boolean? = null
    private var indentString: String? = null

    override fun addAll(specs: Iterable<TypeSpec>): Boolean =
        throw UnsupportedOperationException("Java file may only have single type.")

    override fun add(spec: TypeSpec) {
        check(typeSpec == null) { "Java file may only have single type." }
        typeSpec = spec
    }

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
    fun addStaticImport(type: KClass<*>, vararg names: String) = addStaticImport(type.java, *names)

    /** Add static import with reified function. */
    inline fun <reified T> addStaticImport(vararg names: String) = addStaticImport(T::class, *names)

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
    fun build(): JavaFile = JavaFile.builder(packageName, checkNotNull(typeSpec) { "A main type must be initialized" })
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
