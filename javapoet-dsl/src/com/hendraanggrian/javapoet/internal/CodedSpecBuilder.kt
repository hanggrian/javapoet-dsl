package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.buildCodeBlock
import com.squareup.javapoet.CodeBlock

internal interface CodedSpecBuilder {

    /** Add a code to this spec builder. */
    fun code(format: String, vararg args: Any)

    /** Add a code block to this spec builder. */
    fun code(block: CodeBlock)

    /** Build a code block and add it to this spec builder. */
    fun code(builder: CodeBlockBuilder.() -> Unit) = code(buildCodeBlock(builder))

    /** Add a statement to this spec builder. */
    fun statement(format: String, vararg args: Any)

    /** Add a statement block to this spec builder. */
    fun statement(block: CodeBlock)

    /** Build a statement block and add it to this spec builder. */
    fun statement(builder: CodeBlockBuilder.() -> Unit) = statement(buildCodeBlock(builder))
}