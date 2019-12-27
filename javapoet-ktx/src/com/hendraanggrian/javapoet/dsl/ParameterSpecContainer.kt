package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.ParameterSpecBuilder
import com.hendraanggrian.javapoet.buildParameter
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

/** A [ParameterSpecContainer] is responsible for managing a set of parameter instances. */
abstract class ParameterSpecContainer internal constructor() {

    /** Add parameter to this container. */
    abstract fun add(spec: ParameterSpec)

    /** Add parameter from [type] and [name], returning the parameter added. */
    fun add(type: TypeName, name: String, vararg modifiers: Modifier): ParameterSpec =
        buildParameter(type, name, *modifiers).also { add(it) }

    /** Add parameter from [type] and [name] with custom initialization [builderAction], returning the parameter added. */
    inline fun add(
        type: TypeName,
        name: String,
        vararg modifiers: Modifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = buildParameter(type, name, *modifiers, builderAction = builderAction).also { add(it) }

    /** Add parameter from [type] and [name], returning the parameter added. */
    fun add(type: Type, name: String, vararg modifiers: Modifier): ParameterSpec =
        buildParameter(type, name, *modifiers).also { add(it) }

    /** Add parameter from [type] and [name] with custom initialization [builderAction], returning the parameter added. */
    inline fun add(
        type: Type,
        name: String,
        vararg modifiers: Modifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = buildParameter(type, name, *modifiers, builderAction = builderAction).also { add(it) }

    /** Add parameter from [type] and [name], returning the parameter added. */
    fun add(type: KClass<*>, name: String, vararg modifiers: Modifier): ParameterSpec =
        buildParameter(type, name, *modifiers).also { add(it) }

    /** Add parameter from [type] and [name] with custom initialization [builderAction], returning the parameter added. */
    inline fun add(
        type: KClass<*>,
        name: String,
        vararg modifiers: Modifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = buildParameter(type, name, *modifiers, builderAction = builderAction).also { add(it) }

    /** Add parameter from reified [T] and [name], returning the parameter added. */
    inline fun <reified T> add(name: String, vararg modifiers: Modifier): ParameterSpec =
        buildParameter<T>(name, *modifiers).also { add(it) }

    /** Add parameter from reified [T] and [name] with custom initialization [builderAction], returning the parameter added. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: Modifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = buildParameter<T>(name, *modifiers, builderAction = builderAction).also { add(it) }

    /** Convenient method to add parameter with operator function. */
    operator fun plusAssign(spec: ParameterSpec) {
        add(spec)
    }

    /** Convenient method to add parameter with operator function. */
    operator fun set(name: String, type: TypeName) {
        add(type, name)
    }

    /** Convenient method to add parameter with operator function. */
    operator fun set(name: String, type: Type) {
        add(type, name)
    }

    /** Convenient method to add parameter with operator function. */
    operator fun set(name: String, type: KClass<*>) {
        add(type, name)
    }

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: ParameterSpecContainerScope.() -> Unit): Unit =
        ParameterSpecContainerScope(this).configuration()
}

/** Receiver for the `parameters` block providing an extended set of operators for the configuration. */
@JavapoetDslMarker
class ParameterSpecContainerScope @PublishedApi internal constructor(private val container: ParameterSpecContainer) :
    ParameterSpecContainer() {

    override fun add(spec: ParameterSpec) = container.add(spec)

    /** Convenient method to add parameter with receiver type. */
    inline operator fun String.invoke(
        type: TypeName,
        vararg modifiers: Modifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(type, this, *modifiers, builderAction = builderAction)

    /** Convenient method to add parameter with receiver type. */
    inline operator fun String.invoke(
        type: Type,
        vararg modifiers: Modifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(type, this, *modifiers, builderAction = builderAction)

    /** Convenient method to add parameter with receiver type. */
    inline operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: Modifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(type, this, *modifiers, builderAction = builderAction)

    /** Convenient method to add parameter with receiver type. */
    inline operator fun <reified T> String.invoke(
        vararg modifiers: Modifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add<T>(this, *modifiers, builderAction = builderAction)
}
