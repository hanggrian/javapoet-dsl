package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.squareup.javapoet.CodeBlock

abstract class MemberContainer internal constructor() : MemberContainerDelegate() {

    operator fun invoke(configuration: MemberContainerScope.() -> Unit) =
        MemberContainerScope(this).configuration()
}

@JavapoetDslMarker
@Suppress("NOTHING_TO_INLINE")
class MemberContainerScope @PublishedApi internal constructor(private val container: MemberContainer) :
    MemberContainerDelegate() {

    override fun add(name: String, format: String, vararg args: Any) = container.add(name, format, *args)

    override fun add(name: String, block: CodeBlock): CodeBlock = container.add(name, block)

    operator fun String.invoke(builder: CodeBlockBuilder.() -> Unit): CodeBlock = add(this, builder)
}

sealed class MemberContainerDelegate {

    abstract fun add(name: String, format: String, vararg args: Any)

    abstract fun add(name: String, block: CodeBlock): CodeBlock

    fun add(name: String, builder: CodeBlockBuilder.() -> Unit): CodeBlock = add(name, CodeBlockBuilder.of(builder))

    operator fun set(name: String, format: String) {
        add(name, format)
    }

    operator fun set(name: String, block: CodeBlock) {
        add(name, block)
    }
}