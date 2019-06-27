package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.buildCodeBlock
import com.squareup.javapoet.CodeBlock

abstract class CodeContainer {

    abstract operator fun plusAssign(block: CodeBlock)

    abstract fun add(format: String, vararg args: Any)

    operator fun plusAssign(value: String) = add(value)

    fun add(builder: CodeBlockBuilder.() -> Unit) =
        plusAssign(buildCodeBlock(builder))

    operator fun invoke(configuration: CodeContainerScope.() -> Unit) =
        configuration(CodeContainerScope(this))
}

@JavapoetDslMarker
class CodeContainerScope internal constructor(private val container: CodeContainer) {

    fun add(format: String, vararg args: Any) {
        container.add(format, *args)
    }

    fun add(builder: CodeBlockBuilder.() -> Unit) {
        container += buildCodeBlock(builder)
    }
}