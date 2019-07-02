@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.squareup.javapoet.CodeBlock

/** An [CodeContainer] is responsible for managing a set of code instances. */
abstract class CodeContainer internal constructor() {

    abstract fun add(format: String, vararg args: Any)

    abstract fun add(block: CodeBlock): CodeBlock

    fun add(builder: CodeBlockBuilder.() -> Unit): CodeBlock = add(CodeBlockBuilder.of(builder))

    inline operator fun plusAssign(value: String) {
        add(value)
    }

    inline operator fun plusAssign(block: CodeBlock) {
        add(block)
    }

    inline operator fun invoke(configuration: CodeContainerScope.() -> Unit) =
        CodeContainerScope(this).configuration()
}

/**
 * Receiver for the `codes`, `statements`, and `javadoc` block providing an extended set of operators for the
 * configuration.
 */
class CodeContainerScope @PublishedApi internal constructor(private val container: CodeContainer) :
    CodeContainer() {

    override fun add(format: String, vararg args: Any) = container.add(format, *args)

    override fun add(block: CodeBlock): CodeBlock = container.add(block)
}