@file:JvmMultifileClass
@file:JvmName("SpecBuildersKt")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.TypeContainer
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import kotlin.reflect.KClass

/** Creates [JavaFile] with DSL. */
inline fun buildJavaFile(packageName: String, builder: JavaFileBuilder.() -> Unit): JavaFile =
    JavaFileBuilder(packageName)
        .apply(builder)
        .build()

/** Wrapper of [JavaFile.Builder], providing DSL support as a replacement to Java builder. */
class JavaFileBuilder @PublishedApi internal constructor(private val packageName: String) : TypeContainer() {

    private var _spec: TypeSpec? = null
    private var _comments: Map<String, Array<Any>>? = null
    private var _staticImports: MutableList<Pair<Any, Array<String>>>? = null
    private var _skipJavaLangImports: Boolean? = null
    private var _indent: String? = null

    override fun add(spec: TypeSpec): TypeSpec = spec.also {
        check(_spec == null) { "Java file may only have 1 type" }
        _spec = it
    }

    /** Add a comment to this file. */
    fun addComment(format: String, vararg args: Any) {
        if (_comments !is MutableMap) {
            _comments = mutableMapOf()
        }
        _comments as MutableMap += format to arrayOf(*args)
    }

    /** Set a comment to this file, cancelling all changes made with [addComment]. */
    var comment: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            _comments = mapOf(value to emptyArray())
        }

    /** Add static imports to this file. */
    fun addStaticImports(constant: Enum<*>) {
        if (_staticImports == null) {
            _staticImports = mutableListOf()
        }
        _staticImports!! += constant to emptyArray()
    }

    /** Add static imports to this file. */
    fun addStaticImports(type: ClassName, vararg names: String) {
        if (_staticImports == null) {
            _staticImports = mutableListOf()
        }
        _staticImports!! += type to arrayOf(*names)
    }

    /** Add static imports to this file. */
    fun addStaticImports(type: KClass<*>, vararg names: String) {
        if (_staticImports == null) {
            _staticImports = mutableListOf()
        }
        _staticImports!! += type to arrayOf(*names)
    }

    /** Add static imports to this file. */
    inline fun <reified T> addStaticImports(vararg names: String) = addStaticImports(T::class, *names)

    var skipJavaLangImports: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            _skipJavaLangImports = value
        }

    var indent: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            _indent = value
        }

    inline var indentCount: Int
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            indent = buildString { repeat(value) { append(' ') } }
        }

    fun build(): JavaFile = JavaFile.builder(packageName, checkNotNull(_spec) { "A main type must be initialized" })
        .apply {
            _comments?.forEach { (format, args) -> addFileComment(format, *args) }
            _staticImports?.forEach { (type, names) ->
                when (type) {
                    is Enum<*> -> addStaticImport(type)
                    is ClassName -> addStaticImport(type, *names)
                    is KClass<*> -> addStaticImport(type.java, *names)
                }
            }
            _skipJavaLangImports?.let { skipJavaLangImports(it) }
            _indent?.let { indent(it) }
        }
        .build()
}
