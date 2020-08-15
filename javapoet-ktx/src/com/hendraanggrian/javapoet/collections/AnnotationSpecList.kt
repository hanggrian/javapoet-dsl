package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.hendraanggrian.javapoet.SpecDslMarker
import com.hendraanggrian.javapoet.annotationSpecOf
import com.hendraanggrian.javapoet.buildAnnotationSpec
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import kotlin.reflect.KClass

/** An [AnnotationSpecList] is responsible for managing a set of annotation instances. */
open class AnnotationSpecList internal constructor(actualList: MutableList<AnnotationSpec>) :
    MutableList<AnnotationSpec> by actualList {

    /** Add annotation from [ClassName]. */
    fun add(type: ClassName): Boolean = add(annotationSpecOf(type))

    /** Add annotation from [Class]. */
    fun add(type: Class<*>): Boolean = add(annotationSpecOf(type))

    /** Add annotation from [KClass]. */
    fun add(type: KClass<*>): Boolean = add(annotationSpecOf(type))

    /** Add annotation from [T]. */
    inline fun <reified T> add(): Boolean = add(annotationSpecOf<T>())

    /** Add annotation from [ClassName] with custom initialization [builderAction]. */
    inline fun add(
        type: ClassName,
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): Boolean = add(buildAnnotationSpec(type, builderAction))

    /** Add annotation from [Class] with custom initialization [builderAction]. */
    inline fun add(
        type: Class<*>,
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): Boolean = add(buildAnnotationSpec(type, builderAction))

    /** Add annotation from [KClass] with custom initialization [builderAction]. */
    inline fun add(
        type: KClass<*>,
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): Boolean = add(buildAnnotationSpec(type, builderAction))

    /** Add annotation from [T] with custom initialization [builderAction]. */
    inline fun <reified T> add(
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): Boolean = add(buildAnnotationSpec<T>(builderAction))

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(type: ClassName): Unit = plusAssign(annotationSpecOf(type))

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(type: Class<*>): Unit = plusAssign(annotationSpecOf(type))

    /** Convenient method to add annotation with operator function. */
    operator fun plusAssign(type: KClass<*>): Unit = plusAssign(annotationSpecOf(type))
}

/** Receiver for the `annotations` function type providing an extended set of operators for the configuration. */
@SpecDslMarker
class AnnotationSpecListScope(actualList: MutableList<AnnotationSpec>) : AnnotationSpecList(actualList) {

    /** Convenient method to add annotation with receiver type. */
    inline operator fun ClassName.invoke(
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): Boolean = add(this, builderAction)

    /** Convenient method to add annotation with receiver type. */
    inline operator fun Class<*>.invoke(
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): Boolean = add(this, builderAction)

    /** Convenient method to add annotation with receiver type. */
    inline operator fun KClass<*>.invoke(
        builderAction: AnnotationSpecBuilder.() -> Unit
    ): Boolean = add(this, builderAction)
}
