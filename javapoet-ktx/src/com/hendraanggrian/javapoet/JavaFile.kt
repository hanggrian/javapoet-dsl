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
    private var comments: MutableMap<String, Array<Any>>? = null
    private var staticImports: MutableMap<Any, Array<String>>? = null
    private var isSkipJavaLangImports: Boolean? = null
    private var indentString: String? = null

    override fun add(spec: TypeSpec) {
        check(typeSpec == null) { "Java file may only have 1 type" }
        typeSpec = spec
    }

    /** Add comment like [String.format]. */
    fun addComment(format: String, vararg args: Any) {
        if (comments == null) {
            comments = mutableMapOf()
        }
        comments!![format] = arrayOf(*args)
    }

    /** Set comment with simple string, cancelling all changes made with [addComment]. */
    var comment: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            comments = mutableMapOf(value to emptyArray())
        }

    /** Add static import. */
    fun addStaticImport(constant: Enum<*>) {
        if (staticImports == null) {
            staticImports = mutableMapOf()
        }
        staticImports!![constant] = emptyArray()
    }

    /** Add static import. */
    fun addStaticImport(type: ClassName, vararg names: String) {
        if (staticImports == null) {
            staticImports = mutableMapOf()
        }
        staticImports!![type] = arrayOf(*names)
    }

    /** Add static import. */
    fun addStaticImport(type: Class<*>, vararg names: String) {
        if (staticImports == null) {
            staticImports = mutableMapOf()
        }
        staticImports!![type] = arrayOf(*names)
    }

    /** Add static import. */
    fun addStaticImport(type: KClass<*>, vararg names: String) =
        addStaticImport(type.java, *names)

    /** Add static import with reified function. */
    inline fun <reified T> addStaticImport(vararg names: String) =
        addStaticImport(T::class, *names)

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

    /** Set indent space count. */
    inline var indentCount: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            indent = buildString { repeat(value) { append(' ') } }
        }

    /** Returns native spec. */
    fun build(): JavaFile = JavaFile.builder(packageName, checkNotNull(typeSpec) { "A main type must be initialized" })
        .apply {
            comments?.forEach { (format, args) -> addFileComment(format, *args) }
            staticImports?.forEach { (type, names) ->
                when (type) {
                    is Enum<*> -> addStaticImport(type)
                    is ClassName -> addStaticImport(type, *names)
                    is Class<*> -> addStaticImport(type, *names)
                }
            }
            isSkipJavaLangImports?.let { skipJavaLangImports(it) }
            indentString?.let { indent(it) }
        }
        .build()
}
