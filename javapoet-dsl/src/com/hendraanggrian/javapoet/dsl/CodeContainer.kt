package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.squareup.javapoet.CodeBlock

abstract class CodeContainer internal constructor() : CodeContainerDelegate {

    inline operator fun invoke(configuration: CodeContainerScope.() -> Unit) =
        configuration(CodeContainerScope(this))
}

@JavapoetDslMarker
class CodeContainerScope @PublishedApi internal constructor(private val container: CodeContainer) :
    CodeContainerDelegate by container

interface CodeContainerDelegate {

    fun add(format: String, vararg args: Any)

    fun add(block: CodeBlock): CodeBlock

    fun add(builder: CodeBlockBuilder.() -> Unit): CodeBlock = add(CodeBlockBuilder.of(builder))

    operator fun plusAssign(value: String) {
        add(value)
    }

    operator fun plusAssign(block: CodeBlock) {
        add(block)
    }
}