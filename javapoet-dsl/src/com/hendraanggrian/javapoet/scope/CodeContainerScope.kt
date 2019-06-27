package com.hendraanggrian.javapoet.scope

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.buildCodeBlock
import com.hendraanggrian.javapoet.container.CodeContainer

@JavapoetDslMarker
class CodeContainerScope internal constructor(private val container: CodeContainer) {

    fun add(format: String, vararg args: Any) {
        container.add(format, *args)
    }

    fun add(builder: CodeBlockBuilder.() -> Unit) {
        container += buildCodeBlock(builder)
    }
}