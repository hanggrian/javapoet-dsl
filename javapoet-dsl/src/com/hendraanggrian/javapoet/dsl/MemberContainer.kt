@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.squareup.javapoet.CodeBlock

abstract class MemberContainer internal constructor() : MemberContainerDelegate() {

    /** Open DSL to configure this container. */
    inline operator fun invoke(configuration: MemberContainerScope.() -> Unit) =
        MemberContainerScope(this).configuration()
}

@JavapoetDslMarker
class MemberContainerScope @PublishedApi internal constructor(private val container: MemberContainer) :
    MemberContainerDelegate() {

    override fun add(name: String, format: String, vararg args: Any) = container.add(name, format, *args)

    override fun add(name: String, block: CodeBlock): CodeBlock = container.add(name, block)

    /** Convenient method to add member with receiver. */
    inline operator fun String.invoke(noinline builder: CodeBlockBuilder.() -> Unit): CodeBlock = add(this, builder)
}

sealed class MemberContainerDelegate {

    abstract fun add(name: String, format: String, vararg args: Any)

    /** Add spec to this container, returning the spec added. */
    abstract fun add(name: String, block: CodeBlock): CodeBlock

    fun add(name: String, builder: CodeBlockBuilder.() -> Unit): CodeBlock = add(name, CodeBlockBuilder.of(builder))

    inline operator fun set(name: String, format: String) {
        add(name, format)
    }

    inline operator fun set(name: String, block: CodeBlock) {
        add(name, block)
    }
}