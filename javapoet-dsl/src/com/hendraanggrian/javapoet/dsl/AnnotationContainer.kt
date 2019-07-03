@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.hendraanggrian.javapoet.buildAnnotationSpec
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import kotlin.reflect.KClass

internal interface AnnotationCollection {

    fun add(spec: AnnotationSpec): AnnotationSpec

    fun add(name: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
        add(buildAnnotationSpec(name, builder))

    fun add(type: KClass<*>, builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
        add(buildAnnotationSpec(type, builder))
}

/** An [AnnotationContainer] is responsible for managing a set of annotation instances. */
abstract class AnnotationContainer internal constructor() : AnnotationCollection {

    inline fun <reified T> add(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
        add(buildAnnotationSpec<T>(builder))

    inline operator fun plusAssign(spec: AnnotationSpec) {
        add(spec)
    }

    inline operator fun plusAssign(type: ClassName) {
        add(type)
    }

    inline operator fun plusAssign(type: KClass<*>) {
        add(type)
    }

    inline operator fun invoke(configuration: AnnotationContainerScope.() -> Unit) =
        AnnotationContainerScope(this).configuration()
}

/**
 * Receiver for the `annotations` block providing an extended set of operators for the configuration.
 */
class AnnotationContainerScope @PublishedApi internal constructor(collection: AnnotationCollection) :
    AnnotationContainer(), AnnotationCollection by collection {

    inline operator fun ClassName.invoke(noinline builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builder)

    inline operator fun KClass<*>.invoke(noinline builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builder)
}