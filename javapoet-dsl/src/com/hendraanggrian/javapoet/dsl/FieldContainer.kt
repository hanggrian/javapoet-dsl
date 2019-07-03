package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.FieldSpecBuilder
import com.hendraanggrian.javapoet.invoke
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

/** A [FieldContainer] is responsible for managing a set of field instances. */
abstract class FieldContainer internal constructor() {

    abstract fun add(spec: FieldSpec): FieldSpec

    fun add(type: TypeName, name: String): FieldSpec =
        add(FieldSpec.builder(type, name).build())

    inline fun add(type: TypeName, name: String, builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(FieldSpec.builder(type, name)(builder))

    fun add(type: KClass<*>, name: String): FieldSpec =
        add(FieldSpec.builder(type.java, name).build())

    inline fun add(type: KClass<*>, name: String, builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(FieldSpec.builder(type.java, name)(builder))

    inline fun <reified T> add(name: String): FieldSpec =
        add(T::class, name)

    inline fun <reified T> add(name: String, builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(T::class, name, builder)

    operator fun plusAssign(spec: FieldSpec) {
        add(spec)
    }

    operator fun set(name: String, type: TypeName) {
        add(type, name)
    }

    operator fun set(name: String, type: KClass<*>) {
        add(type, name)
    }

    inline operator fun invoke(configuration: FieldContainerScope.() -> Unit) =
        FieldContainerScope(this).configuration()
}

/**
 * Receiver for the `fields` block providing an extended set of operators for the configuration.
 */
class FieldContainerScope @PublishedApi internal constructor(private val container: FieldContainer) :
    FieldContainer() {

    override fun add(spec: FieldSpec): FieldSpec = container.add(spec)

    inline operator fun String.invoke(name: TypeName, builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(name, this, builder)

    inline operator fun String.invoke(type: KClass<*>, builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(type, this, builder)

    inline operator fun <reified T> String.invoke(builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add<T>(this, builder)
}