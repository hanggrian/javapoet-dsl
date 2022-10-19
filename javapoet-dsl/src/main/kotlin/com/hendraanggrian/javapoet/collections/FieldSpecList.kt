package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.FieldSpecBuilder
import com.hendraanggrian.javapoet.JavapoetSpecDsl
import com.hendraanggrian.javapoet.SpecLoader
import com.hendraanggrian.javapoet.buildFieldSpec
import com.hendraanggrian.javapoet.createSpecLoader
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/** A [FieldSpecList] is responsible for managing a set of field instances. */
@OptIn(ExperimentalContracts::class)
open class FieldSpecList internal constructor(actualList: MutableList<FieldSpec>) :
    MutableList<FieldSpec> by actualList {

    /** Add field from [TypeName]. */
    fun add(type: TypeName, name: String, vararg modifiers: Modifier): FieldSpec =
        FieldSpec.builder(type, name, *modifiers).build().also(::add)

    /** Add field from [TypeName] with initialization [configuration]. */
    fun add(
        type: TypeName,
        name: String,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): FieldSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildFieldSpec(type, name, *modifiers, configuration = configuration).also(::add)
    }

    /** Add field from [Type]. */
    fun add(type: Type, name: String, vararg modifiers: Modifier): FieldSpec =
        FieldSpec.builder(type, name, *modifiers).build().also(::add)

    /** Add field from [Type] with initialization [configuration]. */
    fun add(
        type: Type,
        name: String,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): FieldSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildFieldSpec(type, name, *modifiers, configuration = configuration).also(::add)
    }

    /** Add field from [KClass]. */
    fun add(type: KClass<*>, name: String, vararg modifiers: Modifier): FieldSpec =
        FieldSpec.builder(type.java, name, *modifiers).build().also(::add)

    /** Add field from [KClass] with initialization [configuration]. */
    fun add(
        type: KClass<*>,
        name: String,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): FieldSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildFieldSpec(type, name, *modifiers, configuration = configuration).also(::add)
    }

    /** Add field from [T]. */
    inline fun <reified T> add(name: String, vararg modifiers: Modifier): FieldSpec =
        add(T::class.java, name, *modifiers)

    /** Add field from [T] with custom initialization [configuration]. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: Modifier,
        noinline configuration: FieldSpecBuilder.() -> Unit
    ): FieldSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return add(T::class.java, name, *modifiers, configuration = configuration)
    }

    /** Convenient method to add field with operator function. */
    inline operator fun set(name: String, type: TypeName) {
        add(type, name)
    }

    /** Convenient method to add field with operator function. */
    inline operator fun set(name: String, type: Type) {
        add(type, name)
    }

    /** Convenient method to add field with operator function. */
    inline operator fun set(name: String, type: KClass<*>) {
        add(type, name)
    }

    /** Property delegate for adding field from [TypeName]. */
    fun adding(type: TypeName, vararg modifiers: Modifier): SpecLoader<FieldSpec> =
        createSpecLoader { add(type, it, *modifiers) }

    /** Property delegate for adding field from [TypeName] with initialization [configuration]. */
    fun adding(
        type: TypeName,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): SpecLoader<FieldSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { add(type, it, *modifiers, configuration = configuration) }
    }

    /** Property delegate for adding field from [Type]. */
    fun adding(type: Type, vararg modifiers: Modifier): SpecLoader<FieldSpec> =
        createSpecLoader { add(type, it, *modifiers) }

    /** Property delegate for adding field from [Type] with initialization [configuration]. */
    fun adding(
        type: Type,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): SpecLoader<FieldSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { add(type, it, *modifiers, configuration = configuration) }
    }

    /** Property delegate for adding field from [KClass]. */
    fun adding(type: KClass<*>, vararg modifiers: Modifier): SpecLoader<FieldSpec> =
        createSpecLoader { add(type, it, *modifiers) }

    /** Property delegate for adding field from [KClass] with initialization [configuration]. */
    fun adding(
        type: KClass<*>,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): SpecLoader<FieldSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { add(type, it, *modifiers, configuration = configuration) }
    }
}

/** Receiver for the `fields` block providing an extended set of operators for the configuration. */
@JavapoetSpecDsl
class FieldSpecListScope internal constructor(actualList: MutableList<FieldSpec>) :
    FieldSpecList(actualList) {

    /** @see FieldSpecList.add */
    inline operator fun String.invoke(
        type: TypeName,
        vararg modifiers: Modifier,
        noinline configuration: FieldSpecBuilder.() -> Unit
    ): FieldSpec = add(type, this, *modifiers, configuration = configuration)

    /** @see FieldSpecList.add */
    inline operator fun String.invoke(
        type: Type,
        vararg modifiers: Modifier,
        noinline configuration: FieldSpecBuilder.() -> Unit
    ): FieldSpec = add(type, this, *modifiers, configuration = configuration)

    /** @see FieldSpecList.add */
    inline operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: Modifier,
        noinline configuration: FieldSpecBuilder.() -> Unit
    ): FieldSpec = add(type, this, *modifiers, configuration = configuration)
}
