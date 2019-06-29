@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.buildCodeBlock
import com.squareup.javapoet.CodeBlock

abstract class MemberContainer internal constructor() : MemberContainerDelegate {

    inline operator fun set(name: String, format: String) {
        add(name, format)
    }

    inline operator fun set(name: String, block: CodeBlock) {
        add(name, block)
    }

    inline operator fun invoke(configuration: MemberContainerScope.() -> Unit) =
        configuration(MemberContainerScope(this))
}

@JavapoetDslMarker
class MemberContainerScope @PublishedApi internal constructor(private val container: MemberContainer) :
    MemberContainerDelegate {

    override fun add(name: String, format: String, vararg args: Any) = container.add(name, format, *args)

    override fun add(name: String, block: CodeBlock): CodeBlock = container.add(name, block)

    inline operator fun String.invoke(noinline builder: CodeBlockBuilder.() -> Unit): CodeBlock = add(this, builder)
}

internal interface MemberContainerDelegate {

    fun add(name: String, format: String, vararg args: Any)

    fun add(name: String, block: CodeBlock): CodeBlock

    fun add(name: String, builder: CodeBlockBuilder.() -> Unit): CodeBlock = add(name, buildCodeBlock(builder))
}