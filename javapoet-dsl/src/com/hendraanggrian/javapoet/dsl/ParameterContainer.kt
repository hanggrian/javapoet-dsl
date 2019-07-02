@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.ParameterSpecBuilder
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

internal interface ParameterCollection {

    fun add(spec: ParameterSpec): ParameterSpec

    fun add(type: TypeName, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null): ParameterSpec =
        add(ParameterSpecBuilder.of(type, name, builder))

    fun add(type: KClass<*>, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null): ParameterSpec =
        add(ParameterSpecBuilder.of(type, name, builder))
}

/** A [ParameterContainer] is responsible for managing a set of parameter instances. */
abstract class ParameterContainer internal constructor() : ParameterCollection {

    inline fun <reified T> add(
        name: String,
        noinline builder: (ParameterSpecBuilder.() -> Unit)? = null
    ): ParameterSpec = add(T::class, name, builder)

    inline operator fun plusAssign(spec: ParameterSpec) {
        add(spec)
    }

    inline operator fun set(name: String, type: TypeName) {
        add(type, name)
    }

    inline operator fun set(name: String, type: KClass<*>) {
        add(type, name)
    }

    inline operator fun invoke(configuration: ParameterContainerScope.() -> Unit) =
        ParameterContainerScope(this).configuration()
}

/**
 * Receiver for the `parameters` block providing an extended set of operators for the configuration.
 */
class ParameterContainerScope @PublishedApi internal constructor(collection: ParameterCollection) :
    ParameterContainer(), ParameterCollection by collection {

    inline operator fun String.invoke(
        type: TypeName,
        noinline builder: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(type, this, builder)

    inline operator fun String.invoke(
        type: KClass<*>,
        noinline builder: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(type, this, builder)

    inline operator fun <reified T> String.invoke(noinline builder: ParameterSpecBuilder.() -> Unit): ParameterSpec =
        invoke(T::class, builder)
}