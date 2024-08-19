@file:OptIn(ExperimentalContracts::class)

package com.hanggrian.javapoet

import com.hanggrian.javapoet.internals.Internals
import com.squareup.javapoet.CodeBlock
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/** Converts string to [CodeBlock] using formatted [args]. */
public inline fun codeBlockOf(format: String, vararg args: Any?): CodeBlock =
    Internals.format(format, args) { format2, args2 -> CodeBlock.of(format2, *args2) }

/**
 * Builds new [CodeBlock], by populating newly created [CodeBlockBuilder] using
 * provided [configuration].
 */
public inline fun buildCodeBlock(configuration: CodeBlockBuilder.() -> Unit): CodeBlock {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return CodeBlockBuilder(CodeBlock.builder())
        .apply(configuration)
        .build()
}

/** Wrapper of [CodeBlock.Builder], providing DSL support as a replacement to Java builder. */
@JavaPoetDsl
public class CodeBlockBuilder(private val nativeBuilder: CodeBlock.Builder) {
    /** Returns true if this builder contains no code. */
    public fun isEmpty(): Boolean = nativeBuilder.isEmpty

    /** Returns true if this builder contains code. */
    public fun isNotEmpty(): Boolean = !nativeBuilder.isEmpty

    public fun appendNamed(format: String, args: Map<String, *>): Unit =
        Internals.format(format, args) { format2, args2 -> nativeBuilder.addNamed(format2, args2) }

    public fun append(format: String, vararg args: Any): Unit =
        Internals.format(format, args) { format2, args2 -> nativeBuilder.add(format2, *args2) }

    public fun beginControlFlow(format: String, vararg args: Any): Unit =
        Internals.format(format, args) { format2, args2 ->
            nativeBuilder.beginControlFlow(format2, *args2)
        }

    public fun nextControlFlow(format: String, vararg args: Any): Unit =
        Internals.format(format, args) { format2, args2 ->
            nativeBuilder.nextControlFlow(format2, *args2)
        }

    public fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    public fun endControlFlow(format: String, vararg args: Any): Unit =
        Internals.format(format, args) { format2, args2 ->
            nativeBuilder.endControlFlow(format2, *args2)
        }

    public fun appendLine() {
        nativeBuilder.addStatement("")
    }

    public fun appendLine(format: String, vararg args: Any): Unit =
        Internals.format(format, args) { format2, args2 ->
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
