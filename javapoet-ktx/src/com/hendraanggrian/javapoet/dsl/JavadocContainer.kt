package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlocks
import com.squareup.javapoet.CodeBlock

internal interface JavadocCollection {

    /** Add code block to this container. */
    fun append(format: String, vararg args: Any)

    /** Add code block to this container, returning the block added. */
    fun append(block: CodeBlock): CodeBlock

    fun appendln(): Unit =
        append("\n")

    fun appendln(format: String, vararg args: Any) =
        append("$format\n", *args)

    fun appendln(block: CodeBlock): CodeBlock {
        val result = append(block)
        appendln()
        return result
    }
}

/** A [JavadocContainer] is responsible for managing a set of code instances. */
abstract class JavadocContainer internal constructor() : JavadocCollection {

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

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: JavadocContainerScope.() -> Unit) =
        JavadocContainerScope(this).configuration()
}

/** Receiver for the `javadoc` block providing an extended set of operators for the configuration. */
class JavadocContainerScope @PublishedApi internal constructor(collection: JavadocCollection) :
    JavadocContainer(), JavadocCollection by collection
