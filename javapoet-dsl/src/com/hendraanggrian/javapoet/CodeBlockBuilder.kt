@file:Suppress("NOTHING_TO_INLINE")

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
    SpecBuilder<CodeBlock>() {

    fun isEmpty(): Boolean = nativeBuilder.isEmpty

    fun addNamed(format: String, arguments: Map<String, *>) {
        nativeBuilder.addNamed(format, arguments)
    }

    inline fun addNamed(format: String, vararg arguments: Pair<String, *>) = addNamed(format, mapOf(*arguments))

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

    val codes: CodeContainer = object : CodeContainer() {
        override fun add(format: String, vararg args: Any) {
            nativeBuilder.add(format, *args)
        }

        override fun add(block: CodeBlock): CodeBlock = block.also { nativeBuilder.add(it) }
    }

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