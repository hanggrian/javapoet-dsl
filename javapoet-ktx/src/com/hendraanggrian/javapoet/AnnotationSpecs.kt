package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import javax.lang.model.element.AnnotationMirror
import kotlin.reflect.KClass

/** Converts annotation to [AnnotationSpec]. */
fun Annotation.toAnnotation(includeDefaultValues: Boolean = false): AnnotationSpec =
    AnnotationSpec.get(this, includeDefaultValues)

/** Converts mirror to [AnnotationSpec]. */
fun AnnotationMirror.toAnnotation(): AnnotationSpec =
    AnnotationSpec.get(this)

/** Builds a new [AnnotationSpec] from [type]. */
fun buildAnnotation(type: ClassName): AnnotationSpec =
    AnnotationSpec.builder(type).build()

/**
 * Builds a new [AnnotationSpec] from [type],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnnotation(type: ClassName, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(builderAction).build()

/** Builds a new [AnnotationSpec] from [type]. */
fun buildAnnotation(type: Class<*>): AnnotationSpec =
    AnnotationSpec.builder(type).build()

/** Builds a new [AnnotationSpec] from [type]. */
fun buildAnnotation(type: KClass<*>): AnnotationSpec =
    buildAnnotation(type.java)

/** Builds a new [AnnotationSpec] from [T]. */
inline fun <reified T> buildAnnotation(): AnnotationSpec =
    buildAnnotation(T::class.java)

/**
 * Builds a new [AnnotationSpec] from [type],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnnotation(type: Class<*>, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(builderAction).build()

/**
 * Builds a new [AnnotationSpec] from [type],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnnotation(type: KClass<*>, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    buildAnnotation(type.java, builderAction)

/**
 * Builds a new [AnnotationSpec] from [T],
 * by populating newly created [AnnotationSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun <reified T> buildAnnotation(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    buildAnnotation(T::class, builderAction)

/** Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDslMarker
class AnnotationSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: AnnotationSpec.Builder) {

    /** Add code as a member of this annotation. */
    fun addMember(name: String, format: String, vararg args: Any): Unit =
        format.formatWith(args) { s, array -> nativeBuilder.addMember(name, s, *array) }

    /** Add code as a member of this annotation. */
    fun addMember(name: String, block: CodeBlock): CodeBlock =
        block.also { nativeBuilder.addMember(name, it) }

    /** Add code as a member of this annotation with custom initialization [builderAction]. */
    inline fun addMember(name: String, builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        addMember(name, buildCode(builderAction))

    /** Convenient method to add member with operator function. */
    operator fun String.invoke(format: String, vararg args: Any) =
        addMember(this, format, *args)

    /** Convenient method to add member with operator function. */
    operator fun String.invoke(builderAction: CodeBlockBuilder.() -> Unit) =
        addMember(this, builderAction)

    /** Returns native spec. */
    fun build(): AnnotationSpec =
        nativeBuilder.build()
}
