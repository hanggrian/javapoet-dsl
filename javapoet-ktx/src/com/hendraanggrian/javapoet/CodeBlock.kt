package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.CodeBlockHandler
import com.squareup.javapoet.CodeBlock

/** Joins code blocks into a single [CodeBlock], each separated by [separator]. */
fun Iterable<CodeBlock>.join(separator: String): CodeBlock = CodeBlock.join(this, separator)

/**
 * Converts string to [CodeBlock] using formatted [args].
 *
 * @see kotlin.text.format
 */
fun codeBlockOf(format: String, vararg args: Any): CodeBlock =
    format.internalFormat(args) { s, array -> CodeBlock.of(s, *array) }

/**
 * Builds new [CodeBlock],
 * by populating newly created [CodeBlockBuilder] using provided [configuration].
 */
fun buildCodeBlock(configuration: CodeBlockBuilder.() -> Unit): CodeBlock =
    CodeBlockBuilder(CodeBlock.builder()).apply(configuration).build()

/** Modify existing [CodeBlock.Builder] using provided [configuration]. */
fun CodeBlock.Builder.edit(configuration: CodeBlockBuilder.() -> Unit): CodeBlock.Builder =
    CodeBlockBuilder(this).apply(configuration).nativeBuilder

/**
 * Wrapper of [CodeBlock.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
class CodeBlockBuilder internal constructor(val nativeBuilder: CodeBlock.Builder) : CodeBlockHandler() {

    /** Returns true if this builder contains no code. */
    fun isEmpty(): Boolean = nativeBuilder.isEmpty

    /** Returns true if this builder contains code. */
    fun isNotEmpty(): Boolean = !nativeBuilder.isEmpty

    override fun appendNamed(format: String, args: Map<String, *>): Unit =
        format.internalFormat(args) { s, map -> nativeBuilder.addNamed(s, map) }

    override fun append(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { s, array -> nativeBuilder.add(s, *array) }

    override fun beginFlow(flow: String, vararg args: Any): Unit =
        flow.internalFormat(args) { s, array -> nativeBuilder.beginControlFlow(s, *array) }

    override fun nextFlow(flow: String, vararg args: Any): Unit =
        flow.internalFormat(args) { s, array -> nativeBuilder.nextControlFlow(s, *array) }

    override fun endFlow() {
        nativeBuilder.endControlFlow()
    }

    override fun endFlow(flow: String, vararg args: Any): Unit =
        flow.internalFormat(args) { s, array -> nativeBuilder.endControlFlow(s, *array) }

    override fun appendLine(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { s, array -> nativeBuilder.addStatement(s, *array) }

    override fun appendLine(code: CodeBlock) {
        nativeBuilder.addStatement(code)
    }

    override fun append(code: CodeBlock) {
        nativeBuilder.add(code)
    }

    /** Append an indentation. */
    fun indent() {
        nativeBuilder.indent()
    }

    /** Reverse an indentation. */
    fun unindent() {
        nativeBuilder.unindent()
    }

    /** Convenient way to configure code within single indentation. */
    fun indent(configuration: () -> Unit) {
        indent()
        configuration()
        unindent()
    }

    /** Convenient way to configure code within multiple indentation. */
    fun indent(level: Int, configuration: () -> Unit) {
        repeat(level) { indent() }
        configuration()
        repeat(level) { unindent() }
    }

    /** Clear current code. */
    fun clear() {
        nativeBuilder.clear()
    }

    /** Returns native spec. */
    fun build(): CodeBlock = nativeBuilder.build()
}
