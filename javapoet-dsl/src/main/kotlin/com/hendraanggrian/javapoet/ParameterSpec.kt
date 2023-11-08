@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import javax.lang.model.element.VariableElement
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/** Converts element to [ParameterSpec]. */
inline fun VariableElement.toParameterSpec(): ParameterSpec = ParameterSpec.get(this)

/**
 * Builds new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
inline fun buildParameterSpec(
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
inline fun ParameterSpecHandler.parameter(
    type: TypeName,
    name: String,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildParameterSpec(type, name, *modifiers, configuration = configuration)
        .also(parameters::add)
}

/**
 * Inserts new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
inline fun ParameterSpecHandler.parameter(
    type: Type,
    name: String,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildParameterSpec(type.asTypeName(), name, *modifiers, configuration = configuration)
        .also(parameters::add)
}

/**
 * Inserts new [ParameterSpec] by populating newly created [ParameterSpecBuilder] using provided
 * [configuration].
 */
inline fun ParameterSpecHandler.parameter(
    type: KClass<*>,
    name: String,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): ParameterSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildParameterSpec(type.asTypeName(), name, *modifiers, configuration = configuration)
        .also(parameters::add)
}

/**
 * Property delegate for inserting new [ParameterSpec] by populating newly created
 * [ParameterSpecBuilder] using provided [configuration].
 */
fun ParameterSpecHandler.parametering(
    type: TypeName,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): SpecDelegateProvider<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        buildParameterSpec(type, it, *modifiers, configuration = configuration)
            .also(parameters::add)
    }
}

/**
 * Property delegate for inserting new [ParameterSpec] by populating newly created
 * [ParameterSpecBuilder] using provided [configuration].
 */
fun ParameterSpecHandler.parametering(
    type: Type,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): SpecDelegateProvider<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        buildParameterSpec(type.asTypeName(), it, *modifiers, configuration = configuration)
            .also(parameters::add)
    }
}

/**
 * Property delegate for inserting new [ParameterSpec] by populating newly created
 * [ParameterSpecBuilder] using provided [configuration].
 */
fun ParameterSpecHandler.parametering(
    type: KClass<*>,
    vararg modifiers: Modifier,
    configuration: ParameterSpecBuilder.() -> Unit,
): SpecDelegateProvider<ParameterSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        buildParameterSpec(type.asTypeName(), it, *modifiers, configuration = configuration)
            .also(parameters::add)
    }
}

/** Convenient method to insert [ParameterSpec] using reified type. */
inline fun <reified T> ParameterSpecHandler.parameter(
    name: String,
    vararg modifiers: Modifier,
): ParameterSpec =
    ParameterSpec.builder(T::class.java, name, *modifiers).build().also(parameters::add)

/** Invokes DSL to configure [ParameterSpec] collection. */
fun ParameterSpecHandler.parameters(configuration: ParameterSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    ParameterSpecHandlerScope(parameters).configuration()
}

/** Responsible for managing a set of [ParameterSpec] instances. */
sealed interface ParameterSpecHandler {
    val parameters: MutableList<ParameterSpec>

    fun parameter(type: TypeName, name: String, vararg modifiers: Modifier): ParameterSpec =
        ParameterSpec.builder(type, name, *modifiers).build().also(parameters::add)

    fun parameter(type: Type, name: String, vararg modifiers: Modifier): ParameterSpec =
        ParameterSpec.builder(type, name, *modifiers).build().also(parameters::add)

    fun parameter(type: KClass<*>, name: String, vararg modifiers: Modifier): ParameterSpec =
        ParameterSpec.builder(type.java, name, *modifiers).build().also(parameters::add)

    fun parametering(
        type: TypeName,
        vararg modifiers: Modifier,
    ): SpecDelegateProvider<ParameterSpec> =
        SpecDelegateProvider {
            ParameterSpec.builder(type, it, *modifiers).build().also(parameters::add)
        }

    fun parametering(type: Type, vararg modifiers: Modifier): SpecDelegateProvider<ParameterSpec> =
        SpecDelegateProvider {
            ParameterSpec.builder(type, it, *modifiers).build().also(parameters::add)
        }

    fun parametering(
        type: KClass<*>,
        vararg modifiers: Modifier,
    ): SpecDelegateProvider<ParameterSpec> =
        SpecDelegateProvider {
            ParameterSpec.builder(type.java, it, *modifiers).build().also(parameters::add)
        }
}

/**
 * Receiver for the `parameters` block providing an extended set of operators for the
 * configuration.
 */
@JavapoetSpecDsl
class ParameterSpecHandlerScope internal constructor(
    actualList: MutableList<ParameterSpec>,
) : MutableList<ParameterSpec> by actualList {
    /** @see parameter */
    operator fun String.invoke(
        type: TypeName,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit,
    ): ParameterSpec =
        buildParameterSpec(type, this, *modifiers, configuration = configuration)
            .also(::add)

    /** @see parameter */
    operator fun String.invoke(
        type: Type,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit,
    ): ParameterSpec =
        buildParameterSpec(type.asTypeName(), this, *modifiers, configuration = configuration)
            .also(::add)

    /** @see parameter */
    operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: Modifier,
        configuration: ParameterSpecBuilder.() -> Unit,
    ): ParameterSpec =
        buildParameterSpec(type.asTypeName(), this, *modifiers, configuration = configuration)
            .also(::add)
}

/** Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder.*/
@JavapoetSpecDsl
class ParameterSpecBuilder(
    private val nativeBuilder: ParameterSpec.Builder,
) : AnnotationSpecHandler {
    override val annotations: MutableList<AnnotationSpec> = nativeBuilder.annotations
    val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers

    val javadoc: JavadocContainer =
        object : JavadocContainer {
            override fun append(format: String, vararg args: Any): Unit =
                format.internalFormat(args) { format2, args2 ->
                    nativeBuilder.addJavadoc(format2, *args2)
                }

            override fun append(code: CodeBlock) {
                nativeBuilder.addJavadoc(code)
            }
        }

    fun modifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    fun build(): ParameterSpec = nativeBuilder.build()
}
