package com.hendraanggrian.javapoet

import com.squareup.javapoet.CodeBlock

/**
 * Converts string to [CodeBlock] using formatted [args].
 *
 * @see kotlin.text.format
 */
fun codeBlockOf(format: String, vararg args: Any?): CodeBlock =
    format.internalFormat(args) { format2, args2 -> CodeBlock.of(format2, *args2) }

/**
 * Builds new [CodeBlock], by populating newly created [CodeBlockBuilder] using
 * provided [configuration].
 */
inline fun buildCodeBlock(configuration: CodeBlockBuilder.() -> Unit): CodeBlock =
    CodeBlockBuilder(CodeBlock.builder()).apply(configuration).build()

/** Wrapper of [CodeBlock.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDsl
class CodeBlockBuilder(private val nativeBuilder: CodeBlock.Builder) {
    /** Returns true if this builder contains no code. */
    fun isEmpty(): Boolean = nativeBuilder.isEmpty

    /** Returns true if this builder contains code. */
    fun isNotEmpty(): Boolean = !nativeBuilder.isEmpty

    fun appendNamed(format: String, args: Map<String, *>): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.addNamed(format2, args2) }

    fun append(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.add(format2, *args2) }

    fun beginControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.beginControlFlow(format2, *args2)
        }

    fun nextControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.nextControlFlow(format2, *args2)
        }

    fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    fun endControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.endControlFlow(format2, *args2)
        }

    fun appendLine() {
        nativeBuilder.addStatement("")
    }

    fun appendLine(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.addStatement(format2, *args2)
        }

    fun appendLine(code: CodeBlock) {
        nativeBuilder.addStatement(code)
    }

    fun append(code: CodeBlock) {
        nativeBuilder.add(code)
    }

    fun indent() {
        nativeBuilder.indent()
    }

    fun unindent() {
        nativeBuilder.unindent()
    }

    fun clear() {
        nativeBuilder.clear()
    }

    fun build(): CodeBlock = nativeBuilder.build()
}
