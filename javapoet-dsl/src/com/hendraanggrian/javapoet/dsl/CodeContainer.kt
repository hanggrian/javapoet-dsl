package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.squareup.javapoet.CodeBlock

abstract class CodeContainer internal constructor() : CodeContainerDelegate() {

    operator fun invoke(configuration: CodeContainerScope.() -> Unit) =
        CodeContainerScope(this).configuration()
}

@JavapoetDslMarker
class CodeContainerScope @PublishedApi internal constructor(private val container: CodeContainer) :
    CodeContainerDelegate() {

    override fun add(format: String, vararg args: Any) = container.add(format, *args)

    override fun add(block: CodeBlock): CodeBlock = container.add(block)
}

sealed class CodeContainerDelegate {

    abstract fun add(format: String, vararg args: Any)

    abstract fun add(block: CodeBlock): CodeBlock

    fun add(builder: CodeBlockBuilder.() -> Unit): CodeBlock = add(CodeBlockBuilder.of(builder))

    operator fun plusAssign(value: String) {
        add(value)
    }

    operator fun plusAssign(block: CodeBlock) {
        add(block)
    }
}