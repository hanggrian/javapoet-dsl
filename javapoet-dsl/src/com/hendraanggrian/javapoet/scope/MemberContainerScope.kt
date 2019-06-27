package com.hendraanggrian.javapoet.scope

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.buildCodeBlock
import com.hendraanggrian.javapoet.container.MemberContainer

@JavapoetDslMarker
class MemberContainerScope internal constructor(private val container: MemberContainer) {

    operator fun String.invoke(format: String, vararg args: Any) {
        container.add(this, format, *args)
    }

    operator fun String.invoke(builder: CodeBlockBuilder.() -> Unit) {
        container.add(this, buildCodeBlock(builder))
    }
}