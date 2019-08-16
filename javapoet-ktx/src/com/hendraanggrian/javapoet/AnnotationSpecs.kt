package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import javax.lang.model.element.AnnotationMirror
import kotlin.reflect.KClass

fun Annotation.toAnnotation(includeDefaultValues: Boolean = false): AnnotationSpec =
    AnnotationSpec.get(this, includeDefaultValues)

fun AnnotationMirror.toAnnotation(): AnnotationSpec =
    AnnotationSpec.get(this)

fun ClassName.toAnnotation(): AnnotationSpec =
    AnnotationSpec.builder(this).build()

inline fun buildAnnotation(type: ClassName, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(builderAction).build()

fun KClass<*>.toAnnotation(): AnnotationSpec =
    AnnotationSpec.builder(java).build()

inline fun buildAnnotation(type: KClass<*>, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(type.java)).apply(builderAction).build()

inline fun <reified T> buildAnnotation(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    buildAnnotation(T::class, builderAction)

/** Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDslMarker
class AnnotationSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: AnnotationSpec.Builder) {

    fun addMember(name: String, format: String, vararg args: Any) {
        format(format, args) { s, array -> nativeBuilder.addMember(name, s, *array) }
    }

    fun addMember(name: String, block: CodeBlock): CodeBlock =
        block.also { nativeBuilder.addMember(name, it) }

    inline fun addMember(name: String, builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        addMember(name, buildCode(builderAction))

    operator fun String.invoke(format: String, vararg args: Any) =
        addMember(this, format, *args)

    operator fun String.invoke(builderAction: CodeBlockBuilder.() -> Unit) =
        addMember(this, builderAction)

    fun build(): AnnotationSpec =
        nativeBuilder.build()
}
