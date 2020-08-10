package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.buildCodeBlock
import com.squareup.javapoet.CodeBlock

private interface CodeBlockAppendable {

    /** Add code with arguments to this container. */
    fun append(format: String, vararg args: Any)

    /** Add code block to this container. */
    fun append(code: CodeBlock)

    /** Add empty new line to this container. */
    fun appendln()

    /** Add code block with a new line to this container. */
    fun appendln(format: String, vararg args: Any)

    /** Add code block with a new line to this container. */
    fun appendln(code: CodeBlock)
}

abstract class CodeBlockContainer internal constructor() : CodeBlockAppendable {

    /** Add named code to this container. */
    abstract fun appendNamed(format: String, args: Map<String, *>)

    /** Add code block with custom initialization [builderAction]. */
    inline fun append(builderAction: CodeBlockBuilder.() -> Unit): Unit = append(buildCodeBlock(builderAction))

    override fun appendln(): Unit = appendln("")

    /** Add code block with custom initialization [builderAction] and a new line to this container. */
    inline fun appendln(builderAction: CodeBlockBuilder.() -> Unit): Unit = appendln(buildCodeBlock(builderAction))

    /** Starts the control flow. */
    abstract fun beginFlow(flow: String, vararg args: Any)

    /** Continues the control flow. */
    abstract fun nextFlow(flow: String, vararg args: Any)

    /** Stops the control flow. */
    abstract fun endFlow()

    /** Stops the control flow. */
    abstract fun endFlow(flow: String, vararg args: Any)
}

/** A [JavadocContainer] is responsible for managing a set of code instances. */
abstract class JavadocContainer internal constructor() : CodeBlockAppendable {

    /** Add code block with custom initialization [builderAction]. */
    inline fun append(builderAction: CodeBlockBuilder.() -> Unit): Unit = append(buildCodeBlock(builderAction))

    override fun appendln(): Unit = append(SystemProperties.LINE_SEPARATOR)

    override fun appendln(format: String, vararg args: Any) {
        append(format, *args)
        appendln()
    }

    override fun appendln(code: CodeBlock) {
        append(code)
        appendln()
    }

    /** Add code block with custom initialization [builderAction] and a new line to this container. */
    inline fun appendln(builderAction: CodeBlockBuilder.() -> Unit): Unit = appendln(buildCodeBlock(builderAction))

    /** Convenient method to add code block with operator function. */
    operator fun plusAssign(value: String): Unit = append(value)

    /** Convenient method to add code block with operator function. */
    operator fun plusAssign(code: CodeBlock): Unit = append(code)

    /**
     * @see kotlin.text.SystemProperties
     */
    private object SystemProperties {
        /** Line separator for current system. */
        @JvmField
        val LINE_SEPARATOR = System.getProperty("line.separator")!!
    }
}

/** Receiver for the `javadoc` function type providing an extended set of operators for the configuration. */
@JavapoetDslMarker
class JavadocContainerScope(private val container: JavadocContainer) :
    JavadocContainer(),
    CodeBlockAppendable by container {

    override fun appendln(): Unit = container.appendln()
    override fun appendln(code: CodeBlock): Unit = container.appendln(code)
    override fun appendln(format: String, vararg args: Any): Unit = container.appendln(format, *args)
}
