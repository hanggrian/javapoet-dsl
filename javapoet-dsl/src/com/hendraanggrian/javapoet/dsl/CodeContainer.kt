@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.squareup.javapoet.CodeBlock

abstract class CodeContainer internal constructor() : CodeContainerDelegate() {

    /** Open DSL to configure this container. */
    inline operator fun invoke(configuration: CodeContainerScope.() -> Unit) =
        CodeContainerScope(this).configuration()
}

@JavapoetDslMarker
class CodeContainerScope @PublishedApi internal constructor(private val container: CodeContainer) :
    CodeContainerDelegate() {

    override fun add(format: String, vararg args: Any) = container.add(format, *args)

    override fun add(block: CodeBlock): CodeBlock = container.add(block)
}

/** This class is abstract instead of sealed because [com.hendraanggrian.javapoet.CodeBlockBuilder] inherited it. */
abstract class CodeContainerDelegate internal constructor() {

    abstract fun add(format: String, vararg args: Any)

    /** Add spec to this container, returning the spec added. */
    abstract fun add(block: CodeBlock): CodeBlock

    fun add(builder: CodeBlockBuilder.() -> Unit): CodeBlock = add(CodeBlockBuilder.of(builder))

    inline operator fun plusAssign(value: String) {
        add(value)
    }

    inline operator fun plusAssign(block: CodeBlock) {
        add(block)
    }
}