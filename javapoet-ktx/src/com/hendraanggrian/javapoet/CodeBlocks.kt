package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.CodeCollection
import com.squareup.javapoet.CodeBlock

fun String.toCode(vararg args: Any): CodeBlock = format(this, args) { s, array -> CodeBlock.of(s, *array) }

inline fun buildCode(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
    CodeBlockBuilder(CodeBlock.builder()).apply(builderAction).build()

/** Wrapper of [CodeBlock.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDslMarker
class CodeBlockBuilder @PublishedApi internal constructor(private val nativeBuilder: CodeBlock.Builder) :
    CodeCollection() {

    fun isEmpty(): Boolean = nativeBuilder.isEmpty

    fun addNamed(format: String, arguments: Map<String, *>) =
        format(format, arguments) { s, map -> nativeBuilder.addNamed(s, map) }

    override fun append(format: String, vararg args: Any) {
        format(format, args) { s, array -> nativeBuilder.add(s, *array) }
    }

    override fun beginControlFlow(flow: String, vararg args: Any) {
        format(flow, args) { s, array -> nativeBuilder.beginControlFlow(s, *array) }
    }

    override fun nextControlFlow(flow: String, vararg args: Any) {
        format(flow, args) { s, array -> nativeBuilder.nextControlFlow(s, *array) }
    }

    override fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    override fun endControlFlow(flow: String, vararg args: Any) {
        format(flow, args) { s, array -> nativeBuilder.endControlFlow(s, *array) }
    }

    override fun appendln(format: String, vararg args: Any) {
        format(format, args) { s, array -> nativeBuilder.addStatement(s, *array) }
    }

    override fun appendln(block: CodeBlock): CodeBlock =
        block.also { nativeBuilder.addStatement(block) }

    override fun append(block: CodeBlock): CodeBlock =
        block.also { nativeBuilder.add(it) }

    fun indent() {
        nativeBuilder.indent()
    }

    fun unindent() {
        nativeBuilder.unindent()
    }

    fun build(): CodeBlock =
        nativeBuilder.build()
}
