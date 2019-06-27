package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.buildCodeBlock
import com.squareup.javapoet.CodeBlock

abstract class MemberContainer {

    abstract fun add(name: String, block: CodeBlock)

    abstract fun add(name: String, format: String, vararg args: Any)

    fun add(name: String, builder: CodeBlockBuilder.() -> Unit) =
        add(name, buildCodeBlock(builder))

    operator fun invoke(configuration: MemberContainerScope.() -> Unit) =
        configuration(MemberContainerScope(this))
}

@JavapoetDslMarker
class MemberContainerScope internal constructor(private val container: MemberContainer) {

    operator fun String.invoke(format: String, vararg args: Any) {
        container.add(this, format, *args)
    }

    operator fun String.invoke(builder: CodeBlockBuilder.() -> Unit) {
        container.add(this, buildCodeBlock(builder))
    }
}