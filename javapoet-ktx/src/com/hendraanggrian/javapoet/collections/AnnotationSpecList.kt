package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.hendraanggrian.javapoet.SpecMarker
import com.hendraanggrian.javapoet.buildAnnotationSpec
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import kotlin.reflect.KClass

/** An [AnnotationSpecList] is responsible for managing a set of annotation instances. */
open class AnnotationSpecList internal constructor(actualList: MutableList<AnnotationSpec>) :
    MutableList<AnnotationSpec> by actualList {

    /** Add annotation from [ClassName]. */
    fun add(type: ClassName): Boolean = add(AnnotationSpec.builder(type).build())

    /** Add annotation from [ClassName] with custom initialization [configuration]. */
    fun add(type: ClassName, configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationSpec(type, configuration))

    /** Add annotation from [Class]. */
    fun add(type: Class<*>): Boolean = add(AnnotationSpec.builder(type).build())

    /** Add annotation from [Class] with custom initialization [configuration]. */
    fun add(type: Class<*>, configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationSpec(type, configuration))

    /** Add annotation from [KClass]. */
    fun add(type: KClass<*>): Boolean = add(AnnotationSpec.builder(type.java).build())

    /** Add annotation from [KClass] with custom initialization [configuration]. */
    fun add(type: KClass<*>, configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationSpec(type, configuration))

    /** Add annotation from [T]. */
    inline fun <reified T> add(): Boolean = add(AnnotationSpec.builder(T::class.java).build())

    /** Add annotation from [T] with custom initialization [configuration]. */
    inline fun <reified T> add(noinline configuration: AnnotationSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationSpec<T>(configuration))

    /** Convenient method to add annotation with operator function. */
    inline operator fun plusAssign(type: ClassName) {
        add(type)
    }

    /** Convenient method to add annotation with operator function. */
    inline operator fun plusAssign(type: Class<*>) {
        add(type)
    }

    /** Convenient method to add annotation with operator function. */
    inline operator fun plusAssign(type: KClass<*>) {
        add(type)
    }
}

/** Receiver for the `annotations` block providing an extended set of operators for the configuration. */
@SpecMarker
class AnnotationSpecListScope internal constructor(actualList: MutableList<AnnotationSpec>) :
    AnnotationSpecList(actualList) {

    /** @see AnnotationSpecList.add */
    operator fun ClassName.invoke(configuration: AnnotationSpecBuilder.() -> Unit): Boolean = add(this, configuration)

    /** @see AnnotationSpecList.add */
    operator fun Class<*>.invoke(configuration: AnnotationSpecBuilder.() -> Unit): Boolean = add(this, configuration)

    /** @see AnnotationSpecList.add */
    operator fun KClass<*>.invoke(configuration: AnnotationSpecBuilder.() -> Unit): Boolean = add(this, configuration)
}
