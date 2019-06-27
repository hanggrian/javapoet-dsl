package com.hendraanggrian.javapoet.container

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.buildCodeBlock
import com.hendraanggrian.javapoet.scope.MemberContainerScope
import com.squareup.javapoet.CodeBlock

abstract class MemberContainer {

    abstract fun add(name: String, block: CodeBlock)

    abstract fun add(name: String, format: String, vararg args: Any)

    fun add(name: String, builder: CodeBlockBuilder.() -> Unit) =
        add(name, buildCodeBlock(builder))

    operator fun invoke(configuration: MemberContainerScope.() -> Unit) =
        configuration(MemberContainerScope(this))
}