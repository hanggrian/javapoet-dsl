package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock

interface AnnotationSpecBuilder {

    val nativeBuilder: AnnotationSpec.Builder

    fun member(name: String, format: String, vararg args: Any) {
        nativeBuilder.addMember(name, format, *args)
    }

    fun member(name: String, block: CodeBlock) {
        nativeBuilder.addMember(name, block)
    }
}

internal class AnnotationSpecBuilderImpl(override val nativeBuilder: AnnotationSpec.Builder) : AnnotationSpecBuilder