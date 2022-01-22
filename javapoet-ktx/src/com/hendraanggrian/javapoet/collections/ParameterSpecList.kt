package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.ParameterSpecBuilder
import com.hendraanggrian.javapoet.SpecMarker
import com.hendraanggrian.javapoet.buildParameterSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

/** A [ParameterSpecList] is responsible for managing a set of parameter instances. */
open class ParameterSpecList internal constructor(actualList: MutableList<ParameterSpec>) :
    MutableList<ParameterSpec> by actualList {

    /** Add parameter from [TypeName]. */
    fun add(type: TypeName, name: String, vararg modifiers: Modifier): Boolean =
        add(ParameterSpec.builder(type, name, *modifiers).build())

    /** Add parameter from [TypeName] with custom initialization [configuration]. */
    fun add(
        type: TypeName,
        name: String,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(buildParameterSpec(type, name, *modifiers, configuration = configuration))

    /** Add parameter from [Type]. */
    fun add(type: Type, name: String, vararg modifiers: Modifier): Boolean =
        add(ParameterSpec.builder(type, name, *modifiers).build())

    /** Add parameter from [Type] with custom initialization [configuration]. */
    fun add(
        type: Type,
        name: String,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(buildParameterSpec(type, name, *modifiers, configuration = configuration))

    /** Add parameter from [KClass]. */
    fun add(type: KClass<*>, name: String, vararg modifiers: Modifier): Boolean =
        add(ParameterSpec.builder(type.java, name, *modifiers).build())

    /** Add parameter from [KClass] with custom initialization [configuration]. */
    fun add(
        type: KClass<*>,
        name: String,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(buildParameterSpec(type, name, *modifiers, configuration = configuration))

    /** Add parameter from [T]. */
    inline fun <reified T> add(name: String, vararg modifiers: Modifier): Boolean =
        add(ParameterSpec.builder(T::class.java, name, *modifiers).build())

    /** Add parameter from [T] with custom initialization [configuration]. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: Modifier,
        noinline configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(buildParameterSpec<T>(name, *modifiers, configuration = configuration))

    /** Convenient method to add parameter with operator function. */
    inline operator fun set(name: String, type: TypeName) {
        add(type, name)
    }

    /** Convenient method to add parameter with operator function. */
    inline operator fun set(name: String, type: Type) {
        add(type, name)
    }

    /** Convenient method to add parameter with operator function. */
    inline operator fun set(name: String, type: KClass<*>) {
        add(type, name)
    }
}

/** Receiver for the `parameters` block providing an extended set of operators for the configuration. */
@SpecMarker
class ParameterSpecListScope internal constructor(actualList: MutableList<ParameterSpec>) :
    ParameterSpecList(actualList) {

    /** @see ParameterSpecList.add */
    operator fun String.invoke(
        type: TypeName,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(type, this, *modifiers, configuration = configuration)

    /** @see ParameterSpecList.add */
    operator fun String.invoke(
        type: Type,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(type, this, *modifiers, configuration = configuration)

    /** @see ParameterSpecList.add */
    operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(type, this, *modifiers, configuration = configuration)

    /** @see ParameterSpecList.add */
    inline operator fun <reified T> String.invoke(
        vararg modifiers: Modifier,
        noinline configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add<T>(this, *modifiers, configuration = configuration)
}
