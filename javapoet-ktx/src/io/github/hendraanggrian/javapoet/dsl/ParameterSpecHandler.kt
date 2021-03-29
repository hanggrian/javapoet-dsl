package io.github.hendraanggrian.javapoet.dsl

import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import io.github.hendraanggrian.javapoet.ParameterSpecBuilder
import io.github.hendraanggrian.javapoet.SpecDslMarker
import io.github.hendraanggrian.javapoet.buildParameterSpec
import io.github.hendraanggrian.javapoet.parameterSpecOf
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

/** A [ParameterSpecHandler] is responsible for managing a set of parameter instances. */
open class ParameterSpecHandler internal constructor(actualList: MutableList<ParameterSpec>) :
    MutableList<ParameterSpec> by actualList {

    /** Add parameter from [TypeName]. */
    fun add(type: TypeName, name: String, vararg modifiers: Modifier): Boolean =
        add(parameterSpecOf(type, name, *modifiers))

    /** Add parameter from [TypeName] with custom initialization [configuration]. */
    fun add(
        type: TypeName,
        name: String,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(buildParameterSpec(type, name, *modifiers, configuration = configuration))

    /** Add parameter from [Type]. */
    fun add(type: Type, name: String, vararg modifiers: Modifier): Boolean =
        add(parameterSpecOf(type, name, *modifiers))

    /** Add parameter from [Type] with custom initialization [configuration]. */
    fun add(
        type: Type,
        name: String,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(buildParameterSpec(type, name, *modifiers, configuration = configuration))

    /** Add parameter from [KClass]. */
    fun add(type: KClass<*>, name: String, vararg modifiers: Modifier): Boolean =
        add(parameterSpecOf(type, name, *modifiers))

    /** Add parameter from [KClass] with custom initialization [configuration]. */
    fun add(
        type: KClass<*>,
        name: String,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(buildParameterSpec(type, name, *modifiers, configuration = configuration))

    /** Add parameter from [T]. */
    inline fun <reified T> add(name: String, vararg modifiers: Modifier): Boolean =
        add(parameterSpecOf<T>(name, *modifiers))

    /** Add parameter from [T] with custom initialization [configuration]. */
    inline fun <reified T> add(
        name: String,
        vararg modifiers: Modifier,
        noinline configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(buildParameterSpec<T>(name, *modifiers, configuration = configuration))

    /** Convenient method to add parameter with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun set(name: String, type: TypeName) {
        add(type, name)
    }

    /** Convenient method to add parameter with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun set(name: String, type: Type) {
        add(type, name)
    }

    /** Convenient method to add parameter with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun set(name: String, type: KClass<*>) {
        add(type, name)
    }
}

/** Receiver for the `parameters` block providing an extended set of operators for the configuration. */
@SpecDslMarker
class ParameterSpecHandlerScope internal constructor(actualList: MutableList<ParameterSpec>) :
    ParameterSpecHandler(actualList) {

    /** @see ParameterSpecHandler.add */
    operator fun String.invoke(type: TypeName, vararg modifiers: Modifier): Boolean = add(type, this, *modifiers)

    /** @see ParameterSpecHandler.add */
    operator fun String.invoke(
        type: TypeName,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(type, this, *modifiers, configuration = configuration)

    /** @see ParameterSpecHandler.add */
    operator fun String.invoke(type: Type, vararg modifiers: Modifier): Boolean =
        add(type, this, *modifiers)

    /** @see ParameterSpecHandler.add */
    operator fun String.invoke(
        type: Type,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(type, this, *modifiers, configuration = configuration)

    /** @see ParameterSpecHandler.add */
    operator fun String.invoke(type: KClass<*>, vararg modifiers: Modifier): Boolean =
        add(type, this, *modifiers)

    /** @see ParameterSpecHandler.add */
    operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add(type, this, *modifiers, configuration = configuration)

    /** @see ParameterSpecHandler.add */
    inline operator fun <reified T> String.invoke(vararg modifiers: Modifier): Boolean =
        add<T>(this, *modifiers)

    /** @see ParameterSpecHandler.add */
    inline operator fun <reified T> String.invoke(
        vararg modifiers: Modifier,
        noinline configuration: ParameterSpecBuilder.() -> Unit
    ): Boolean = add<T>(this, *modifiers, configuration = configuration)
}
