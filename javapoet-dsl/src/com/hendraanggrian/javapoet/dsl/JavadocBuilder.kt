package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.SpecBuilderDslMarker
import com.hendraanggrian.javapoet.buildCodeBlock
import com.squareup.javapoet.CodeBlock

@SpecBuilderDslMarker
abstract class JavadocBuilder {

    abstract fun add(block: CodeBlock)

    abstract fun add(format: String, vararg args: Any)

    fun add(builder: CodeBlockBuilder.() -> Unit) =
        add(buildCodeBlock(builder))
}