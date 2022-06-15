package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.JavapoetSpecDsl
import com.hendraanggrian.javapoet.ParameterSpecBuilder
import com.hendraanggrian.javapoet.SpecLoader
import com.hendraanggrian.javapoet.buildParameterSpec
import com.hendraanggrian.javapoet.createSpecLoader
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/** A [ParameterSpecList] is responsible for managing a set of parameter instances. */
@OptIn(ExperimentalContracts::class)
open class ParameterSpecList internal constructor(actualList: MutableList<ParameterSpec>) :
    MutableList<ParameterSpec> by actualList {

    /** Add parameter from [TypeName]. */
    fun add(type: TypeName, name: String, vararg modifiers: Modifier): ParameterSpec =
        ParameterSpec.builder(type, name, *modifiers).build().also(::add)

    /** Add parameter from [TypeName] with custom initialization [configuration]. */
    fun add(
        type: TypeName,
        name: String,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildParameterSpec(type, name, *modifiers, configuration = configuration).also(::add)
    }

    /** Add parameter from [Type]. */
    fun add(type: Type, name: String, vararg modifiers: Modifier): ParameterSpec =
        ParameterSpec.builder(type, name, *modifiers).build().also(::add)

    /** Add parameter from [Type] with custom initialization [configuration]. */
    fun add(
        type: Type,
        name: String,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildParameterSpec(type, name, *modifiers, configuration = configuration).also(::add)
    }

    /** Add parameter from [KClass]. */
    fun add(type: KClass<*>, name: String, vararg modifiers: Modifier): ParameterSpec =
        ParameterSpec.builder(type.java, name, *modifiers).build().also(::add)

    /** Add parameter from [KClass] with custom initialization [configuration]. */
    fun add(
        type: KClass<*>,
        name: String,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildParameterSpec(type, name, *modifiers, configuration = configuration).also(::add)
    }

    /** Add parameter from [T]. */
    inline fun <reified T> add(name: String, vararg modifiers: Modifier): ParameterSpec =
        add(T::class.java, name, *modifiers)

    /** Add parameter from [T] with custom initialization [configuration]. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: Modifier,
        noinline configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return add(T::class.java, name, *modifiers, configuration = configuration)
    }

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

    /** Property delegate for adding parameter from [TypeName]. */
    fun adding(type: TypeName, vararg modifiers: Modifier): SpecLoader<ParameterSpec> =
        createSpecLoader { add(type, it, *modifiers) }

    /** Property delegate for adding parameter from [TypeName] with custom initialization [configuration]. */
    fun adding(
        type: TypeName,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): SpecLoader<ParameterSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { add(type, it, *modifiers, configuration = configuration) }
    }

    /** Property delegate for adding parameter from [Type]. */
    fun adding(type: Type, vararg modifiers: Modifier): SpecLoader<ParameterSpec> =
        createSpecLoader { add(type, it, *modifiers) }

    /** Property delegate for adding parameter from [Type] with custom initialization [configuration]. */
    fun adding(
        type: Type,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): SpecLoader<ParameterSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { add(type, it, *modifiers, configuration = configuration) }
    }

    /** Property delegate for adding parameter from [KClass]. */
    fun adding(type: KClass<*>, vararg modifiers: Modifier): SpecLoader<ParameterSpec> =
        createSpecLoader { add(type, it, *modifiers) }

    /** Property delegate for adding parameter from [KClass] with custom initialization [configuration]. */
    fun adding(
        type: KClass<*>,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): SpecLoader<ParameterSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { add(type, it, *modifiers, configuration = configuration) }
    }
}

/** Receiver for the `parameters` block providing an extended set of operators for the configuration. */
@JavapoetSpecDsl
class ParameterSpecListScope internal constructor(actualList: MutableList<ParameterSpec>) :
    ParameterSpecList(actualList) {

    /** @see ParameterSpecList.add */
    inline operator fun String.invoke(
        type: TypeName,
        vararg modifiers: Modifier,
        noinline configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(type, this, *modifiers, configuration = configuration)

    /** @see ParameterSpecList.add */
    inline operator fun String.invoke(
        type: Type,
        vararg modifiers: Modifier,
        noinline configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(type, this, *modifiers, configuration = configuration)

    /** @see ParameterSpecList.add */
    inline operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: Modifier,
        noinline configuration: ParameterSpecBuilder.() -> Unit
    ): ParameterSpec = add(type, this, *modifiers, configuration = configuration)
}
