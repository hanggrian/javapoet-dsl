package com.hendraanggrian.javapoet.container

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.buildCodeBlock
import com.squareup.javapoet.CodeBlock

abstract class JavadocContainer {

    abstract operator fun plusAssign(block: CodeBlock)

    abstract fun add(format: String, vararg args: Any)

    fun add(builder: CodeBlockBuilder.() -> Unit) =
        plusAssign(buildCodeBlock(builder))
}