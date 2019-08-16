package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.buildCode
import com.squareup.javapoet.CodeBlock

abstract class CodeCollection internal constructor() {

    /** Add code block to this container. */
    abstract fun append(format: String, vararg args: Any)

    /** Add code block to this container, returning the block added. */
    abstract fun append(block: CodeBlock): CodeBlock

    /** Add code block with custom initialization [builderAction], returning the block added. */
    inline fun append(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        append(buildCode(builderAction))

    /** Starts the control flow. */
    abstract fun beginControlFlow(flow: String, vararg args: Any)

    /** Continues the control flow. */
    abstract fun nextControlFlow(flow: String, vararg args: Any)

    /** Stops the control flow. */
    abstract fun endControlFlow()

    /** Stops the control flow. */
    abstract fun endControlFlow(flow: String, vararg args: Any)

    /** Add code block with a new line to this container. */
    abstract fun appendln(format: String, vararg args: Any)

    /** Add code block with a new line to this container, returning the block added. */
    abstract fun appendln(block: CodeBlock): CodeBlock

    /** Add code block with custom initialization [builderAction] and a new line to this container, returning the block added. */
    inline fun appendln(builderAction: CodeBlockBuilder.() -> Unit) =
        appendln(buildCode(builderAction))
}

sealed class JavadocCollection {

    /** Add code block to this container. */
    abstract fun append(format: String, vararg args: Any)

    /** Add code block to this container, returning the block added. */
    abstract fun append(block: CodeBlock): CodeBlock

    /** Add code block with custom initialization [builderAction], returning the block added. */
    inline fun append(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        append(buildCode(builderAction))

    fun appendln(): Unit =
        append("\n")

    fun appendln(format: String, vararg args: Any) =
        append("$format\n", *args)

    fun appendln(block: CodeBlock): CodeBlock {
        val result = append(block)
        appendln()
        return result
    }

    /** Add code block with custom initialization [builderAction], returning the block added. */
    inline fun appendln(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        appendln(buildCode(builderAction))
}

/** A [JavadocContainer] is responsible for managing a set of code instances. */
abstract class JavadocContainer internal constructor() : JavadocCollection() {

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
class JavadocContainerScope @PublishedApi internal constructor(private val collection: JavadocCollection) :
    JavadocContainer() {

    override fun append(format: String, vararg args: Any) = collection.append(format, *args)

    override fun append(block: CodeBlock): CodeBlock = collection.append(block)
}
