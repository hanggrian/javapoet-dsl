package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.collections.CodeBlockContainer
import com.squareup.javapoet.CodeBlock

/** Joins code blocks into a single [CodeBlock], each separated by [separator]. */
fun Iterable<CodeBlock>.join(separator: String): CodeBlock = CodeBlock.join(this, separator)

/**
 * Converts string to [CodeBlock] using formatted [args].
 *
 * @see kotlin.text.format
 */
fun codeBlockOf(format: String, vararg args: Any): CodeBlock =
    format.formatWith(args) { s, array -> CodeBlock.of(s, *array) }

/**
 * Builds new [CodeBlock],
 * by populating newly created [CodeBlockBuilder] using provided [builderAction] and then building it.
 */
inline fun buildCodeBlock(
    builderAction: CodeBlockBuilder.() -> Unit
): CodeBlock = CodeBlock.builder().build(builderAction)

/** Modify existing [CodeBlock.Builder] using provided [builderAction] and then building it. */
inline fun CodeBlock.Builder.build(
    builderAction: CodeBlockBuilder.() -> Unit
): CodeBlock = CodeBlockBuilder(this).apply(builderAction).build()

/** Wrapper of [CodeBlock.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDslMarker
class CodeBlockBuilder(private val nativeBuilder: CodeBlock.Builder) : CodeBlockContainer() {

    /** Returns true if this builder contains no code. */
    fun isEmpty(): Boolean = nativeBuilder.isEmpty

    /** Returns true if this builder contains code. */
    fun isNotEmpty(): Boolean = !nativeBuilder.isEmpty

    override fun appendNamed(format: String, args: Map<String, *>): Unit =
        format.formatWith(args) { s, map -> nativeBuilder.addNamed(s, map) }

    override fun append(format: String, vararg args: Any): Unit =
        format.formatWith(args) { s, array -> nativeBuilder.add(s, *array) }

    override fun beginFlow(flow: String, vararg args: Any): Unit =
        flow.formatWith(args) { s, array -> nativeBuilder.beginControlFlow(s, *array) }

    override fun nextFlow(flow: String, vararg args: Any): Unit =
        flow.formatWith(args) { s, array -> nativeBuilder.nextControlFlow(s, *array) }

    override fun endFlow() {
        nativeBuilder.endControlFlow()
    }

    override fun endFlow(flow: String, vararg args: Any): Unit =
        flow.formatWith(args) { s, array -> nativeBuilder.endControlFlow(s, *array) }

    override fun appendln(format: String, vararg args: Any): Unit =
        format.formatWith(args) { s, array -> nativeBuilder.addStatement(s, *array) }

    override fun appendln(code: CodeBlock) {
        nativeBuilder.addStatement(code)
    }

    override fun append(code: CodeBlock) {
        nativeBuilder.add(code)
    }

    /** Indent current code. */
    fun indent() {
        nativeBuilder.indent()
    }

    /** Unindent current code. */
    fun unindent() {
        nativeBuilder.unindent()
    }

    /** Clear current code. */
    fun clear() {
        nativeBuilder.clear()
    }

    /** Returns native spec. */
    fun build(): CodeBlock = nativeBuilder.build()
}
