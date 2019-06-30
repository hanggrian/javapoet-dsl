package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.squareup.javapoet.CodeBlock

abstract class MemberContainer internal constructor() : MemberContainerDelegate {

    inline operator fun invoke(configuration: MemberContainerScope.() -> Unit) =
        configuration(MemberContainerScope(this))
}

@JavapoetDslMarker
@Suppress("NOTHING_TO_INLINE")
class MemberContainerScope @PublishedApi internal constructor(private val container: MemberContainer) :
    MemberContainerDelegate by container {

    inline operator fun String.invoke(noinline builder: CodeBlockBuilder.() -> Unit): CodeBlock = add(this, builder)
}

interface MemberContainerDelegate {

    fun add(name: String, format: String, vararg args: Any)

    fun add(name: String, block: CodeBlock): CodeBlock

    fun add(name: String, builder: CodeBlockBuilder.() -> Unit): CodeBlock = add(name, CodeBlockBuilder.of(builder))

    operator fun set(name: String, format: String) {
        add(name, format)
    }

    operator fun set(name: String, block: CodeBlock) {
        add(name, block)
    }
}