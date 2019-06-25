package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.SpecBuilder
import com.squareup.javapoet.CodeBlock

/** Returns block of code with custom initialization block. */
inline fun buildCodeBlock(builder: CodeBlockBuilder.() -> Unit): CodeBlock =
    CodeBlockBuilder(CodeBlock.builder())
        .apply(builder)
        .build()

@SpecBuilderDslMarker
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

    override fun addStatement(format: String, vararg args: Any) {
        nativeBuilder.addStatement(format, *args)
    }

    override fun addStatement(block: CodeBlock) {
        nativeBuilder.addStatement(block)
    }

    override fun addCode(format: String, vararg args: Any) {
        nativeBuilder.add(format, *args)
    }

    override fun addCode(block: CodeBlock) {
        nativeBuilder.add(block)
    }

    fun indent() {
        nativeBuilder.indent()
    }

    fun unindent() {
        nativeBuilder.unindent()
    }

    override fun build(): CodeBlock = nativeBuilder.build()
}