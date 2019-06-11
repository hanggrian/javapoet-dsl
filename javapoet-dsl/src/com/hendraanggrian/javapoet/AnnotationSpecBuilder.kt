package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.SpecBuilder
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock

/** Returns an annotation with custom initialization block. */
fun buildAnnotationSpec(name: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(name))
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

@SpecBuilderDslMarker
class AnnotationSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: AnnotationSpec.Builder) :
    SpecBuilder<AnnotationSpec>() {

    fun member(name: String, format: String, vararg args: Any) {
        nativeBuilder.addMember(name, format, *args)
    }

    fun member(name: String, block: CodeBlock) {
        nativeBuilder.addMember(name, block)
    }

    inline fun member(name: String, builder: CodeBlockBuilder.() -> Unit) = member(name, buildCodeBlock(builder))

    override fun build(): AnnotationSpec = nativeBuilder.build()
}