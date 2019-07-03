package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.invoke
import com.squareup.javapoet.CodeBlock

/** A [CodeContainer] is responsible for managing a set of code block instances. */
abstract class CodeContainer internal constructor() {

    /** Add code block to this container. */
    abstract fun add(format: String, vararg args: Any)

    /** Add code block to this container, returning the block added. */
    abstract fun add(block: CodeBlock): CodeBlock

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

    /** Starts the control flow. */
    abstract fun beginControlFlow(flow: String, vararg args: Any)

    /** Continues the control flow. */
    abstract fun nextControlFlow(flow: String, vararg args: Any)

    /** Stops the control flow. */
    abstract fun endControlFlow()

    /** Stops the control flow. */
    abstract fun endControlFlow(flow: String, vararg args: Any)

    /** Add code block with a new line to this container. */
    abstract fun addStatement(format: String, vararg args: Any)

    /** Add code block with a new line to this container, returning the block added. */
    abstract fun addStatement(block: CodeBlock): CodeBlock

    /** Add code block with custom initialization [builder] and a new line to this container, returning the block added. */
    inline fun addStatement(builder: CodeBlockBuilder.() -> Unit) = addStatement(CodeBlock.builder()(builder))

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: CodeContainerScope.() -> Unit) =
        CodeContainerScope(this).configuration()
}

/** Receiver for the `codes` block providing an extended set of operators for the configuration. */
class CodeContainerScope @PublishedApi internal constructor(private val container: CodeContainer) :
    CodeContainer() {

    override fun add(format: String, vararg args: Any) = container.add(format, *args)
    override fun add(block: CodeBlock): CodeBlock = container.add(block)
    override fun beginControlFlow(flow: String, vararg args: Any) = container.beginControlFlow(flow, *args)
    override fun nextControlFlow(flow: String, vararg args: Any) = container.nextControlFlow(flow, *args)
    override fun endControlFlow() = container.endControlFlow()
    override fun endControlFlow(flow: String, vararg args: Any) = container.endControlFlow(flow, *args)
    override fun addStatement(format: String, vararg args: Any) = container.addStatement(format, *args)
    override fun addStatement(block: CodeBlock): CodeBlock = container.addStatement(block)
}