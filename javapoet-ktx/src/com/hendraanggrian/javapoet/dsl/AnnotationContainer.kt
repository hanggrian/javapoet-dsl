package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.AnnotationSpecs
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import kotlin.reflect.KClass

internal interface AnnotationCollection {

    /** Add annotation to this container, returning the annotation added. */
    fun add(spec: AnnotationSpec): AnnotationSpec
}

/** An [AnnotationContainer] is responsible for managing a set of annotation instances. */
abstract class AnnotationContainer internal constructor() : AnnotationCollection {

    /** Add annotation from [type], returning the annotation added. */
    fun add(type: ClassName): AnnotationSpec =
        add(AnnotationSpecs.of(type))

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun add(type: ClassName, builderAction: AnnotationSpecs.Builder.() -> Unit): AnnotationSpec =
        add(AnnotationSpecs.of(type, builderAction))

    /** Add annotation from [type], returning the annotation added. */
    fun add(type: KClass<*>): AnnotationSpec =
        add(AnnotationSpecs.of(type))

    /** Add annotation from [type] with custom initialization [builderAction], returning the annotation added. */
    inline fun add(type: KClass<*>, builderAction: AnnotationSpecs.Builder.() -> Unit): AnnotationSpec =
        add(AnnotationSpecs.of(type, builderAction))

    /** Add annotation from reified [T], returning the annotation added. */
    inline fun <reified T> add(): AnnotationSpec =
        add(AnnotationSpecs.of<T>())

    /** Add annotation from reified [T] with custom initialization [builderAction], returning the annotation added. */
    inline fun <reified T> add(builderAction: AnnotationSpecs.Builder.() -> Unit): AnnotationSpec =
        add(AnnotationSpecs.of<T>(builderAction))

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
class AnnotationContainerScope @PublishedApi internal constructor(collection: AnnotationCollection) :
    AnnotationContainer(), AnnotationCollection by collection {

    /** Convenient method to add annotation with receiver type. */
    inline operator fun ClassName.invoke(builderAction: AnnotationSpecs.Builder.() -> Unit): AnnotationSpec =
        add(this, builderAction)

    /** Convenient method to add annotation with receiver type. */
    inline operator fun KClass<*>.invoke(builderAction: AnnotationSpecs.Builder.() -> Unit): AnnotationSpec =
        add(this, builderAction)
}
