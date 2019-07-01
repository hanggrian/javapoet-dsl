@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import kotlin.reflect.KClass

@JavapoetDslMarker
class AnnotationContainerScope @PublishedApi internal constructor(container: AnnotationContainer) :
    AnnotationContainer by container {

    /** Convenient method to add annotation with receiver. */
    inline operator fun ClassName.invoke(noinline builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builder)

    /** Convenient method to add annotation with receiver. */
    inline operator fun KClass<*>.invoke(noinline builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builder)
}

interface AnnotationContainer {

    /** Add spec to this container, returning the spec added. */
    fun add(spec: AnnotationSpec): AnnotationSpec

    /** Add annotation with [name] and [builder] configuration, returning the spec added. */
    fun add(name: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
        add(AnnotationSpecBuilder.of(name, builder))

    /** Add annotation with [type] and [builder] configuration, returning the spec added. */
    fun add(type: KClass<*>, builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
        add(AnnotationSpecBuilder.of(type, builder))
}

/** Configures the annotations of this container. */
inline operator fun AnnotationContainer.invoke(configuration: AnnotationContainerScope.() -> Unit) =
    AnnotationContainerScope(this).configuration()

/** Convenient method to add annotation with reified type. */
inline fun <reified T> AnnotationContainer.add(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
    add(T::class, builder)

/** Convenient method to add annotation with plus operator. */
inline operator fun AnnotationContainer.plusAssign(spec: AnnotationSpec) {
    add(spec)
}

/** Convenient method to add annotation with plus operator. */
inline operator fun AnnotationContainer.plusAssign(type: ClassName) {
    add(type)
}

/** Convenient method to add annotation with plus operator. */
inline operator fun AnnotationContainer.plusAssign(type: KClass<*>) {
    add(type)
}