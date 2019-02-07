package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.buildCodeBlock
import com.squareup.javapoet.CodeBlock

interface JavadocManager {

    fun javadoc(format: String, vararg args: Any)

    fun javadoc(block: CodeBlock)

    fun javadoc(builder: CodeBlockBuilder.() -> Unit) = javadoc(buildCodeBlock(builder))
}