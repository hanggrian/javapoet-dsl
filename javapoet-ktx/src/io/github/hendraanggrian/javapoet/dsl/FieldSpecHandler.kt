package io.github.hendraanggrian.javapoet.dsl

import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import io.github.hendraanggrian.javapoet.FieldSpecBuilder
import io.github.hendraanggrian.javapoet.SpecDslMarker
import io.github.hendraanggrian.javapoet.buildFieldSpec
import io.github.hendraanggrian.javapoet.fieldSpecOf
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

/** A [FieldSpecHandler] is responsible for managing a set of field instances. */
open class FieldSpecHandler internal constructor(actualList: MutableList<FieldSpec>) :
    MutableList<FieldSpec> by actualList {

    /** Add field from [TypeName]. */
    fun add(type: TypeName, name: String, vararg modifiers: Modifier): Boolean =
        add(fieldSpecOf(type, name, *modifiers))

    /** Add field from [Type]. */
    fun add(type: Type, name: String, vararg modifiers: Modifier): Boolean =
        add(fieldSpecOf(type, name, *modifiers))

    /** Add field from [KClass]. */
    fun add(type: KClass<*>, name: String, vararg modifiers: Modifier): Boolean =
        add(fieldSpecOf(type, name, *modifiers))

    /** Add field from [T]. */
    inline fun <reified T> add(name: String, vararg modifiers: Modifier): Boolean =
        add(fieldSpecOf<T>(name, *modifiers))

    /** Add field from [TypeName] with custom initialization [configuration]. */
    inline fun add(
        type: TypeName,
        name: String,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(buildFieldSpec(type, name, *modifiers, configuration = configuration))

    /** Add field from [Type] with custom initialization [configuration]. */
    inline fun add(
        type: Type,
        name: String,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(buildFieldSpec(type, name, *modifiers, configuration = configuration))

    /** Add field from [KClass] with custom initialization [configuration]. */
    inline fun add(
        type: KClass<*>,
        name: String,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(buildFieldSpec(type, name, *modifiers, configuration = configuration))

    /** Add field from [T] with custom initialization [configuration]. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(buildFieldSpec<T>(name, *modifiers, configuration = configuration))

    /** Convenient method to add field with operator function. */
    operator fun set(name: String, type: TypeName): Unit = plusAssign(fieldSpecOf(type, name))

    /** Convenient method to add field with operator function. */
    operator fun set(name: String, type: Type): Unit = plusAssign(fieldSpecOf(type, name))

    /** Convenient method to add field with operator function. */
    operator fun set(name: String, type: KClass<*>): Unit = plusAssign(fieldSpecOf(type, name))
}

/** Receiver for the `fields` function type providing an extended set of operators for the configuration. */
@SpecDslMarker
class FieldSpecHandlerScope(actualList: MutableList<FieldSpec>) : FieldSpecHandler(actualList) {

    /** Convenient method to add field with receiver type. */
    inline operator fun String.invoke(
        type: TypeName,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(type, this, *modifiers, configuration = configuration)

    /** Convenient method to add field with receiver type. */
    inline operator fun String.invoke(
        type: Type,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(type, this, *modifiers, configuration = configuration)

    /** Convenient method to add field with receiver type. */
    inline operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(type, this, *modifiers, configuration = configuration)

    /** Convenient method to add field with receiver type. */
    inline operator fun <reified T> String.invoke(
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add<T>(this, *modifiers, configuration = configuration)
}
