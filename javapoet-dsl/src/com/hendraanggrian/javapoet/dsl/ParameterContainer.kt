package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.ParameterSpecBuilder
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

abstract class ParameterContainer internal constructor() : ParameterContainerDelegate {

    inline operator fun invoke(configuration: ParameterContainerScope.() -> Unit) =
        configuration(ParameterContainerScope(this))
}

@JavapoetDslMarker
@Suppress("NOTHING_TO_INLINE")
class ParameterContainerScope @PublishedApi internal constructor(private val container: ParameterContainer) :
    ParameterContainerDelegate by container {

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

interface ParameterContainerDelegate {

    fun add(spec: ParameterSpec): ParameterSpec

    fun add(type: TypeName, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null): ParameterSpec =
        add(ParameterSpecBuilder.of(type, name, builder))

    fun add(type: KClass<*>, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null): ParameterSpec =
        add(ParameterSpecBuilder.of(type, name, builder))

    operator fun plusAssign(spec: ParameterSpec) {
        add(spec)
    }

    operator fun set(name: String, type: TypeName) {
        add(type, name)
    }

    operator fun set(name: String, type: KClass<*>) {
        add(type, name)
    }
}

inline fun <reified T> ParameterContainerDelegate.add(
    name: String,
    noinline builder: (ParameterSpecBuilder.() -> Unit)? = null
): ParameterSpec = add(T::class, name, builder)