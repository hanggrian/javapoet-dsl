package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.ControlFlowManager
import com.hendraanggrian.javapoet.internal.StatementManager
import com.squareup.javapoet.CodeBlock

/** Returns block of code with custom initialization block. */
fun buildCodeBlock(builder: CodeBlockBuilder.() -> Unit): CodeBlock =
    CodeBlockBuilder(CodeBlock.builder())
        .apply(builder)
        .build()

class CodeBlockBuilder(private val nativeBuilder: CodeBlock.Builder) : ControlFlowManager, StatementManager {

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

    override fun statement(format: String, vararg args: Any) {
        nativeBuilder.addStatement(format, *args)
    }

    override fun statement(builder: CodeBlockBuilder.() -> Unit) {
        nativeBuilder.addStatement(buildCodeBlock(builder))
    }

    fun add(builder: CodeBlockBuilder.() -> Unit) {
        nativeBuilder.add(buildCodeBlock(builder))
    }

    fun indent() {
        nativeBuilder.indent()
    }

    fun unindent() {
        nativeBuilder.unindent()
    }

    fun build(): CodeBlock = nativeBuilder.build()
}