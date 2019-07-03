@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.buildCodeBlock
import com.squareup.javapoet.CodeBlock

internal interface CodeCollection {

    fun add(format: String, vararg args: Any)

    fun add(block: CodeBlock): CodeBlock

    fun add(builder: CodeBlockBuilder.() -> Unit): CodeBlock = add(buildCodeBlock(builder))
}

/** A [CodeContainer] is responsible for managing a set of code instances. */
abstract class CodeContainer internal constructor() : CodeCollection {

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
class CodeContainerScope @PublishedApi internal constructor(collection: CodeCollection) :
    CodeContainer(), CodeCollection by collection