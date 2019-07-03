@file:JvmMultifileClass
@file:JvmName("SpecBuildersKt")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.CodeContainer
import com.squareup.javapoet.CodeBlock

inline operator fun CodeBlock.invoke(builder: CodeBlockBuilder.() -> Unit): CodeBlock =
    toBuilder()(builder)

inline operator fun CodeBlock.Builder.invoke(builder: CodeBlockBuilder.() -> Unit): CodeBlock =
    CodeBlockBuilder(this).apply(builder).build()

class CodeBlockBuilder @PublishedApi internal constructor(private val nativeBuilder: CodeBlock.Builder) :
    CodeContainer() {

    fun isEmpty(): Boolean = nativeBuilder.isEmpty

    fun addNamed(format: String, arguments: Map<String, *>) {
        nativeBuilder.addNamed(format, arguments)
    }

    override fun add(format: String, vararg args: Any) {
        nativeBuilder.add(format, *args)
    }

    fun beginControlFlow(controlFlow: String, vararg args: Any) {
        nativeBuilder.beginControlFlow(controlFlow, *args)
    }

    fun nextControlFlow(controlFlow: String, vararg args: Any) {
        nativeBuilder.nextControlFlow(controlFlow, *args)
    }

    fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    fun endControlFlow(controlFlow: String, vararg args: Any) {
        nativeBuilder.endControlFlow(controlFlow, *args)
    }

    val statements: CodeContainer = object : CodeContainer() {
        override fun add(format: String, vararg args: Any) {
            nativeBuilder.addStatement(format, *args)
        }

        override fun add(codeBlock: CodeBlock): CodeBlock = codeBlock.also { nativeBuilder.addStatement(it) }
    }

    override fun add(codeBlock: CodeBlock): CodeBlock = codeBlock.also { nativeBuilder.add(it) }

    fun indent() {
        nativeBuilder.indent()
    }

    fun unindent() {
        nativeBuilder.unindent()
    }

    fun build(): CodeBlock = nativeBuilder.build()
}