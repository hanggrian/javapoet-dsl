package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.ControlFlowManager
import com.squareup.javapoet.CodeBlock

/** Returns block of code with custom initialization block. */
fun buildCodeBlock(builder: CodeBlockBuilder.() -> Unit): CodeBlock =
    CodeBlockBuilderImpl(CodeBlock.builder())
        .apply(builder)
        .build()

interface CodeBlockBuilder : ControlFlowManager {

    val nativeBuilder: CodeBlock.Builder

    fun named(format: String, arguments: Map<String, *>) {
        nativeBuilder.addNamed(format, arguments)
    }

    operator fun String.invoke(vararg args: Any) {
        nativeBuilder.add(this, *args)
    }

    override fun beginControlFlow(format: String, vararg args: Any) {
        nativeBuilder.beginControlFlow(format, *args)
    }

    override fun nextControlFlow(format: String, vararg args: Any) {
        nativeBuilder.nextControlFlow(format, *args)
    }

    override fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    override fun endControlFlow(format: String, vararg args: Any) {
        nativeBuilder.endControlFlow(format, *args)
    }

    fun statement(format: String, vararg args: Any) {
        nativeBuilder.addStatement(format, *args)
    }

    fun statement(block: CodeBlock) {
        nativeBuilder.addStatement(block)
    }

    operator fun plusAssign(block: CodeBlock) {
        nativeBuilder.add(block)
    }

    fun indent() {
        nativeBuilder.indent()
    }

    fun unindent() {
        nativeBuilder.unindent()
    }

    fun build(): CodeBlock = nativeBuilder.build()
}

internal class CodeBlockBuilderImpl(override val nativeBuilder: CodeBlock.Builder) : CodeBlockBuilder