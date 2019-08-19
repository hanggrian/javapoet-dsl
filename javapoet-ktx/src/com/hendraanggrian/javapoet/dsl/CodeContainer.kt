package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.buildCode
import com.squareup.javapoet.CodeBlock

private interface CodeAppendable {

    /** Add code block to this container. */
    fun append(format: String, vararg args: Any)

    /** Add code block to this container, returning the block added. */
    fun append(block: CodeBlock): CodeBlock

    fun appendln()

    /** Add code block with a new line to this container. */
    fun appendln(format: String, vararg args: Any)

    /** Add code block with a new line to this container, returning the block added. */
    fun appendln(block: CodeBlock): CodeBlock
}

abstract class CodeCollection internal constructor() : CodeAppendable {

    /** Add code block with custom initialization [builderAction], returning the block added. */
    inline fun append(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        append(buildCode(builderAction))

    override fun appendln() =
        appendln("")

    /** Add code block with custom initialization [builderAction] and a new line to this container, returning the block added. */
    inline fun appendln(builderAction: CodeBlockBuilder.() -> Unit) =
        appendln(buildCode(builderAction))

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
abstract class JavadocContainer internal constructor() : CodeAppendable {

    /** Add code block with custom initialization [builderAction], returning the block added. */
    inline fun append(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        append(buildCode(builderAction))

    override fun appendln(): Unit =
        append("\n")

    override fun appendln(format: String, vararg args: Any) =
        append("$format\n", *args)

    override fun appendln(block: CodeBlock): CodeBlock {
        val result = append(block)
        appendln()
        return result
    }

    /** Add code block with custom initialization [builderAction] and a new line to this container, returning the block added. */
    inline fun appendln(builderAction: CodeBlockBuilder.() -> Unit) =
        appendln(buildCode(builderAction))

    /** Convenient method to add code block with operator function. */
    operator fun plusAssign(value: String) {
        append(value)
    }

    /** Convenient method to add code block with operator function. */
    operator fun plusAssign(block: CodeBlock) {
        append(block)
    }

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: JavadocContainerScope.() -> Unit) =
        JavadocContainerScope(this).configuration()
}

/** Receiver for the `javadoc` block providing an extended set of operators for the configuration. */
@JavapoetDslMarker
class JavadocContainerScope @PublishedApi internal constructor(private val container: JavadocContainer) :
    JavadocContainer(), CodeAppendable by container {

    override fun appendln(block: CodeBlock): CodeBlock = container.appendln(block)
    override fun appendln() = container.appendln()
    override fun appendln(format: String, vararg args: Any) = container.appendln(format, *args)
}
