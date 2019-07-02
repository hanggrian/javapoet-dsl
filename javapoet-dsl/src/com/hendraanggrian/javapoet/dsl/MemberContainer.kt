@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.squareup.javapoet.CodeBlock

/** An [MemberContainer] is responsible for managing a set of member instances. */
abstract class MemberContainer internal constructor() {

    abstract fun add(name: String, format: String, vararg args: Any)

    abstract fun add(name: String, block: CodeBlock): CodeBlock

    fun add(name: String, builder: CodeBlockBuilder.() -> Unit): CodeBlock = add(name, CodeBlockBuilder.of(builder))

    inline operator fun set(name: String, format: String) {
        add(name, format)
    }

    inline operator fun set(name: String, block: CodeBlock) {
        add(name, block)
    }

    inline operator fun invoke(configuration: MemberContainerScope.() -> Unit) =
        MemberContainerScope(this).configuration()
}

/**
 * Receiver for the `members` block providing an extended set of operators for the configuration.
 */
class MemberContainerScope @PublishedApi internal constructor(private val container: MemberContainer) :
    MemberContainer() {

    override fun add(name: String, format: String, vararg args: Any) = container.add(name, format, *args)

    override fun add(name: String, block: CodeBlock): CodeBlock = container.add(name, block)

    inline operator fun String.invoke(noinline builder: CodeBlockBuilder.() -> Unit): CodeBlock = add(this, builder)
}