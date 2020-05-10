package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.ParameterSpecBuilder
import com.hendraanggrian.javapoet.buildParameterSpec
import com.hendraanggrian.javapoet.parameterSpecOf
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

/** A [ParameterSpecList] is responsible for managing a set of parameter instances. */
open class ParameterSpecList internal constructor(specs: MutableList<ParameterSpec>) :
    MutableList<ParameterSpec> by specs {

    /** Add parameter from [type] and [name], returning the parameter added. */
    fun add(type: TypeName, name: String, vararg modifiers: Modifier): ParameterSpec =
        parameterSpecOf(type, name, *modifiers).also { add(it) }

    /** Add parameter from [type] and [name] with custom initialization [builderAction], returning the parameter added. */
    inline fun add(
        type: TypeName,
        name: String,
        vararg modifiers: Modifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = buildParameterSpec(type, name, *modifiers, builderAction = builderAction).also { add(it) }

    /** Add parameter from [type] and [name], returning the parameter added. */
    fun add(type: Type, name: String, vararg modifiers: Modifier): ParameterSpec =
        parameterSpecOf(type, name, *modifiers).also { add(it) }

    /** Add parameter from [type] and [name] with custom initialization [builderAction], returning the parameter added. */
    inline fun add(
        type: Type,
        name: String,
        vararg modifiers: Modifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = buildParameterSpec(type, name, *modifiers, builderAction = builderAction).also { add(it) }

    /** Add parameter from [type] and [name], returning the parameter added. */
    fun add(type: KClass<*>, name: String, vararg modifiers: Modifier): ParameterSpec =
        parameterSpecOf(type, name, *modifiers).also { add(it) }

    /** Add parameter from [type] and [name] with custom initialization [builderAction], returning the parameter added. */
    inline fun add(
        type: KClass<*>,
        name: String,
        vararg modifiers: Modifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = buildParameterSpec(type, name, *modifiers, builderAction = builderAction).also { add(it) }

    /** Add parameter from reified [T] and [name], returning the parameter added. */
    inline fun <reified T> add(name: String, vararg modifiers: Modifier): ParameterSpec =
        parameterSpecOf<T>(name, *modifiers).also { add(it) }

    /** Add parameter from reified [T] and [name] with custom initialization [builderAction], returning the parameter added. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: Modifier,
        builderAction: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = buildParameterSpec<T>(name, *modifiers, builderAction = builderAction).also { add(it) }

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
}

/** Receiver for the `parameters` function type providing an extended set of operators for the configuration. */
@JavapoetDslMarker
class ParameterSpecListScope(specs: MutableList<ParameterSpec>) : ParameterSpecList(specs) {

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
