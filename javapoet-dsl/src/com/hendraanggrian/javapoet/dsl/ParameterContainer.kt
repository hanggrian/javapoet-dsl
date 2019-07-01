@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.ParameterSpecBuilder
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

@JavapoetDslMarker
class ParameterContainerScope @PublishedApi internal constructor(container: ParameterContainer) :
    ParameterContainer by container {

    /** Convenient method to add parameter with receiver. */
    inline operator fun String.invoke(
        type: TypeName,
        noinline builder: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(type, this, builder)

    /** Convenient method to add parameter with receiver. */
    inline operator fun String.invoke(
        type: KClass<*>,
        noinline builder: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(type, this, builder)

    /** Convenient method to add parameter with receiver and reified type. */
    inline operator fun <reified T> String.invoke(noinline builder: ParameterSpecBuilder.() -> Unit): ParameterSpec =
        invoke(T::class, builder)
}

interface ParameterContainer {

    /** Add spec to this container, returning the spec added. */
    fun add(spec: ParameterSpec): ParameterSpec

    fun add(type: TypeName, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null): ParameterSpec =
        add(ParameterSpecBuilder.of(type, name, builder))

    fun add(type: KClass<*>, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null): ParameterSpec =
        add(ParameterSpecBuilder.of(type, name, builder))
}

/** Configures the parameters of this container. */
inline operator fun ParameterContainer.invoke(configuration: ParameterContainerScope.() -> Unit) =
    ParameterContainerScope(this).configuration()

inline fun <reified T> ParameterContainer.add(
    name: String,
    noinline builder: (ParameterSpecBuilder.() -> Unit)? = null
): ParameterSpec = add(T::class, name, builder)

inline operator fun ParameterContainer.plusAssign(spec: ParameterSpec) {
    add(spec)
}

inline operator fun ParameterContainer.set(name: String, type: TypeName) {
    add(type, name)
}

inline operator fun ParameterContainer.set(name: String, type: KClass<*>) {
    add(type, name)
}