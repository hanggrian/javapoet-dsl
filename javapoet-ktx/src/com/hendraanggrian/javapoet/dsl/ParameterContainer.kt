package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.ParameterSpecBuilder
import com.hendraanggrian.javapoet.buildParameter
import com.hendraanggrian.javapoet.toParameter
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

internal interface ParameterCollection {

    /** Add parameter to this container, returning the parameter added. */
    fun add(spec: ParameterSpec): ParameterSpec
}

/** A [ParameterContainer] is responsible for managing a set of parameter instances. */
abstract class ParameterContainer internal constructor() : ParameterCollection {

    /** Add parameter from [type] and [name], returning the parameter added. */
    fun add(type: TypeName, name: String, vararg modifiers: Modifier): ParameterSpec =
        add(type.toParameter(name, *modifiers))

    /** Add parameter from [type] and [name] with custom initialization [builder], returning the parameter added. */
    inline fun add(
        type: TypeName,
        name: String,
        vararg modifiers: Modifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec =
        add(buildParameter(type, name, *modifiers, builderAction = builderAction))

    /** Add parameter from [type] and [name], returning the parameter added. */
    fun add(type: KClass<*>, name: String, vararg modifiers: Modifier): ParameterSpec =
        add(type.toParameter(name, *modifiers))

    /** Add parameter from [type] and [name] with custom initialization [builder], returning the parameter added. */
    inline fun add(
        type: KClass<*>,
        name: String,
        vararg modifiers: Modifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec =
        add(buildParameter(type, name, *modifiers, builderAction = builderAction))

    /** Add parameter from reified [T] and [name], returning the parameter added. */
    inline fun <reified T> add(name: String, vararg modifiers: Modifier): ParameterSpec =
        add(T::class, name, *modifiers)

    /** Add parameter from reified [T] and [name] with custom initialization [builder], returning the parameter added. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: Modifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec =
        add(T::class, name, *modifiers, builderAction = builderAction)

    /** Convenient method to add parameter with operator function. */
    operator fun plusAssign(spec: ParameterSpec) {
        add(spec)
    }

    /** Convenient method to add parameter with operator function. */
    operator fun set(name: String, type: TypeName) {
        add(type, name)
    }

    /** Convenient method to add parameter with operator function. */
    operator fun set(name: String, type: KClass<*>) {
        add(type, name)
    }

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: ParameterContainerScope.() -> Unit) =
        ParameterContainerScope(this).configuration()
}

/** Receiver for the `parameters` block providing an extended set of operators for the configuration. */
class ParameterContainerScope @PublishedApi internal constructor(collection: ParameterCollection) :
    ParameterContainer(), ParameterCollection by collection {

    /** Convenient method to add parameter with receiver type. */
    inline operator fun String.invoke(
        type: TypeName,
        vararg modifiers: Modifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec =
        add(type, this, *modifiers, builderAction = builderAction)

    /** Convenient method to add parameter with receiver type. */
    inline operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: Modifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec =
        add(type, this, *modifiers, builderAction = builderAction)

    /** Convenient method to add parameter with receiver type. */
    inline operator fun <reified T> String.invoke(
        vararg modifiers: Modifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec =
        add<T>(this, *modifiers, builderAction = builderAction)
}
