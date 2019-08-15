package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import javax.lang.model.element.AnnotationMirror
import kotlin.reflect.KClass

fun Annotation.toAnnotationSpec(includeDefaultValues: Boolean = false): AnnotationSpec =
    AnnotationSpec.get(this, includeDefaultValues)

fun AnnotationMirror.toAnnotationSpec(): AnnotationSpec =
    AnnotationSpec.get(this)

fun ClassName.toAnnotationSpec(): AnnotationSpec =
    AnnotationSpec.builder(this).build()

inline fun buildAnnotationSpec(type: ClassName, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(builderAction).build()

fun KClass<*>.toAnnotationSpec(): AnnotationSpec =
    AnnotationSpec.builder(java).build()

inline fun buildAnnotationSpec(type: KClass<*>, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(type.java)).apply(builderAction).build()

inline fun <reified T> buildAnnotationSpec(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    buildAnnotationSpec(T::class, builderAction)

/** Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDslMarker
class AnnotationSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: AnnotationSpec.Builder) {

    fun addMember(name: String, format: String, vararg args: Any) {
        format(format, args) { s, array ->
            nativeBuilder.addMember(name, s, *array)
        }
    }

    fun addMember(name: String, block: CodeBlock): CodeBlock =
        block.also { nativeBuilder.addMember(name, it) }

    inline fun addMember(name: String, builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        addMember(name, buildCodeBlock(builderAction))

    operator fun String.invoke(format: String, vararg args: Any) =
        addMember(this, format, *args)

    operator fun String.invoke(builderAction: CodeBlockBuilder.() -> Unit) =
        addMember(this, builderAction)

    fun build(): AnnotationSpec =
        nativeBuilder.build()
}
