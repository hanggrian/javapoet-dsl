package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.collections.CodeBlockContainer
import com.squareup.javapoet.CodeBlock

/**
 * Converts string to [CodeBlock] using formatted [args].
 *
 * @see kotlin.text.format
 */
fun codeBlockOf(format: String, vararg args: Any?): CodeBlock =
    format.internalFormat(args) { format2, args2 -> CodeBlock.of(format2, *args2) }

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
@SpecMarker
class CodeBlockBuilder internal constructor(val nativeBuilder: CodeBlock.Builder) : CodeBlockContainer {

    /** Returns true if this builder contains no code. */
    fun isEmpty(): Boolean = nativeBuilder.isEmpty

    /** Returns true if this builder contains code. */
    fun isNotEmpty(): Boolean = !nativeBuilder.isEmpty

    override fun appendNamed(format: String, args: Map<String, *>): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.addNamed(format2, args2) }

    override fun append(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.add(format2, *args2) }

    override fun beginControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.beginControlFlow(format2, *args2) }

    override fun nextControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.nextControlFlow(format2, *args2) }

    override fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    override fun endControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.endControlFlow(format2, *args2) }

    override fun appendLine(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.addStatement(format2, *args2) }

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
