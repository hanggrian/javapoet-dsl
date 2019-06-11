package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec

/** Returns a java file with custom initialization block. */
inline fun buildJavaFile(packageName: String, builder: JavaFileBuilder.() -> Unit): JavaFile =
    JavaFileBuilder(packageName)
        .apply(builder)
        .build()

@SpecBuilderDslMarker
class JavaFileBuilder @PublishedApi internal constructor(private val packageName: String) : TypableSpecBuilder {
    private var type: TypeSpec? = null
    private var comments: MutableMap<String, Array<Any>>? = null
    private var staticImports: MutableList<Pair<Any, Array<String>>>? = null
    private var _skipJavaLangImports: Boolean? = null
    private var indent: Int? = null

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
    fun staticImports(type: Class<*>, vararg names: String) {
        if (staticImports == null) {
            staticImports = mutableListOf()
        }
        staticImports!! += type to arrayOf(*names)
    }

    /** Add static imports to this file. */
    inline fun <reified T> staticImports(vararg names: String) = staticImports(T::class.java, *names)

    var skipJavaLangImports: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            _skipJavaLangImports = value
        }

    override fun type(spec: TypeSpec) {
        type = spec
    }

    fun build(): JavaFile = JavaFile.builder(packageName, checkNotNull(type) { "A main type must be initialized" })
        .apply {
            comments?.forEach { (format, args) -> addFileComment(format, *args) }
            staticImports?.forEach { (type, names) ->
                when (type) {
                    is Enum<*> -> addStaticImport(type)
                    is ClassName -> addStaticImport(type, *names)
                    is Class<*> -> addStaticImport(type, *names)
                }
            }
            _skipJavaLangImports?.let { skipJavaLangImports(it) }
            indent?.let { indent(buildString { repeat(it) { append(' ') } }) }
        }
        .build()
}