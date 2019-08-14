package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlocks
import com.squareup.javapoet.CodeBlock

internal interface CodeCollection {

    /** Add code block to this container. */
    fun append(format: String, vararg args: Any)

    /** Add code block to this container, returning the block added. */
    fun append(block: CodeBlock): CodeBlock

    /** Starts the control flow. */
    fun beginControlFlow(flow: String, vararg args: Any)

    /** Continues the control flow. */
    fun nextControlFlow(flow: String, vararg args: Any)

    /** Stops the control flow. */
    fun endControlFlow()

    /** Stops the control flow. */
    fun endControlFlow(flow: String, vararg args: Any)

    /** Add code block with a new line to this container. */
    fun appendln(format: String, vararg args: Any)

    /** Add code block with a new line to this container, returning the block added. */
    fun appendln(block: CodeBlock): CodeBlock
}

/** A [CodeContainer] is responsible for managing a set of code block instances. */
abstract class CodeContainer internal constructor() : CodeCollection {

    /** Add code block with custom initialization [builderAction], returning the block added. */
    inline fun append(builderAction: CodeBlocks.Builder.() -> Unit): CodeBlock =
        append(CodeBlocks.of(builderAction))

    /** Convenient method to add code block with operator function. */
    operator fun plusAssign(value: String) {
        append(value)
    }

    /** Convenient method to add code block with operator function. */
    operator fun plusAssign(block: CodeBlock) {
        append(block)
    }

    /** Add code block with custom initialization [builderAction] and a new line to this container, returning the block added. */
    inline fun addStatement(builderAction: CodeBlocks.Builder.() -> Unit) =
        appendln(CodeBlocks.of(builderAction))

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: CodeContainerScope.() -> Unit) =
        CodeContainerScope(this).configuration()
}

/** Receiver for the `codes` block providing an extended set of operators for the configuration. */
class CodeContainerScope @PublishedApi internal constructor(collection: CodeCollection) :
    CodeContainer(), CodeCollection by collection
