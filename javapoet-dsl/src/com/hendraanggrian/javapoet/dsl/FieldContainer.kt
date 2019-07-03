package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.FieldSpecBuilder
import com.hendraanggrian.javapoet.invoke
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

/** A [FieldContainer] is responsible for managing a set of field instances. */
abstract class FieldContainer internal constructor() {

    /** Add field to this container, returning the field added. */
    abstract fun add(spec: FieldSpec): FieldSpec

    /** Add field from [type] and [name], returning the field added. */
    fun add(type: TypeName, name: String): FieldSpec =
        add(FieldSpec.builder(type, name).build())

    /** Add field from [type] and [name] with custom initialization [builder], returning the field added. */
    inline fun add(type: TypeName, name: String, builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(FieldSpec.builder(type, name)(builder))

    /** Add field from [type] and [name], returning the field added. */
    fun add(type: KClass<*>, name: String): FieldSpec =
        add(FieldSpec.builder(type.java, name).build())

    /** Add field from [type] and [name] with custom initialization [builder], returning the field added. */
    inline fun add(type: KClass<*>, name: String, builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(FieldSpec.builder(type.java, name)(builder))

    /** Add field from reified [T] and [name], returning the field added. */
    inline fun <reified T> add(name: String): FieldSpec =
        add(T::class, name)

    /** Add field from reified [T] and [name] with custom initialization [builder], returning the field added. */
    inline fun <reified T> add(name: String, builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(T::class, name, builder)

    /** Convenient method to add field with operator function. */
    operator fun plusAssign(spec: FieldSpec) {
        add(spec)
    }

    /** Convenient method to add field with operator function. */
    operator fun set(name: String, type: TypeName) {
        add(type, name)
    }

    /** Convenient method to add field with operator function. */
    operator fun set(name: String, type: KClass<*>) {
        add(type, name)
    }

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: FieldContainerScope.() -> Unit) =
        FieldContainerScope(this).configuration()
}

/** Receiver for the `fields` block providing an extended set of operators for the configuration. */
class FieldContainerScope @PublishedApi internal constructor(private val container: FieldContainer) :
    FieldContainer() {

    override fun add(spec: FieldSpec): FieldSpec = container.add(spec)

    /** Convenient method to add field with receiver type. */
    inline operator fun String.invoke(name: TypeName, builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(name, this, builder)

    /** Convenient method to add field with receiver type. */
    inline operator fun String.invoke(type: KClass<*>, builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(type, this, builder)

    /** Convenient method to add field with receiver type. */
    inline operator fun <reified T> String.invoke(builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add<T>(this, builder)
}