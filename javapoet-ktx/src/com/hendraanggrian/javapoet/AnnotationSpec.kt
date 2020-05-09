package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import javax.lang.model.element.AnnotationMirror
import kotlin.reflect.KClass

/** Converts annotation to [AnnotationSpec]. */
fun Annotation.asAnnotationSpec(includeDefaultValues: Boolean = false): AnnotationSpec =
    AnnotationSpec.get(this, includeDefaultValues)

/** Converts mirror to [AnnotationSpec]. */
fun AnnotationMirror.asAnnotationSpec(): AnnotationSpec =
    AnnotationSpec.get(this)

/** Builds a new [AnnotationSpec] from [type]. */
fun annotationSpecOf(type: ClassName): AnnotationSpec =
    AnnotationSpec.builder(type).build()

/**
 * Builds a new [AnnotationSpec] from [type],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnnotationSpec(type: ClassName, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpec.builder(type).build(builderAction)

/** Builds a new [AnnotationSpec] from [type]. */
fun annotationSpecOf(type: Class<*>): AnnotationSpec =
    AnnotationSpec.builder(type).build()

/** Builds a new [AnnotationSpec] from [type]. */
fun annotationSpecOf(type: KClass<*>): AnnotationSpec =
    annotationSpecOf(type.java)

/** Builds a new [AnnotationSpec] from [T]. */
inline fun <reified T> annotationSpecOf(): AnnotationSpec =
    annotationSpecOf(T::class)

/**
 * Builds a new [AnnotationSpec] from [type],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnnotationSpec(type: Class<*>, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpec.builder(type).build(builderAction)

/**
 * Builds a new [AnnotationSpec] from [type],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnnotationSpec(type: KClass<*>, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    buildAnnotationSpec(type.java, builderAction)

/**
 * Builds a new [AnnotationSpec] from [T],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun <reified T> buildAnnotationSpec(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    buildAnnotationSpec(T::class, builderAction)

/** Modify existing [AnnotationSpec.Builder] using provided [builderAction] and then building it. */
inline fun AnnotationSpec.Builder.build(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(this).apply(builderAction).build()

/** Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDslMarker
class AnnotationSpecBuilder(private val nativeBuilder: AnnotationSpec.Builder) {

    /** Add code as a member of this annotation. */
    fun addMember(name: String, format: String, vararg args: Any): Unit =
        format.formatWith(args) { s, array -> nativeBuilder.addMember(name, s, *array) }

    /** Add code as a member of this annotation. */
    fun addMember(name: String, code: CodeBlock): CodeBlock = code.also { nativeBuilder.addMember(name, it) }

    /** Add code as a member of this annotation with custom initialization [builderAction]. */
    inline fun addMember(name: String, builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        addMember(name, buildCodeBlock(builderAction))

    /** Convenient method to add member with operator function. */
    operator fun String.invoke(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        addMember(this, builderAction)

    /** Returns native spec. */
    fun build(): AnnotationSpec = nativeBuilder.build()
}
