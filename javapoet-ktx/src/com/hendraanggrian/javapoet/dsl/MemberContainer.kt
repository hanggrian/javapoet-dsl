package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlocks
import com.squareup.javapoet.CodeBlock

internal interface MemberCollection {

    /** Add member from [name] and code block to this container. */
    fun add(name: String, format: String, vararg args: Any)

    /** Add member from [name] and [block] to this container, returning the member added. */
    fun add(name: String, block: CodeBlock): CodeBlock
}

/** A [MemberContainer] is responsible for managing a set of member instances. */
abstract class MemberContainer internal constructor() : MemberCollection {

    /** Add member with [name] and custom initialization [builder], returning the member added. */
    inline fun add(name: String, builderAction: CodeBlocks.Builder.() -> Unit): CodeBlock =
        add(name, CodeBlocks.of(builderAction))

    /** Convenient method to add member with operator function. */
    operator fun set(name: String, format: String) {
        add(name, format)
    }

    /** Convenient method to add member with operator function. */
    operator fun set(name: String, block: CodeBlock) {
        add(name, block)
    }

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: MemberContainerScope.() -> Unit) =
        MemberContainerScope(this).configuration()
}

/** Receiver for the `members` block providing an extended set of operators for the configuration. */
class MemberContainerScope @PublishedApi internal constructor(collection: MemberCollection) :
    MemberContainer(), MemberCollection by collection {

    /** Convenient method to add member with receiver type. */
    inline operator fun String.invoke(builder: CodeBlocks.Builder.() -> Unit): CodeBlock =
        add(this, builder)
}
