package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.annotationSpecOf
import com.hendraanggrian.javapoet.buildAnnotationSpec
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import kotlin.reflect.KClass

/** An [AnnotationSpecContainer] is responsible for managing a set of annotation instances. */
abstract class AnnotationSpecContainer {

    /** Add collection of annotations to this container. */
    abstract fun addAll(specs: Iterable<AnnotationSpec>): Boolean

    /** Add annotation to this container. */
    abstract fun add(spec: AnnotationSpec)

    /** Add annotation from [type], returning the annotation added. */
    fun add(type: ClassName): AnnotationSpec = annotationSpecOf(type).also { add(it) }

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun add(type: ClassName, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        buildAnnotationSpec(type, builderAction).also { add(it) }

    /** Add annotation from [type], returning the annotation added. */
    fun add(type: Class<*>): AnnotationSpec = annotationSpecOf(type).also { add(it) }

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun add(type: Class<*>, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        buildAnnotationSpec(type, builderAction).also { add(it) }

    /** Add annotation from [type], returning the annotation added. */
    fun add(type: KClass<*>): AnnotationSpec = annotationSpecOf(type).also { add(it) }

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun add(type: KClass<*>, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        buildAnnotationSpec(type, builderAction).also { add(it) }

    /** Add annotation from reified [T], returning the annotation added. */
    inline fun <reified T> add(): AnnotationSpec = annotationSpecOf<T>().also { add(it) }

    /** Add annotation from reified [T] with custom initialization [builderAction], returning the annotation added. */
    inline fun <reified T> add(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        buildAnnotationSpec<T>(builderAction).also { add(it) }

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(spec: AnnotationSpec) = add(spec)

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(type: ClassName) {
        add(type)
    }

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(type: Class<*>) {
        add(type)
    }

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(type: KClass<*>) {
        add(type)
    }
}

/** Receiver for the `annotations` function type providing an extended set of operators for the configuration. */
@JavapoetDslMarker
class AnnotationSpecContainerScope(private val container: AnnotationSpecContainer) : AnnotationSpecContainer() {

    override fun addAll(specs: Iterable<AnnotationSpec>): Boolean = container.addAll(specs)
    override fun add(spec: AnnotationSpec): Unit = container.add(spec)

    /** Convenient method to add annotation with receiver type. */
    inline operator fun ClassName.invoke(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builderAction)

    /** Convenient method to add annotation with receiver type. */
    inline operator fun Class<*>.invoke(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builderAction)

    /** Convenient method to add annotation with receiver type. */
    inline operator fun KClass<*>.invoke(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builderAction)
}
