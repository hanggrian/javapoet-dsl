package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.buildCodeBlock
import com.squareup.javapoet.CodeBlock

private interface CodeBlockAppendable {

    /** Add code with arguments to this container. */
    fun append(format: String, vararg args: Any)

    /** Add code block to this container. */
    fun append(code: CodeBlock)

    /** Add empty new line to this container. */
    fun appendLine()

    /** Add code block with a new line to this container. */
    fun appendLine(format: String, vararg args: Any)

    /** Add code block with a new line to this container. */
    fun appendLine(code: CodeBlock)
}

abstract class CodeBlockContainer internal constructor() : CodeBlockAppendable {

    /** Add named code to this container. */
    abstract fun appendNamed(format: String, args: Map<String, *>)

    /** Add code block with custom initialization [builderAction]. */
    inline fun append(builderAction: CodeBlockBuilder.() -> Unit): Unit =
        append(buildCodeBlock(builderAction))

    override fun appendLine(): Unit = appendLine("")

    /** Add code block with custom initialization [builderAction] and a new line to this container. */
    inline fun appendLine(builderAction: CodeBlockBuilder.() -> Unit): Unit =
        appendLine(buildCodeBlock(builderAction))

    /** Insert code flow with custom initialization [configuration]. */
    fun appendFlow(flow: String, vararg args: Any, configuration: () -> Unit) {
        beginFlow(flow, *args)
        configuration()
        endFlow()
    }

    /** Insert do/while code flow with custom initialization [configuration]. */
    fun appendFlow(
        startFlow: String,
        startArgs: Array<Any>,
        endFlow: String,
        endArgs: Array<Any>,
        configuration: () -> Unit
    ) {
        beginFlow(startFlow, *startArgs)
        configuration()
        endFlow(endFlow, *endArgs)
    }

    /**
     * Continues the control flow.
     * @see CodeBlock.Builder.nextControlFlow
     */
    abstract fun nextFlow(flow: String, vararg args: Any)

    /**
     * Manually starts the control flow, as opposed to [appendFlow].
     * @see CodeBlock.Builder.beginControlFlow
     */
    internal abstract fun beginFlow(flow: String, vararg args: Any)

    /**
     * Manually stops the control flow.
     * @see CodeBlock.Builder.endControlFlow
     */
    internal abstract fun endFlow()

    /**
     * Manually stops the control flow, only used in do/while.
     * @see CodeBlock.Builder.endControlFlow
     */
    internal abstract fun endFlow(flow: String, vararg args: Any)
}

/** A [JavadocContainer] is responsible for managing a set of code instances. */
abstract class JavadocContainer internal constructor() : CodeBlockAppendable {

    /** Add code block with custom initialization [builderAction]. */
    inline fun append(builderAction: CodeBlockBuilder.() -> Unit): Unit =
        append(buildCodeBlock(builderAction))

    override fun appendLine(): Unit = append(SystemProperties.LINE_SEPARATOR)

    override fun appendLine(format: String, vararg args: Any) {
        append(format, *args)
        appendLine()
    }

    override fun appendLine(code: CodeBlock) {
        append(code)
        appendLine()
    }

    /** Add code block with custom initialization [builderAction] and a new line to this container. */
    inline fun appendLine(builderAction: CodeBlockBuilder.() -> Unit): Unit =
        appendLine(buildCodeBlock(builderAction))

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
class JavadocContainerScope(private val container: JavadocContainer) :
    JavadocContainer(),
    CodeBlockAppendable by container {

    override fun appendLine(): Unit = container.appendLine()
    override fun appendLine(code: CodeBlock): Unit = container.appendLine(code)
    override fun appendLine(format: String, vararg args: Any): Unit = container.appendLine(format, *args)
}
