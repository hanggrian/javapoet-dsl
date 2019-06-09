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
    ControlFlowableSpecBuilder,
    CodableSpecBuilder {

    fun named(format: String, arguments: Map<String, *>) {
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

    override fun statement(format: String, vararg args: Any) {
        nativeBuilder.addStatement(format, *args)
    }

    override fun statement(builder: CodeBlockBuilder.() -> Unit) {
        nativeBuilder.addStatement(buildCodeBlock(builder))
    }

    override fun code(format: String, vararg args: Any) {
        nativeBuilder.add(format, *args)
    }

    override fun code(builder: CodeBlockBuilder.() -> Unit) {
        nativeBuilder.add(buildCodeBlock(builder))
    }

    fun indent() {
        nativeBuilder.indent()
    }

    fun unindent() {
        nativeBuilder.unindent()
    }

    override fun build(): CodeBlock = nativeBuilder.build()
}