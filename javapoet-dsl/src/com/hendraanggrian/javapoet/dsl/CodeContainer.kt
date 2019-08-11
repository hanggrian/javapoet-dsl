package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.invoke
import com.squareup.javapoet.CodeBlock

internal interface CodeCollection {

    /** Add code block to this container. */
    fun add(format: String, vararg args: Any)

    /** Add code block to this container, returning the block added. */
    fun add(block: CodeBlock): CodeBlock

    /** Starts the control flow. */
    fun beginControlFlow(flow: String, vararg args: Any)

    /** Continues the control flow. */
    fun nextControlFlow(flow: String, vararg args: Any)

    /** Stops the control flow. */
    fun endControlFlow()

    /** Stops the control flow. */
    fun endControlFlow(flow: String, vararg args: Any)

    /** Add code block with a new line to this container. */
    fun addStatement(format: String, vararg args: Any)

    /** Add code block with a new line to this container, returning the block added. */
    fun addStatement(block: CodeBlock): CodeBlock
}

/** A [CodeContainer] is responsible for managing a set of code block instances. */
abstract class CodeContainer internal constructor() : CodeCollection {

    /** Add code block with custom initialization [builder], returning the block added. */
    inline fun add(builder: CodeBlockBuilder.() -> Unit): CodeBlock =
        add(CodeBlock.builder()(builder))

    /** Convenient method to add code block with operator function. */
    operator fun plusAssign(value: String) {
        add(value)
    }

    /** Convenient method to add code block with operator function. */
    operator fun plusAssign(block: CodeBlock) {
        add(block)
    }

    /** Add code block with custom initialization [builder] and a new line to this container, returning the block added. */
    inline fun addStatement(builder: CodeBlockBuilder.() -> Unit) = addStatement(CodeBlock.builder()(builder))

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: CodeContainerScope.() -> Unit) =
        CodeContainerScope(this).configuration()
}

/** Receiver for the `codes` block providing an extended set of operators for the configuration. */
class CodeContainerScope @PublishedApi internal constructor(collection: CodeCollection) :
    CodeContainer(), CodeCollection by collection
