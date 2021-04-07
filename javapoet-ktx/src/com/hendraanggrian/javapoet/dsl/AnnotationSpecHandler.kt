package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.hendraanggrian.javapoet.SpecDslMarker
import com.hendraanggrian.javapoet.annotationSpecOf
import com.hendraanggrian.javapoet.buildAnnotationSpec
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import kotlin.reflect.KClass

/** An [AnnotationSpecHandler] is responsible for managing a set of annotation instances. */
open class AnnotationSpecHandler(actualList: MutableList<AnnotationSpec>) : MutableList<AnnotationSpec> by actualList {

    /** Add annotation from [ClassName]. */
    fun add(type: ClassName): Boolean = add(annotationSpecOf(type))

    /** Add annotation from [ClassName] with custom initialization [configuration]. */
    fun add(type: ClassName, configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationSpec(type, configuration))

    /** Add annotation from [Class]. */
    fun add(type: Class<*>): Boolean = add(annotationSpecOf(type))

    /** Add annotation from [Class] with custom initialization [configuration]. */
    fun add(type: Class<*>, configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationSpec(type, configuration))

    /** Add annotation from [KClass]. */
    fun add(type: KClass<*>): Boolean = add(annotationSpecOf(type))

    /** Add annotation from [KClass] with custom initialization [configuration]. */
    fun add(type: KClass<*>, configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationSpec(type, configuration))

    /** Add annotation from [T]. */
    inline fun <reified T> add(): Boolean = add(annotationSpecOf<T>())

    /** Add annotation from [T] with custom initialization [configuration]. */
    inline fun <reified T> add(noinline configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationSpec<T>(configuration))

    /** Convenient method to add annotation with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun plusAssign(type: ClassName) {
        add(type)
    }

    /** Convenient method to add annotation with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun plusAssign(type: Class<*>) {
        add(type)
    }

    /** Convenient method to add annotation with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun plusAssign(type: KClass<*>) {
        add(type)
    }
}

/** Receiver for the `annotations` block providing an extended set of operators for the configuration. */
@SpecDslMarker
class AnnotationSpecHandlerScope(actualList: MutableList<AnnotationSpec>) : AnnotationSpecHandler(actualList) {

    /** @see AnnotationSpecHandler.add */
    operator fun ClassName.invoke(configuration: AnnotationSpecBuilder.() -> Unit): Boolean = add(this, configuration)

    /** @see AnnotationSpecHandler.add */
    operator fun Class<*>.invoke(configuration: AnnotationSpecBuilder.() -> Unit): Boolean = add(this, configuration)

    /** @see AnnotationSpecHandler.add */
    operator fun KClass<*>.invoke(configuration: AnnotationSpecBuilder.() -> Unit): Boolean = add(this, configuration)
}
