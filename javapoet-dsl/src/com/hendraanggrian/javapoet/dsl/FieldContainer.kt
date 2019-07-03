@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.FieldSpecBuilder
import com.hendraanggrian.javapoet.invoke
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

internal interface FieldCollection {

    fun add(spec: FieldSpec): FieldSpec

    fun add(type: TypeName, name: String): FieldSpec =
        add(FieldSpec.builder(type, name).build())

    fun add(type: TypeName, name: String, builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(FieldSpec.builder(type, name)(builder))

    fun add(type: KClass<*>, name: String): FieldSpec =
        add(FieldSpec.builder(type.java, name).build())

    fun add(type: KClass<*>, name: String, builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(FieldSpec.builder(type.java, name)(builder))
}

/** A [FieldContainer] is responsible for managing a set of field instances. */
abstract class FieldContainer internal constructor() : FieldCollection {

    inline fun <reified T> add(name: String): FieldSpec = add(T::class, name)

    inline fun <reified T> add(name: String, noinline builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(T::class, name, builder)

    inline operator fun plusAssign(spec: FieldSpec) {
        add(spec)
    }

    inline operator fun set(name: String, type: TypeName) {
        add(type, name)
    }

    inline operator fun set(name: String, type: KClass<*>) {
        add(type, name)
    }

    inline operator fun invoke(configuration: FieldContainerScope.() -> Unit) =
        FieldContainerScope(this).configuration()
}

/**
 * Receiver for the `fields` block providing an extended set of operators for the configuration.
 */
class FieldContainerScope @PublishedApi internal constructor(collection: FieldCollection) :
    FieldContainer(), FieldCollection by collection {

    inline operator fun String.invoke(name: TypeName, noinline builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(name, this, builder)

    inline operator fun String.invoke(type: KClass<*>, noinline builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(type, this, builder)

    inline operator fun <reified T> String.invoke(noinline builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        invoke(T::class, builder)
}