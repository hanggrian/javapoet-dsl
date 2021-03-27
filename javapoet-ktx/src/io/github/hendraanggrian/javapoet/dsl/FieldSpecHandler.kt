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

    /** Add field from [TypeName] with custom initialization [configuration]. */
    fun add(
        type: TypeName,
        name: String,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(buildFieldSpec(type, name, *modifiers, configuration = configuration))

    /** Add field from [Type]. */
    fun add(type: Type, name: String, vararg modifiers: Modifier): Boolean =
        add(fieldSpecOf(type, name, *modifiers))

    /** Add field from [Type] with custom initialization [configuration]. */
    fun add(
        type: Type,
        name: String,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(buildFieldSpec(type, name, *modifiers, configuration = configuration))

    /** Add field from [KClass]. */
    fun add(type: KClass<*>, name: String, vararg modifiers: Modifier): Boolean =
        add(fieldSpecOf(type, name, *modifiers))

    /** Add field from [KClass] with custom initialization [configuration]. */
    fun add(
        type: KClass<*>,
        name: String,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(buildFieldSpec(type, name, *modifiers, configuration = configuration))

    /** Add field from [T]. */
    inline fun <reified T> add(name: String, vararg modifiers: Modifier): Boolean =
        add(fieldSpecOf<T>(name, *modifiers))

    /** Add field from [T] with custom initialization [configuration]. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(buildFieldSpec<T>(name, *modifiers, configuration = configuration))

    /** Convenient method to add field with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun set(name: String, type: TypeName) {
        add(type, name)
    }

    /** Convenient method to add field with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun set(name: String, type: Type) {
        add(type, name)
    }

    /** Convenient method to add field with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun set(name: String, type: KClass<*>) {
        add(type, name)
    }
}

/** Receiver for the `fields` block providing an extended set of operators for the configuration. */
@SpecDslMarker
class FieldSpecHandlerScope internal constructor(actualList: MutableList<FieldSpec>) : FieldSpecHandler(actualList) {

    /** @see FieldSpecHandler.add */
    operator fun String.invoke(type: TypeName, vararg modifiers: Modifier): Boolean =
        add(type, this, *modifiers)

    /** @see FieldSpecHandler.add */
    operator fun String.invoke(
        type: TypeName,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(type, this, *modifiers, configuration = configuration)

    /** @see FieldSpecHandler.add */
    operator fun String.invoke(type: Type, vararg modifiers: Modifier): Boolean =
        add(type, this, *modifiers)

    /** @see FieldSpecHandler.add */
    operator fun String.invoke(
        type: Type,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(type, this, *modifiers, configuration = configuration)

    /** @see FieldSpecHandler.add */
    operator fun String.invoke(type: KClass<*>, vararg modifiers: Modifier): Boolean =
        add(type, this, *modifiers)

    /** @see FieldSpecHandler.add */
    operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add(type, this, *modifiers, configuration = configuration)

    /** @see FieldSpecHandler.add */
    inline operator fun <reified T> String.invoke(vararg modifiers: Modifier): Boolean =
        add<T>(this, *modifiers)

    /** @see FieldSpecHandler.add */
    inline operator fun <reified T> String.invoke(
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit
    ): Boolean = add<T>(this, *modifiers, configuration = configuration)
}
