package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import javax.lang.model.element.AnnotationMirror
import kotlin.reflect.KClass

object AnnotationSpecs {

    operator fun get(annotation: Annotation, includeDefaultValues: Boolean = false): AnnotationSpec =
        AnnotationSpec.get(annotation, includeDefaultValues)

    operator fun get(annotation: AnnotationMirror): AnnotationSpec =
        AnnotationSpec.get(annotation)

    fun of(type: ClassName): AnnotationSpec =
        AnnotationSpec.builder(type).build()

    inline fun of(type: ClassName, builderAction: Builder.() -> Unit): AnnotationSpec =
        Builder(AnnotationSpec.builder(type)).apply(builderAction).build()

    fun of(type: KClass<*>): AnnotationSpec =
        AnnotationSpec.builder(type.java).build()

    inline fun of(type: KClass<*>, builderAction: Builder.() -> Unit): AnnotationSpec =
        Builder(AnnotationSpec.builder(type.java)).apply(builderAction).build()

    inline fun <reified T> of(): AnnotationSpec =
        of(T::class)

    inline fun <reified T> of(builderAction: Builder.() -> Unit): AnnotationSpec =
        of(T::class, builderAction)

    /** Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder. */
    @JavapoetDslMarker
    class Builder @PublishedApi internal constructor(private val nativeBuilder: AnnotationSpec.Builder) {

        fun addMember(name: String, format: String, vararg args: Any) {
            format(format, args) { s, array ->
                nativeBuilder.addMember(name, s, *array)
            }
        }

        fun addMember(name: String, block: CodeBlock): CodeBlock =
            block.also { nativeBuilder.addMember(name, it) }

        inline fun addMember(name: String, builderAction: CodeBlocks.Builder.() -> Unit): CodeBlock =
            addMember(name, CodeBlocks.of(builderAction))

        operator fun String.invoke(format: String, vararg args: Any) =
            addMember(this, format, *args)

        operator fun String.invoke(builderAction: CodeBlocks.Builder.() -> Unit) =
            addMember(this, builderAction)

        fun build(): AnnotationSpec =
            nativeBuilder.build()
    }
}
