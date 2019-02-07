package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock

/** Returns an annotation with custom initialization block. */
fun buildAnnotationSpec(type: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
    AnnotationSpecBuilderImpl(AnnotationSpec.builder(type))
        .also { builder?.invoke(it) }
        .build()

/** Returns an annotation with custom initialization block. */
fun buildAnnotationSpec(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
    AnnotationSpecBuilderImpl(AnnotationSpec.builder(type))
        .also { builder?.invoke(it) }
        .build()

interface AnnotationSpecBuilder {

    val nativeBuilder: AnnotationSpec.Builder

    fun member(name: String, format: String, vararg args: Any) {
        nativeBuilder.addMember(name, format, *args)
    }

    fun member(name: String, block: CodeBlock) {
        nativeBuilder.addMember(name, block)
    }

    fun member(name: String, builder: CodeBlockBuilder.() -> Unit) = member(name, buildCodeBlock(builder))

    fun build(): AnnotationSpec = nativeBuilder.build()
}

internal class AnnotationSpecBuilderImpl(override val nativeBuilder: AnnotationSpec.Builder) : AnnotationSpecBuilder