package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.buildCodeBlock
import com.squareup.javapoet.CodeBlock

internal interface Codable {

    fun code(format: String, vararg args: Any)

    fun code(block: CodeBlock)

    fun code(builder: CodeBlockBuilder.() -> Unit) = code(buildCodeBlock(builder))

    fun statement(format: String, vararg args: Any)

    fun statement(block: CodeBlock)

    fun statement(builder: CodeBlockBuilder.() -> Unit) = statement(buildCodeBlock(builder))
}