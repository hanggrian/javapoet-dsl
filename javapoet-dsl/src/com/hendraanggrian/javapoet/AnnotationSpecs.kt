package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock

fun buildAnnotationSpec(type: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpecBuilder =
    AnnotationSpecBuilderImpl(AnnotationSpec.builder(type)).also { builder?.invoke(it) }

fun buildAnnotationSpec(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpecBuilder =
    AnnotationSpecBuilderImpl(AnnotationSpec.builder(type)).also { builder?.invoke(it) }

interface AnnotationSpecBuilder : SpecBuilder<AnnotationSpec> {

    val nativeBuilder: AnnotationSpec.Builder

    fun member(name: String, format: String, vararg args: Any) {
        nativeBuilder.addMember(name, format, *args)
    }

    fun member(name: String, block: CodeBlock) {
        nativeBuilder.addMember(name, block)
    }

    override fun build(): AnnotationSpec = nativeBuilder.build()
}

internal class AnnotationSpecBuilderImpl(override val nativeBuilder: AnnotationSpec.Builder) : AnnotationSpecBuilder