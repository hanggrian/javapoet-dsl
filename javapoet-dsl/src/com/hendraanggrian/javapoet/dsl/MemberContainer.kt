@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.squareup.javapoet.CodeBlock

@JavapoetDslMarker
class MemberContainerScope @PublishedApi internal constructor(container: MemberContainer) :
    MemberContainer by container {

    /** Convenient method to add member with receiver. */
    inline operator fun String.invoke(noinline builder: CodeBlockBuilder.() -> Unit): CodeBlock = add(this, builder)
}

interface MemberContainer {

    fun add(name: String, format: String, vararg args: Any)

    /** Add spec to this container, returning the spec added. */
    fun add(name: String, block: CodeBlock): CodeBlock

    fun add(name: String, builder: CodeBlockBuilder.() -> Unit): CodeBlock = add(name, CodeBlockBuilder.of(builder))
}

/** Configures the members of this container. */
inline operator fun MemberContainer.invoke(configuration: MemberContainerScope.() -> Unit) =
    MemberContainerScope(this).configuration()

inline operator fun MemberContainer.set(name: String, format: String) {
    add(name, format)
}

inline operator fun MemberContainer.set(name: String, block: CodeBlock) {
    add(name, block)
}