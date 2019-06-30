package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.ParameterSpecBuilder
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

abstract class ParameterContainer internal constructor() : ParameterContainerDelegate() {

    operator fun invoke(configuration: ParameterContainerScope.() -> Unit) =
        ParameterContainerScope(this).configuration()
}

@JavapoetDslMarker
@Suppress("NOTHING_TO_INLINE")
class ParameterContainerScope @PublishedApi internal constructor(private val container: ParameterContainer) :
    ParameterContainerDelegate() {

    override fun add(spec: ParameterSpec): ParameterSpec = container.add(spec)

    operator fun String.invoke(type: TypeName, builder: ParameterSpecBuilder.() -> Unit): ParameterSpec =
        add(type, this, builder)

    operator fun String.invoke(type: KClass<*>, builder: ParameterSpecBuilder.() -> Unit): ParameterSpec =
        add(type, this, builder)

    inline operator fun <reified T> String.invoke(noinline builder: ParameterSpecBuilder.() -> Unit): ParameterSpec =
        invoke(T::class, builder)
}

sealed class ParameterContainerDelegate {

    abstract fun add(spec: ParameterSpec): ParameterSpec

    fun add(type: TypeName, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null): ParameterSpec =
        add(ParameterSpecBuilder.of(type, name, builder))

    fun add(type: KClass<*>, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null): ParameterSpec =
        add(ParameterSpecBuilder.of(type, name, builder))

    inline fun <reified T> add(
        name: String,
        noinline builder: (ParameterSpecBuilder.() -> Unit)? = null
    ): ParameterSpec = add(T::class, name, builder)

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