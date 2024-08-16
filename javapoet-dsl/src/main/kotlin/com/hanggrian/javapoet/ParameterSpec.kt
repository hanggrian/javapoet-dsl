@file:OptIn(ExperimentalContracts::class)

package com.hanggrian.javapoet

import com.hanggrian.javapoet.internals.Internals
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/** Creates new [ParameterSpec] using parameters. */
public inline fun parameterSpecOf(
    type: TypeName,
    name: String,
    vararg modifiers: Modifier,
): ParameterSpec =
    ParameterSpec
        .builder(type, name, *modifiers)
        .build()

/**
 * Builds new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildParameterSpec(
    type: TypeName,
    name: String,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(ParameterSpec.builder(type, name, *modifiers))
        .apply(configuration)
        .build()
}

/**
 * Inserts new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
public inline fun ParameterSpecHandler.add(
    type: TypeName,
    name: String,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(ParameterSpec.builder(type, name, *modifiers))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
public inline fun ParameterSpecHandler.add(
    type: Type,
    name: String,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(ParameterSpec.builder(type, name, *modifiers))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
public inline fun ParameterSpecHandler.add(
    type: KClass<*>,
    name: String,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return ParameterSpecBuilder(ParameterSpec.builder(type.java, name, *modifiers))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Property delegate for inserting new [ParameterSpec] by populating newly created
 * [ParameterSpecBuilder] using provided [configuration].
 */
public fun ParameterSpecHandler.adding(
    type: TypeName,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): SpecDelegateProvider<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        ParameterSpecBuilder(ParameterSpec.builder(type, it, *modifiers))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/**
 * Property delegate for inserting new [ParameterSpec] by populating newly created
 * [ParameterSpecBuilder] using provided [configuration].
 */
public fun ParameterSpecHandler.adding(
    type: Type,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): SpecDelegateProvider<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        ParameterSpecBuilder(ParameterSpec.builder(type, it, *modifiers))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/**
 * Property delegate for inserting new [ParameterSpec] by populating newly created
 * [ParameterSpecBuilder] using provided [configuration].
 */
public fun ParameterSpecHandler.adding(
    type: KClass<*>,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): SpecDelegateProvider<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        ParameterSpecBuilder(ParameterSpec.builder(type.java, it, *modifiers))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/** Convenient method to insert [ParameterSpec] using reified type. */
public inline fun <reified T> ParameterSpecHandler.add(
    name: String,
    vararg modifiers: Modifier,
): ParameterSpec =
    ParameterSpec
        .builder(T::class.java, name, *modifiers)
        .build()
        .also(::add)

/** Responsible for managing a set of [ParameterSpec] instances. */
public interface ParameterSpecHandler {
    public fun add(parameter: ParameterSpec)

    public fun add(type: TypeName, name: String, vararg modifiers: Modifier): ParameterSpec =
        parameterSpecOf(type, name, *modifiers).also(::add)

    public fun add(type: Type, name: String, vararg modifiers: Modifier): ParameterSpec =
        parameterSpecOf(type.name, name, *modifiers).also(::add)

    public fun add(type: KClass<*>, name: String, vararg modifiers: Modifier): ParameterSpec =
        parameterSpecOf(type.name, name, *modifiers).also(::add)

    public fun adding(
        type: TypeName,
        vararg modifiers: Modifier,
    ): SpecDelegateProvider<ParameterSpec> =
        SpecDelegateProvider { parameterSpecOf(type, it, *modifiers).also(::add) }

    public fun adding(
        type: Type,
        vararg modifiers: Modifier,
    ): SpecDelegateProvider<ParameterSpec> =
        SpecDelegateProvider { parameterSpecOf(type.name, it, *modifiers).also(::add) }

    public fun adding(
        type: KClass<*>,
        vararg modifiers: Modifier,
    ): SpecDelegateProvider<ParameterSpec> =
        SpecDelegateProvider { parameterSpecOf(type.name, it, *modifiers).also(::add) }
}

/**
 * Receiver for the `parameters` block providing an extended set of operators for the
 * configuration.
 */
@JavapoetDsl
public open class ParameterSpecHandlerScope private constructor(handler: ParameterSpecHandler) :
    ParameterSpecHandler by handler {
        public inline operator fun String.invoke(
            type: TypeName,
            vararg modifiers: Modifier,
            configuration: ParameterSpecBuilder.() -> Unit,
        ): ParameterSpec = add(type, this, *modifiers, configuration = configuration)

        public inline operator fun String.invoke(
            type: Type,
            vararg modifiers: Modifier,
            configuration: ParameterSpecBuilder.() -> Unit,
        ): ParameterSpec = add(type.name, this, *modifiers, configuration = configuration)

        public inline operator fun String.invoke(
            type: KClass<*>,
            vararg modifiers: Modifier,
            configuration: ParameterSpecBuilder.() -> Unit,
        ): ParameterSpec = add(type.name, this, *modifiers, configuration = configuration)

        public companion object {
            public fun of(handler: ParameterSpecHandler): ParameterSpecHandlerScope =
                ParameterSpecHandlerScope(handler)
        }
    }

/** Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDsl
public class ParameterSpecBuilder(private val nativeBuilder: ParameterSpec.Builder) {
    public val annotations: AnnotationSpecHandler =
        object : AnnotationSpecHandler {
            override fun add(annotation: AnnotationSpec) {
                annotationSpecs += annotation
            }
        }

    /** Invokes DSL to configure [AnnotationSpec] collection. */
    public inline fun annotations(configuration: AnnotationSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        AnnotationSpecHandlerScope
            .of(annotations)
            .configuration()
    }

    public val annotationSpecs: MutableList<AnnotationSpec> get() = nativeBuilder.annotations
    public val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers

    public fun addJavadoc(format: String, vararg args: Any): Unit =
        Internals.format(format, args) { format2, args2 ->
            nativeBuilder.addJavadoc(format2, *args2)
        }

    public fun addJavadoc(block: CodeBlock) {
        nativeBuilder.addJavadoc(block)
    }

    public fun addModifiers(vararg modifiers: Modifier) {
        this.modifiers += modifiers
    }

    public fun build(): ParameterSpec = nativeBuilder.build()
}
