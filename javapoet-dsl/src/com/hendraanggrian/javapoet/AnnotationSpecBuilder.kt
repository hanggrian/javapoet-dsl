package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName

/** Returns an annotation with custom initialization block. */
fun buildAnnotationSpec(type: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(type))
        .also { builder?.invoke(it) }
        .build()

/** Returns an annotation with custom initialization block. */
fun buildAnnotationSpec(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(type))
        .also { builder?.invoke(it) }
        .build()

/** Returns an annotation with custom initialization block. */
inline fun <reified T> buildAnnotationSpec(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
    buildAnnotationSpec(T::class.java, builder)

class AnnotationSpecBuilder(private val nativeBuilder: AnnotationSpec.Builder) {

    fun member(name: String, format: String, vararg args: Any) {
        nativeBuilder.addMember(name, format, *args)
    }

    fun member(name: String, builder: CodeBlockBuilder.() -> Unit) {
        nativeBuilder.addMember(name, buildCodeBlock(builder))
    }

    fun build(): AnnotationSpec = nativeBuilder.build()
}