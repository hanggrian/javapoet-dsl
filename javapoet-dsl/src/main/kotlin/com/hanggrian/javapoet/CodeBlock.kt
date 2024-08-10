package com.hanggrian.javapoet

import com.squareup.javapoet.CodeBlock

/** Converts string to [CodeBlock] using formatted [args]. */
public fun codeBlockOf(format: String, vararg args: Any?): CodeBlock =
    format.internalFormat(args) { format2, args2 -> CodeBlock.of(format2, *args2) }

/**
 * Builds new [CodeBlock], by populating newly created [CodeBlockBuilder] using
 * provided [configuration].
 */
public fun buildCodeBlock(configuration: CodeBlockBuilder.() -> Unit): CodeBlock =
    CodeBlockBuilder(CodeBlock.builder()).apply(configuration).build()

/** Wrapper of [CodeBlock.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDsl
public class CodeBlockBuilder(private val nativeBuilder: CodeBlock.Builder) {
    /** Returns true if this builder contains no code. */
    public fun isEmpty(): Boolean = nativeBuilder.isEmpty

    /** Returns true if this builder contains code. */
    public fun isNotEmpty(): Boolean = !nativeBuilder.isEmpty

    public fun appendNamed(format: String, args: Map<String, *>): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.addNamed(format2, args2) }

    public fun append(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.add(format2, *args2) }

    public fun beginControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.beginControlFlow(format2, *args2)
        }

    public fun nextControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.nextControlFlow(format2, *args2)
        }

    public fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    public fun endControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.endControlFlow(format2, *args2)
        }

    public fun appendLine() {
        nativeBuilder.addStatement("")
    }

    public fun appendLine(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.addStatement(format2, *args2)
        }

    public fun appendLine(code: CodeBlock) {
        nativeBuilder.addStatement(code)
    }

    public fun append(code: CodeBlock) {
        nativeBuilder.add(code)
    }

    public fun indent() {
        nativeBuilder.indent()
    }

    public fun unindent() {
        nativeBuilder.unindent()
    }

    public fun clear() {
        nativeBuilder.clear()
    }

    public fun build(): CodeBlock = nativeBuilder.build()
}
