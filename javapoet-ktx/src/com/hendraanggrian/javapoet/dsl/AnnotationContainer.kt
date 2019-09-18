package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.buildAnnotation
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import kotlin.reflect.KClass

private interface AnnotationAddable {

    /** Add annotation to this container, returning the annotation added. */
    fun add(spec: AnnotationSpec): AnnotationSpec
}

/** An [AnnotationContainer] is responsible for managing a set of annotation instances. */
abstract class AnnotationContainer internal constructor() : AnnotationAddable {

    /** Add annotation from [type], returning the annotation added. */
    fun add(type: ClassName): AnnotationSpec =
        add(buildAnnotation(type))

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun add(type: ClassName, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(buildAnnotation(type, builderAction))

    /** Add annotation from [type], returning the annotation added. */
    fun add(type: Class<*>): AnnotationSpec =
        add(buildAnnotation(type))

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun add(type: Class<*>, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(buildAnnotation(type, builderAction))

    /** Add annotation from [type], returning the annotation added. */
    fun add(type: KClass<*>): AnnotationSpec =
        add(buildAnnotation(type))

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun add(type: KClass<*>, builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(buildAnnotation(type, builderAction))

    /** Add annotation from reified [T], returning the annotation added. */
    inline fun <reified T> add(): AnnotationSpec =
        add(buildAnnotation<T>())

    /** Add annotation from reified [T] with custom initialization [builderAction], returning the annotation added. */
    inline fun <reified T> add(builderAction: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(buildAnnotation<T>(builderAction))

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(spec: AnnotationSpec) {
        add(spec)
    }

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(type: ClassName) {
        add(type)
    }

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(type: KClass<*>) {
        add(type)
    }

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: AnnotationContainerScope.() -> Unit) =
        AnnotationContainerScope(this).configuration()
}

/** Receiver for the `annotations` block providing an extended set of operators for the configuration. */
@JavapoetDslMarker
class AnnotationContainerScope @PublishedApi internal constructor(container: AnnotationContainer) :
    AnnotationContainer(), AnnotationAddable by container {

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
