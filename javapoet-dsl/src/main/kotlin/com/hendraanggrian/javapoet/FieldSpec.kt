@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/**
 * Creates new [FieldSpec] by populating newly created [FieldSpecBuilder] using provided
 * [configuration].
 */
inline fun buildFieldSpec(
    type: TypeName,
    name: String,
    vararg modifiers: Modifier,
    configuration: FieldSpecBuilder.() -> Unit,
): FieldSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FieldSpecBuilder(FieldSpec.builder(type, name, *modifiers)).apply(configuration).build()
}

/**
 * Inserts new [FieldSpec] by populating newly created [FieldSpecBuilder] using provided
 * [configuration].
 */
inline fun FieldSpecHandler.field(
    type: TypeName,
    name: String,
    vararg modifiers: Modifier,
    configuration: FieldSpecBuilder.() -> Unit,
): FieldSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildFieldSpec(type, name, *modifiers, configuration = configuration).also(fields::add)
}

/**
 * Inserts new [FieldSpec] by populating newly created [FieldSpecBuilder] using provided
 * [configuration].
 */
inline fun FieldSpecHandler.field(
    type: KClass<*>,
    name: String,
    vararg modifiers: Modifier,
    configuration: FieldSpecBuilder.() -> Unit,
): FieldSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildFieldSpec(type.name, name, *modifiers, configuration = configuration)
        .also(fields::add)
}

/**
 * Property delegate for inserting new [FieldSpec] by populating newly created [FieldSpecBuilder]
 * using provided [configuration].
 */
fun FieldSpecHandler.fielding(
    type: TypeName,
    vararg modifiers: Modifier,
    configuration: FieldSpecBuilder.() -> Unit,
): SpecDelegateProvider<FieldSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        buildFieldSpec(type, it, *modifiers, configuration = configuration).also(fields::add)
    }
}

/**
 * Property delegate for inserting new [FieldSpec] by populating newly created [FieldSpecBuilder]
 * using provided [configuration].
 */
fun FieldSpecHandler.fielding(
    type: KClass<*>,
    vararg modifiers: Modifier,
    configuration: FieldSpecBuilder.() -> Unit,
): SpecDelegateProvider<FieldSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        buildFieldSpec(type.name, it, *modifiers, configuration = configuration)
            .also(fields::add)
    }
}

/** Convenient method to insert [FieldSpec] using reified type. */
inline fun <reified T> FieldSpecHandler.field(
    name: String,
    vararg modifiers: Modifier,
): FieldSpec = FieldSpec.builder(T::class.java, name, *modifiers).build().also(fields::add)

/** Invokes DSL to configure [FieldSpec] collection. */
fun FieldSpecHandler.fields(configuration: FieldSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    FieldSpecHandlerScope(this).configuration()
}

/** Responsible for managing a set of [FieldSpec] instances. */
sealed interface FieldSpecHandler {
    val fields: MutableList<FieldSpec>

    fun field(type: TypeName, name: String, vararg modifiers: Modifier): FieldSpec =
        FieldSpec.builder(type, name, *modifiers).build().also(fields::add)

    fun field(type: KClass<*>, name: String, vararg modifiers: Modifier): FieldSpec =
        FieldSpec.builder(type.java, name, *modifiers).build().also(fields::add)

    fun fielding(type: TypeName, vararg modifiers: Modifier): SpecDelegateProvider<FieldSpec> =
        SpecDelegateProvider { FieldSpec.builder(type, it, *modifiers).build().also(fields::add) }

    fun fielding(type: KClass<*>, vararg modifiers: Modifier): SpecDelegateProvider<FieldSpec> =
        SpecDelegateProvider {
            FieldSpec.builder(type.java, it, *modifiers).build().also(fields::add)
        }
}

/** Receiver for the `fields` block providing an extended set of operators for the configuration. */
@JavapoetSpecDsl
class FieldSpecHandlerScope internal constructor(
    handler: FieldSpecHandler,
) : FieldSpecHandler by handler {
    /** @see field */
    operator fun String.invoke(
        type: TypeName,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit,
    ): FieldSpec =
        buildFieldSpec(type, this, *modifiers, configuration = configuration)
            .also(fields::add)

    /** @see field */
    operator fun String.invoke(
        type: KClass<*>,
        vararg modifiers: Modifier,
        configuration: FieldSpecBuilder.() -> Unit,
    ): FieldSpec =
        buildFieldSpec(type.name, this, *modifiers, configuration = configuration)
            .also(fields::add)
}

/** Wrapper of [FieldSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetSpecDsl
class FieldSpecBuilder(
    private val nativeBuilder: FieldSpec.Builder,
) : AnnotationSpecHandler {
    override val annotations: MutableList<AnnotationSpec> get() = nativeBuilder.annotations
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

    fun initializer(format: String, vararg args: Any) {
        initializer = codeBlockOf(format, *args)
    }

    var initializer: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.initializer(value)
        }

    fun build(): FieldSpec = nativeBuilder.build()
}
