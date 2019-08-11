package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.hendraanggrian.javapoet.invoke
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
        add(AnnotationSpec.builder(type).build())

    /** Add annotation from [type] with custom initialization [builder], returning the annotation added. */
    inline fun add(type: ClassName, builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(AnnotationSpec.builder(type)(builder))

    /** Add annotation from [type], returning the annotation added. */
    fun add(type: KClass<*>): AnnotationSpec =
        add(AnnotationSpec.builder(type.java).build())

    /** Add annotation from [type] with custom initialization [builder], returning the annotation added. */
    inline fun add(type: KClass<*>, builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(AnnotationSpec.builder(type.java)(builder))

    /** Add annotation from reified [T], returning the annotation added. */
    inline fun <reified T> add(): AnnotationSpec =
        add(T::class)

    /** Add annotation from reified [T] with custom initialization [builder], returning the annotation added. */
    inline fun <reified T> add(builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(T::class, builder)

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
    inline operator fun ClassName.invoke(builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builder)

    /** Convenient method to add annotation with receiver type. */
    inline operator fun KClass<*>.invoke(builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builder)
}
