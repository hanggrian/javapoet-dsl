package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.TypeContainerDelegate
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import kotlin.reflect.KClass

/** Returns a java file with custom initialization block. */
inline fun buildJavaFile(packageName: String, builder: JavaFileBuilder.() -> Unit): JavaFile =
    JavaFileBuilder(packageName)
        .apply(builder)
        .build()

@JavapoetDslMarker
class JavaFileBuilder @PublishedApi internal constructor(private val packageName: String) :
    TypeContainerDelegate(), TypedSpecBuilder {

    private var type: TypeSpec? = null
    private var comments: MutableMap<String, Array<Any>>? = null
    private var staticImports: MutableList<Pair<Any, Array<String>>>? = null
    private var _skipJavaLangImports: Boolean? = null
    private var indent: Int? = null

    override fun add(spec: TypeSpec): TypeSpec = spec.also {
        check(type == null) { "Java file may only have 1 type" }
        type = it
    }

    /** Add a comment to this file. */
    fun comment(format: String, vararg args: Any) {
        if (comments == null) {
            comments = mutableMapOf()
        }
        comments!! += format to arrayOf(*args)
    }

    /** Set a comment to this file, cancelling all changes made with [comment]. */
    var comment: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            comments = mutableMapOf(value to emptyArray())
        }

    /** Add static imports to this file. */
    fun staticImports(constant: Enum<*>) {
        if (staticImports == null) {
            staticImports = mutableListOf()
        }
        staticImports!! += constant to emptyArray()
    }

    /** Add static imports to this file. */
    fun staticImports(type: ClassName, vararg names: String) {
        if (staticImports == null) {
            staticImports = mutableListOf()
        }
        staticImports!! += type to arrayOf(*names)
    }

    /** Add static imports to this file. */
    fun staticImports(type: KClass<*>, vararg names: String) {
        if (staticImports == null) {
            staticImports = mutableListOf()
        }
        staticImports!! += type to arrayOf(*names)
    }

    /** Add static imports to this file. */
    inline fun <reified T> staticImports(vararg names: String) = staticImports(T::class, *names)

    var skipJavaLangImports: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            _skipJavaLangImports = value
        }

    fun build(): JavaFile = JavaFile.builder(packageName, checkNotNull(type) { "A main type must be initialized" })
        .apply {
            comments?.forEach { (format, args) -> addFileComment(format, *args) }
            staticImports?.forEach { (type, names) ->
                when (type) {
                    is Enum<*> -> addStaticImport(type)
                    is ClassName -> addStaticImport(type, *names)
                    is KClass<*> -> addStaticImport(type.java, *names)
                }
            }
            _skipJavaLangImports?.let { skipJavaLangImports(it) }
            indent?.let { indent(buildString { repeat(it) { append(' ') } }) }
        }
        .build()
}