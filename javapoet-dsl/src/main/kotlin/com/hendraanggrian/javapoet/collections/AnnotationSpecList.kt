package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.hendraanggrian.javapoet.JavapoetSpecMarker
import com.hendraanggrian.javapoet.buildAnnotationSpec
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/** An [AnnotationSpecList] is responsible for managing a set of annotation instances. */
@OptIn(ExperimentalContracts::class)
open class AnnotationSpecList internal constructor(actualList: MutableList<AnnotationSpec>) :
    MutableList<AnnotationSpec> by actualList {

    /** Add annotation from [ClassName]. */
    fun add(type: ClassName): AnnotationSpec = AnnotationSpec.builder(type).build().also(::add)

    /** Add annotation from [ClassName] with custom initialization [configuration]. */
    fun add(type: ClassName, configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildAnnotationSpec(type, configuration).also(::add)
    }

    /** Add annotation from [Class]. */
    fun add(type: Class<*>): AnnotationSpec = AnnotationSpec.builder(type).build().also(::add)

    /** Add annotation from [Class] with custom initialization [configuration]. */
    fun add(type: Class<*>, configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildAnnotationSpec(type, configuration).also(::add)
    }

    /** Add annotation from [KClass]. */
    fun add(type: KClass<*>): AnnotationSpec = AnnotationSpec.builder(type.java).build().also(::add)

    /** Add annotation from [KClass] with custom initialization [configuration]. */
    fun add(type: KClass<*>, configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildAnnotationSpec(type, configuration).also(::add)
    }

    /** Add annotation from [T]. */
    inline fun <reified T> add(): AnnotationSpec = add(T::class.java)

    /** Add annotation from [T] with custom initialization [configuration]. */
    inline fun <reified T> add(noinline configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return add(T::class.java, configuration)
    }

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
@JavapoetSpecMarker
class AnnotationSpecListScope internal constructor(actualList: MutableList<AnnotationSpec>) :
    AnnotationSpecList(actualList)
