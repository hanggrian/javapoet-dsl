package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.CodeBlockBuilder

internal interface CodedSpecBuilder {

    /** Add a code to this spec builder. */
    fun code(format: String, vararg args: Any)

    /** Build a code block and add it to this spec builder. */
    fun code(builder: CodeBlockBuilder.() -> Unit)

    /** Add a statement to this spec builder. */
    fun statement(format: String, vararg args: Any)

    /** Build a statement block and add it to this spec builder. */
    fun statement(builder: CodeBlockBuilder.() -> Unit)
}