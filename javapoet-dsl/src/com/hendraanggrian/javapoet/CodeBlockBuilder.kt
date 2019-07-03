@file:JvmMultifileClass
@file:JvmName("SpecBuildersKt")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.CodeContainer
import com.squareup.javapoet.CodeBlock

/** Configure [this] block with DSL. */
inline operator fun CodeBlock.invoke(builder: CodeBlockBuilder.() -> Unit): CodeBlock =
    toBuilder()(builder)

/** Configure [this] builder with DSL. */
inline operator fun CodeBlock.Builder.invoke(builder: CodeBlockBuilder.() -> Unit): CodeBlock =
    CodeBlockBuilder(this).apply(builder).build()

/** Wrapper of [CodeBlock.Builder], providing DSL support as a replacement to Java builder. */
class CodeBlockBuilder @PublishedApi internal constructor(private val nativeBuilder: CodeBlock.Builder) :
    CodeContainer() {

    fun isEmpty(): Boolean = nativeBuilder.isEmpty

    fun addNamed(format: String, arguments: Map<String, *>) {
        nativeBuilder.addNamed(format, arguments)
    }

    override fun add(format: String, vararg args: Any) {
        nativeBuilder.add(format, *args)
    }

    override fun beginControlFlow(flow: String, vararg args: Any) {
        nativeBuilder.beginControlFlow(flow, *args)
    }

    override fun nextControlFlow(flow: String, vararg args: Any) {
        nativeBuilder.nextControlFlow(flow, *args)
    }

    override fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    override fun endControlFlow(flow: String, vararg args: Any) {
        nativeBuilder.endControlFlow(flow, *args)
    }

    override fun addStatement(format: String, vararg args: Any) {
        nativeBuilder.addStatement(format, *args)
    }

    override fun addStatement(block: CodeBlock): CodeBlock =
        block.also { nativeBuilder.addStatement(block) }

    override fun add(block: CodeBlock): CodeBlock =
        block.also { nativeBuilder.add(it) }

    fun indent() {
        nativeBuilder.indent()
    }

    fun unindent() {
        nativeBuilder.unindent()
    }

    fun build(): CodeBlock = nativeBuilder.build()
}