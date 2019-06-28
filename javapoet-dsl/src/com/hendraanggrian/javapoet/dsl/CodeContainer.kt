@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.buildCodeBlock
import com.squareup.javapoet.CodeBlock

abstract class CodeContainer : CodeContainerDelegate {

    inline operator fun plusAssign(value: String) = add(value)

    inline operator fun plusAssign(block: CodeBlock) = add(block)

    inline operator fun invoke(configuration: CodeContainerScope.() -> Unit) =
        configuration(CodeContainerScope(this))
}

@JavapoetDslMarker
class CodeContainerScope @PublishedApi internal constructor(private val container: CodeContainer) :
    CodeContainerDelegate {

    override fun add(format: String, vararg args: Any) = container.add(format, *args)

    override fun add(block: CodeBlock) = container.add(block)
}

internal interface CodeContainerDelegate {

    fun add(format: String, vararg args: Any)

    fun add(block: CodeBlock)

    fun add(builder: CodeBlockBuilder.() -> Unit) = add(buildCodeBlock(builder))
}