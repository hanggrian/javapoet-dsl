package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.CodeContainer
import com.hendraanggrian.javapoet.internal.SpecBuilder
import com.squareup.javapoet.CodeBlock

/** Returns block of code with custom initialization block. */
inline fun buildCodeBlock(builder: CodeBlockBuilder.() -> Unit): CodeBlock =
    CodeBlockBuilder(CodeBlock.builder())
        .apply(builder)
        .build()

@JavapoetDslMarker
class CodeBlockBuilder @PublishedApi internal constructor(private val nativeBuilder: CodeBlock.Builder) :
    SpecBuilder<CodeBlock>(),
    ControlFlowedSpecBuilder,
    CodedSpecBuilder {

    fun addNamed(format: String, arguments: Map<String, *>) {
        nativeBuilder.addNamed(format, arguments)
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

    override val statements: CodeContainer = object : CodeContainer() {
        override fun plusAssign(block: CodeBlock) {
            nativeBuilder.addStatement(block)
        }

        override fun add(format: String, vararg args: Any) {
            nativeBuilder.addStatement(format, *args)
        }
    }

    override val codes: CodeContainer = object : CodeContainer() {
        override fun plusAssign(block: CodeBlock) {
            nativeBuilder.add(block)
        }

        override fun add(format: String, vararg args: Any) {
            nativeBuilder.add(format, *args)
        }
    }

    fun indent() {
        nativeBuilder.indent()
    }

    fun unindent() {
        nativeBuilder.unindent()
    }

    override fun build(): CodeBlock = nativeBuilder.build()
}