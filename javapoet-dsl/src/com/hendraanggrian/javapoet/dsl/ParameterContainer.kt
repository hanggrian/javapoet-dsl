package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.ParameterSpecBuilder
import com.hendraanggrian.javapoet.invoke
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

/** A [ParameterContainer] is responsible for managing a set of parameter instances. */
abstract class ParameterContainer internal constructor() {

    abstract fun add(spec: ParameterSpec): ParameterSpec

    fun add(type: TypeName, name: String): ParameterSpec =
        add(ParameterSpec.builder(type, name).build())

    inline fun add(type: TypeName, name: String, builder: ParameterSpecBuilder.() -> Unit): ParameterSpec =
        add(ParameterSpec.builder(type, name)(builder))

    fun add(type: KClass<*>, name: String): ParameterSpec =
        add(ParameterSpec.builder(type.java, name).build())

    inline fun add(type: KClass<*>, name: String, builder: ParameterSpecBuilder.() -> Unit): ParameterSpec =
        add(ParameterSpec.builder(type.java, name)(builder))

    inline fun <reified T> add(name: String): ParameterSpec =
        add(T::class, name)

    inline fun <reified T> add(name: String, builder: ParameterSpecBuilder.() -> Unit): ParameterSpec =
        add(T::class, name, builder)

    operator fun plusAssign(spec: ParameterSpec) {
        add(spec)
    }

    operator fun set(name: String, type: TypeName) {
        add(type, name)
    }

    operator fun set(name: String, type: KClass<*>) {
        add(type, name)
    }

    inline operator fun invoke(configuration: ParameterContainerScope.() -> Unit) =
        ParameterContainerScope(this).configuration()
}

/**
 * Receiver for the `parameters` block providing an extended set of operators for the configuration.
 */
class ParameterContainerScope @PublishedApi internal constructor(private val container: ParameterContainer) :
    ParameterContainer() {

    override fun add(spec: ParameterSpec): ParameterSpec = container.add(spec)

    inline operator fun String.invoke(type: TypeName, builder: ParameterSpecBuilder.() -> Unit): ParameterSpec =
        add(type, this, builder)

    inline operator fun String.invoke(type: KClass<*>, builder: ParameterSpecBuilder.() -> Unit): ParameterSpec =
        add(type, this, builder)

    inline operator fun <reified T> String.invoke(builder: ParameterSpecBuilder.() -> Unit): ParameterSpec =
        add<T>(this, builder)
}