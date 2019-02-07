package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.CodeBlockBuilder

interface StatementManager {

    fun statement(format: String, vararg args: Any)

    fun statement(builder: CodeBlockBuilder.() -> Unit)
}