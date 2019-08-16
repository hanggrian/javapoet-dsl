package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.FieldSpecBuilder
import com.hendraanggrian.javapoet.buildField
import com.hendraanggrian.javapoet.toField
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

internal interface FieldCollection {

    /** Add field to this container, returning the field added. */
    fun add(spec: FieldSpec): FieldSpec
}

/** A [FieldContainer] is responsible for managing a set of field instances. */
abstract class FieldContainer internal constructor() : FieldCollection {

    /** Add field from [type] and [name], returning the field added. */
    fun add(type: TypeName, name: String, vararg modifiers: Modifier): FieldSpec =
        add(type.toField(name, *modifiers))

    /** Add field from [type] and [name] with custom initialization [builderAction], returning the field added. */
    inline fun add(
        type: TypeName,
        name: String,
        vararg modifiers: Modifier,
        builderAction: FieldSpecBuilder.() -> Unit
    ): FieldSpec =
        add(buildField(type, name, *modifiers, builderAction = builderAction))

    /** Add field from [type] and [name], returning the field added. */
    fun add(type: KClass<*>, name: String, vararg modifiers: Modifier): FieldSpec =
        add(type.toField(name, *modifiers))

    /** Add field from [type] and [name] with custom initialization [builder], returning the field added. */
    inline fun add(
        type: KClass<*>,
        name: String,
        vararg modifiers: Modifier,
        builderAction: FieldSpecBuilder.() -> Unit
    ): FieldSpec =
        add(buildField(type, name, *modifiers, builderAction = builderAction))

    /** Add field from reified [T] and [name], returning the field added. */
    inline fun <reified T> add(name: String, vararg modifiers: Modifier): FieldSpec =
        add(T::class, name, *modifiers)

    /** Add field from reified [T] and [name] with custom initialization [builder], returning the field added. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: Modifier,
        builderAction: FieldSpecBuilder.() -> Unit
    ): FieldSpec =
        add(T::class, name, *modifiers, builderAction = builderAction)

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
class FieldContainerScope @PublishedApi internal constructor(collection: FieldCollection) :
    FieldContainer(), FieldCollection by collection {

    /** Convenient method to add field with receiver type. */
    inline operator fun String.invoke(
        type: TypeName,
        vararg modifiers: Modifier,
        builderAction: FieldSpecBuilder.() -> Unit
    ): FieldSpec =
        add(type, this, *modifiers, builderAction = builderAction)

    /** Convenient method to add field with receiver type. */
    inline operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: Modifier,
        builderAction: FieldSpecBuilder.() -> Unit
    ): FieldSpec =
        add(type, this, *modifiers, builderAction = builderAction)

    /** Convenient method to add field with receiver type. */
    inline operator fun <reified T> String.invoke(
        vararg modifiers: Modifier,
        builderAction: FieldSpecBuilder.() -> Unit
    ): FieldSpec =
        add<T>(this, *modifiers, builderAction = builderAction)
}
