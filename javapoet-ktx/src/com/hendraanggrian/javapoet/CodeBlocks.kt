package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.CodeCollection
import com.squareup.javapoet.CodeBlock

/** Converts string to [CodeBlock] using formatted [args]. */
fun String.toCode(vararg args: Any): CodeBlock =
    convert(this, args) { s, array -> CodeBlock.of(s, *array) }

/**
 * Builds new [CodeBlock] by populating newly created [CodeBlockBuilder] using provided [builderAction]
 * and then building it.
 */
inline fun buildCode(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
    CodeBlockBuilder(CodeBlock.builder()).apply(builderAction).build()

/** Joins code blocks into a single [CodeBlock], each separated by [separator]. */
fun Iterable<CodeBlock>.join(separator: String): CodeBlock =
    CodeBlock.join(this, separator)

/** Wrapper of [CodeBlock.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDslMarker
class CodeBlockBuilder @PublishedApi internal constructor(private val nativeBuilder: CodeBlock.Builder) :
    CodeCollection() {

    fun isEmpty(): Boolean = nativeBuilder.isEmpty

    fun addNamed(format: String, arguments: Map<String, *>) =
        convert(format, arguments) { s, map -> nativeBuilder.addNamed(s, map) }

    override fun append(format: String, vararg args: Any) {
        convert(format, args) { s, array -> nativeBuilder.add(s, *array) }
    }

    override fun beginFlow(flow: String, vararg args: Any) {
        convert(flow, args) { s, array -> nativeBuilder.beginControlFlow(s, *array) }
    }

    override fun nextFlow(flow: String, vararg args: Any) {
        convert(flow, args) { s, array -> nativeBuilder.nextControlFlow(s, *array) }
    }

    override fun endFlow() {
        nativeBuilder.endControlFlow()
    }

    override fun endFlow(flow: String, vararg args: Any) {
        convert(flow, args) { s, array -> nativeBuilder.endControlFlow(s, *array) }
    }

    override fun appendln(format: String, vararg args: Any) {
        convert(format, args) { s, array -> nativeBuilder.addStatement(s, *array) }
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
