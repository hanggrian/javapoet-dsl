package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.SpecMarker
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

abstract class CodeBlockCollection : CodeBlockAppendable {

    /** Add named code to this container. */
    abstract fun appendNamed(format: String, args: Map<String, *>)

    override fun appendLine(): Unit = appendLine("")

    /** Insert code flow with custom initialization [configuration]. */
    fun appendControlFlow(format: String, vararg args: Any, configuration: () -> Unit) {
        beginControlFlow(format, *args)
        configuration()
        endControlFlow()
    }

    /** Insert do/while code flow with custom initialization [configuration]. */
    fun appendControlFlow(
        startFormat: String,
        startArgs: Array<Any>,
        endFlow: String,
        endFormat: Array<Any>,
        configuration: () -> Unit
    ) {
        beginControlFlow(startFormat, *startArgs)
        configuration()
        endControlFlow(endFlow, *endFormat)
    }

    /**
     * Manually starts the control flow, as opposed to [appendControlFlow].
     * @see CodeBlock.Builder.beginControlFlow
     */
    abstract fun beginControlFlow(format: String, vararg args: Any)

    /**
     * Continues the control flow.
     * @see CodeBlock.Builder.nextControlFlow
     */
    abstract fun nextControlFlow(format: String, vararg args: Any)

    /**
     * Manually stops the control flow.
     * @see CodeBlock.Builder.endControlFlow
     */
    abstract fun endControlFlow()

    /**
     * Manually stops the control flow, only used in do/while.
     * @see CodeBlock.Builder.endControlFlow
     */
    abstract fun endControlFlow(format: String, vararg args: Any)
}

/** A [JavadocCollection] is responsible for managing a set of code instances. */
abstract class JavadocCollection : CodeBlockAppendable {

    override fun appendLine(): Unit = append(SystemProperties.LINE_SEPARATOR)

    override fun appendLine(format: String, vararg args: Any) {
        append(format, *args)
        appendLine()
    }

    override fun appendLine(code: CodeBlock) {
        append(code)
        appendLine()
    }

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

/** Receiver for the `kdoc` block providing an extended set of operators for the configuration. */
@SpecMarker
class JavadocCollectionScope internal constructor(private val handler: JavadocCollection) :
    JavadocCollection(),
    CodeBlockAppendable by handler {

    override fun appendLine(): Unit = handler.appendLine()
    override fun appendLine(code: CodeBlock): Unit = handler.appendLine(code)
    override fun appendLine(format: String, vararg args: Any): Unit = handler.appendLine(format, *args)
}
