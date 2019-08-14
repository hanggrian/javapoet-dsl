package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.invoke
import com.squareup.javapoet.CodeBlock

internal interface JavadocCollection {

    /** Add code block to this container. */
    fun add(format: String, vararg args: Any)

    /** Add code block to this container, returning the block added. */
    fun add(block: CodeBlock): CodeBlock
}

/** A [JavadocContainer] is responsible for managing a set of code instances. */
abstract class JavadocContainer internal constructor() : JavadocCollection {

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

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: JavadocContainerScope.() -> Unit) =
        JavadocContainerScope(this).configuration()
}

/** Receiver for the `javadoc` block providing an extended set of operators for the configuration. */
class JavadocContainerScope @PublishedApi internal constructor(collection: JavadocCollection) :
    JavadocContainer(), JavadocCollection by collection
