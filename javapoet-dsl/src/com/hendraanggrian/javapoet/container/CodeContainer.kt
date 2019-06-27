package com.hendraanggrian.javapoet.container

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.buildCodeBlock
import com.hendraanggrian.javapoet.scope.CodeContainerScope
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