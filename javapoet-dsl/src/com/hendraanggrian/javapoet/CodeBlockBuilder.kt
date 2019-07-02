package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.CodeCollection
import com.hendraanggrian.javapoet.dsl.CodeContainer
import com.squareup.javapoet.CodeBlock

class CodeBlockBuilder @PublishedApi internal constructor(private val nativeBuilder: CodeBlock.Builder) :
    SpecBuilder<CodeBlock>(), CodeCollection {

    @PublishedApi
    @Suppress("NOTHING_TO_INLINE")
    internal companion object {
        inline fun of(builder: CodeBlockBuilder.() -> Unit): CodeBlock =
            CodeBlockBuilder(CodeBlock.builder())
                .apply(builder)
                .build()
    }

    fun isEmpty(): Boolean = nativeBuilder.isEmpty

    fun addNamed(format: String, arguments: Map<String, *>) {
        nativeBuilder.addNamed(format, arguments)
    }

    fun beginControlFlow(format: String, vararg args: Any) {
        nativeBuilder.beginControlFlow(format, *args)
    }

    fun nextControlFlow(format: String, vararg args: Any) {
        nativeBuilder.nextControlFlow(format, *args)
    }

    fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    fun endControlFlow(format: String, vararg args: Any) {
        nativeBuilder.endControlFlow(format, *args)
    }

    override fun add(format: String, vararg args: Any) {
        nativeBuilder.add(format, *args)
    }

    override fun add(block: CodeBlock): CodeBlock = block.also { nativeBuilder.add(it) }

    val statements: CodeContainer = object : CodeContainer() {
        override fun add(format: String, vararg args: Any) {
            nativeBuilder.addStatement(format, *args)
        }

        override fun add(block: CodeBlock): CodeBlock = block.also { nativeBuilder.addStatement(it) }
    }

    fun indent() {
        nativeBuilder.indent()
    }

    fun unindent() {
        nativeBuilder.unindent()
    }

    override fun build(): CodeBlock = nativeBuilder.build()
}