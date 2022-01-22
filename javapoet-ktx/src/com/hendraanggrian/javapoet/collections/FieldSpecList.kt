package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.FieldSpecBuilder
import com.hendraanggrian.javapoet.SpecMarker
import com.hendraanggrian.javapoet.buildFieldSpec
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

/** A [FieldSpecList] is responsible for managing a set of field instances. */
open class FieldSpecList internal constructor(actualList: MutableList<FieldSpec>) :
    MutableList<FieldSpec> by actualList {

    /** Add field from [TypeName]. */
    fun add(type: TypeName, name: String, vararg modifiers: Modifier): Boolean =
        add(FieldSpec.builder(type, name, *modifiers).build())

    /** Add field from [TypeName] with custom initialization [configuration]. */
    fun add(
        type: TypeName,
        name: String,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(buildFieldSpec(type, name, *modifiers, configuration = configuration))

    /** Add field from [Type]. */
    fun add(type: Type, name: String, vararg modifiers: Modifier): Boolean =
        add(FieldSpec.builder(type, name, *modifiers).build())

    /** Add field from [Type] with custom initialization [configuration]. */
    fun add(
        type: Type,
        name: String,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(buildFieldSpec(type, name, *modifiers, configuration = configuration))

    /** Add field from [KClass]. */
    fun add(type: KClass<*>, name: String, vararg modifiers: Modifier): Boolean =
        add(FieldSpec.builder(type.java, name, *modifiers).build())

    /** Add field from [KClass] with custom initialization [configuration]. */
    fun add(
        type: KClass<*>,
        name: String,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(buildFieldSpec(type, name, *modifiers, configuration = configuration))

    /** Add field from [T]. */
    inline fun <reified T> add(name: String, vararg modifiers: Modifier): Boolean =
        add(FieldSpec.builder(T::class.java, name, *modifiers).build())

    /** Add field from [T] with custom initialization [configuration]. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: Modifier,
        noinline configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(buildFieldSpec<T>(name, *modifiers, configuration = configuration))

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
}

/** Receiver for the `fields` block providing an extended set of operators for the configuration. */
@SpecMarker
class FieldSpecListScope internal constructor(actualList: MutableList<FieldSpec>) : FieldSpecList(actualList) {

    /** @see FieldSpecList.add */
    operator fun String.invoke(
        type: TypeName,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(type, this, *modifiers, configuration = configuration)

    /** @see FieldSpecList.add */
    operator fun String.invoke(
        type: Type,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(type, this, *modifiers, configuration = configuration)

    /** @see FieldSpecList.add */
    operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(type, this, *modifiers, configuration = configuration)

    /** @see FieldSpecList.add */
    inline operator fun <reified T> String.invoke(
        vararg modifiers: Modifier,
        noinline configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add<T>(this, *modifiers, configuration = configuration)
}
