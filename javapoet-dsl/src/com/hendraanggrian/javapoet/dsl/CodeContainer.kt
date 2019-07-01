@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.squareup.javapoet.CodeBlock

@JavapoetDslMarker
class CodeContainerScope @PublishedApi internal constructor(container: CodeContainer) :
    CodeContainer by container

/** This class is abstract instead of sealed because [com.hendraanggrian.javapoet.CodeBlockBuilder] inherited it. */
interface CodeContainer {

    fun add(format: String, vararg args: Any)

    /** Add spec to this container, returning the spec added. */
    fun add(block: CodeBlock): CodeBlock

    fun add(builder: CodeBlockBuilder.() -> Unit): CodeBlock = add(CodeBlockBuilder.of(builder))
}

/** Configures the code blocks of this container. */
inline operator fun CodeContainer.invoke(configuration: CodeContainerScope.() -> Unit) =
    CodeContainerScope(this).configuration()

inline operator fun CodeContainer.plusAssign(value: String) {
    add(value)
}

inline operator fun CodeContainer.plusAssign(block: CodeBlock) {
    add(block)
}