@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

public fun TypeName.asParameterSpec(name: String, vararg modifiers: Modifier): ParameterSpec =
    ParameterSpec.builder(this, name, *modifiers).build()

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
public inline fun ParameterSpecHandler.parameter(
    type: TypeName,
    name: String,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildParameterSpec(type, name, *modifiers, configuration = configuration)
        .also(::parameter)
}

/**
 * Inserts new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
public inline fun ParameterSpecHandler.parameter(
    type: Class<*>,
    name: String,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildParameterSpec(type.name2, name, *modifiers, configuration = configuration)
        .also(::parameter)
}

/**
 * Inserts new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
public inline fun ParameterSpecHandler.parameter(
    type: KClass<*>,
    name: String,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildParameterSpec(type.name, name, *modifiers, configuration = configuration)
        .also(::parameter)
}

/**
 * Property delegate for inserting new [ParameterSpec] by populating newly created
 * [ParameterSpecBuilder] using provided [configuration].
 */
public fun ParameterSpecHandler.parametering(
    type: TypeName,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): SpecDelegateProvider<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        buildParameterSpec(type, it, *modifiers, configuration = configuration)
            .also(::parameter)
    }
}

/**
 * Property delegate for inserting new [ParameterSpec] by populating newly created
 * [ParameterSpecBuilder] using provided [configuration].
 */
public fun ParameterSpecHandler.parametering(
    type: Class<*>,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): SpecDelegateProvider<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        buildParameterSpec(type.name2, it, *modifiers, configuration = configuration)
            .also(::parameter)
    }
}

/**
 * Property delegate for inserting new [ParameterSpec] by populating newly created
 * [ParameterSpecBuilder] using provided [configuration].
 */
public fun ParameterSpecHandler.parametering(
    type: KClass<*>,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): SpecDelegateProvider<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        buildParameterSpec(type.name, it, *modifiers, configuration = configuration)
            .also(::parameter)
    }
}

/** Convenient method to insert [ParameterSpec] using reified type. */
public inline fun <reified T> ParameterSpecHandler.parameter(
    name: String,
    vararg modifiers: Modifier,
): ParameterSpec = T::class.name.asParameterSpec(name, *modifiers).also(::parameter)

/** Invokes DSL to configure [ParameterSpec] collection. */
public fun ParameterSpecHandler.parameters(configuration: ParameterSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    ParameterSpecHandlerScope.of(this).configuration()
}

/** Responsible for managing a set of [ParameterSpec] instances. */
public interface ParameterSpecHandler {
    public fun parameter(parameter: ParameterSpec)

    public fun parameter(type: TypeName, name: String, vararg modifiers: Modifier): ParameterSpec =
        type.asParameterSpec(name, *modifiers).also(::parameter)

    public fun parameter(type: Class<*>, name: String, vararg modifiers: Modifier): ParameterSpec =
        type.name2.asParameterSpec(name, *modifiers).also(::parameter)

    public fun parameter(type: KClass<*>, name: String, vararg modifiers: Modifier): ParameterSpec =
        type.name.asParameterSpec(name, *modifiers).also(::parameter)

    public fun parametering(
        type: TypeName,
        vararg modifiers: Modifier,
    ): SpecDelegateProvider<ParameterSpec> =
        SpecDelegateProvider { type.asParameterSpec(it, *modifiers).also(::parameter) }

    public fun parametering(
        type: Class<*>,
        vararg modifiers: Modifier,
    ): SpecDelegateProvider<ParameterSpec> =
        SpecDelegateProvider { type.name2.asParameterSpec(it, *modifiers).also(::parameter) }

    public fun parametering(
        type: KClass<*>,
        vararg modifiers: Modifier,
    ): SpecDelegateProvider<ParameterSpec> =
        SpecDelegateProvider { type.name.asParameterSpec(it, *modifiers).also(::parameter) }
}

/**
 * Receiver for the `parameters` block providing an extended set of operators for the
 * configuration.
 */
@JavapoetDsl
public open class ParameterSpecHandlerScope private constructor(
    handler: ParameterSpecHandler,
) : ParameterSpecHandler by handler {
    public companion object {
        public fun of(handler: ParameterSpecHandler): ParameterSpecHandlerScope =
            ParameterSpecHandlerScope(handler)
    }

    /** @see parameter */
    public operator fun String.invoke(
        type: TypeName,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit,
    ): ParameterSpec =
        buildParameterSpec(type, this, *modifiers, configuration = configuration).also(::parameter)

    /** @see parameter */
    public operator fun String.invoke(
        type: Class<*>,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit,
    ): ParameterSpec =
        buildParameterSpec(type.name2, this, *modifiers, configuration = configuration)
            .also(::parameter)

    /** @see parameter */
    public operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit,
    ): ParameterSpec =
        buildParameterSpec(type.name, this, *modifiers, configuration = configuration)
            .also(::parameter)
}

/** Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder.*/
@JavapoetDsl
public class ParameterSpecBuilder(
    private val nativeBuilder: ParameterSpec.Builder,
) : AnnotationSpecHandler {
    public val annotations: MutableList<AnnotationSpec> get() = nativeBuilder.annotations
    public val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers

    public fun javadoc(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.addJavadoc(format2, *args2)
        }

    public fun javadoc(block: CodeBlock) {
        nativeBuilder.addJavadoc(block)
    }

    public override fun annotation(annotation: AnnotationSpec) {
        nativeBuilder.addAnnotation(annotation)
    }

    public fun modifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    public fun modifiers(modifiers: Iterable<Modifier>) {
        nativeBuilder.addModifiers(modifiers)
    }

    public fun build(): ParameterSpec = nativeBuilder.build()
}
