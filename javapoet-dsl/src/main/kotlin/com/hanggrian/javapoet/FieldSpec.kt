@file:OptIn(ExperimentalContracts::class)

package com.hanggrian.javapoet

import com.hanggrian.javapoet.internals.Internals
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/** Creates new [TypeName] using parameters. */
public inline fun fieldSpecOf(type: TypeName, name: String, vararg modifiers: Modifier): FieldSpec =
    FieldSpec
        .builder(type, name, *modifiers)
        .build()

/**
 * Builds new [FieldSpec] by populating newly created [FieldSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildFieldSpec(
    type: TypeName,
    name: String,
    vararg modifiers: Modifier,
    configuration: FieldSpecBuilder.() -> Unit,
): FieldSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FieldSpecBuilder(FieldSpec.builder(type, name, *modifiers))
        .apply(configuration)
        .build()
}

/**
 * Inserts new [FieldSpec] by populating newly created [FieldSpecBuilder] using provided
 * [configuration].
 */
public inline fun FieldSpecHandler.add(
    type: TypeName,
    name: String,
    vararg modifiers: Modifier,
    configuration: FieldSpecBuilder.() -> Unit,
): FieldSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FieldSpecBuilder(FieldSpec.builder(type, name, *modifiers))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new [FieldSpec] by populating newly created [FieldSpecBuilder] using provided
 * [configuration].
 */
public inline fun FieldSpecHandler.add(
    type: Type,
    name: String,
    vararg modifiers: Modifier,
    configuration: FieldSpecBuilder.() -> Unit,
): FieldSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FieldSpecBuilder(FieldSpec.builder(type, name, *modifiers))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new [FieldSpec] by populating newly created [FieldSpecBuilder] using provided
 * [configuration].
 */
public inline fun FieldSpecHandler.add(
    type: KClass<*>,
    name: String,
    vararg modifiers: Modifier,
    configuration: FieldSpecBuilder.() -> Unit,
): FieldSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return FieldSpecBuilder(FieldSpec.builder(type.java, name, *modifiers))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Property delegate for inserting new [FieldSpec] by populating newly created [FieldSpecBuilder]
 * using provided [configuration].
 */
public fun FieldSpecHandler.adding(
    type: TypeName,
    vararg modifiers: Modifier,
    configuration: FieldSpecBuilder.() -> Unit,
): SpecDelegateProvider<FieldSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        FieldSpecBuilder(FieldSpec.builder(type, it, *modifiers))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/**
 * Property delegate for inserting new [FieldSpec] by populating newly created [FieldSpecBuilder]
 * using provided [configuration].
 */
public fun FieldSpecHandler.adding(
    type: Type,
    vararg modifiers: Modifier,
    configuration: FieldSpecBuilder.() -> Unit,
): SpecDelegateProvider<FieldSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        FieldSpecBuilder(FieldSpec.builder(type, it, *modifiers))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/**
 * Property delegate for inserting new [FieldSpec] by populating newly created [FieldSpecBuilder]
 * using provided [configuration].
 */
public fun FieldSpecHandler.adding(
    type: KClass<*>,
    vararg modifiers: Modifier,
    configuration: FieldSpecBuilder.() -> Unit,
): SpecDelegateProvider<FieldSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        FieldSpecBuilder(FieldSpec.builder(type.java, it, *modifiers))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/** Convenient method to insert [FieldSpec] using reified type. */
public inline fun <reified T> FieldSpecHandler.add(
    name: String,
    vararg modifiers: Modifier,
): FieldSpec =
    FieldSpec
        .builder(T::class.java, name, *modifiers)
        .build()
        .also(::add)

/** Responsible for managing a set of [FieldSpec] instances. */
public interface FieldSpecHandler {
    public fun add(field: FieldSpec)

    public fun add(type: TypeName, name: String, vararg modifiers: Modifier): FieldSpec =
        fieldSpecOf(type, name, *modifiers).also(::add)

    public fun add(type: Type, name: String, vararg modifiers: Modifier): FieldSpec =
        fieldSpecOf(type.name, name, *modifiers).also(::add)

    public fun add(type: KClass<*>, name: String, vararg modifiers: Modifier): FieldSpec =
        fieldSpecOf(type.name, name, *modifiers).also(::add)

    public fun adding(type: TypeName, vararg modifiers: Modifier): SpecDelegateProvider<FieldSpec> =
        SpecDelegateProvider { fieldSpecOf(type, it, *modifiers).also(::add) }

    public fun adding(type: Type, vararg modifiers: Modifier): SpecDelegateProvider<FieldSpec> =
        SpecDelegateProvider { fieldSpecOf(type.name, it, *modifiers).also(::add) }

    public fun adding(
        type: KClass<*>,
        vararg modifiers: Modifier,
    ): SpecDelegateProvider<FieldSpec> =
        SpecDelegateProvider { fieldSpecOf(type.name, it, *modifiers).also(::add) }
}

/** Receiver for the `fields` block providing an extended set of operators for the configuration. */
@JavaPoetDsl
public open class FieldSpecHandlerScope private constructor(handler: FieldSpecHandler) :
    FieldSpecHandler by handler {
        public inline operator fun String.invoke(
            type: TypeName,
            vararg modifiers: Modifier,
            configuration: FieldSpecBuilder.() -> Unit,
        ): FieldSpec = add(type, this, *modifiers, configuration = configuration)

        public inline operator fun String.invoke(
            type: Type,
            vararg modifiers: Modifier,
            configuration: FieldSpecBuilder.() -> Unit,
        ): FieldSpec = add(type, this, *modifiers, configuration = configuration)

        public inline operator fun String.invoke(
            type: KClass<*>,
            vararg modifiers: Modifier,
            configuration: FieldSpecBuilder.() -> Unit,
        ): FieldSpec = add(type, this, *modifiers, configuration = configuration)

        public companion object {
            public fun of(handler: FieldSpecHandler): FieldSpecHandlerScope =
                FieldSpecHandlerScope(handler)
        }
    }

/** Wrapper of [FieldSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavaPoetDsl
public class FieldSpecBuilder(private val nativeBuilder: FieldSpec.Builder) {
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

    public var initializer: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.initializer(value)
        }

    public fun setInitializer(format: String, vararg args: Any) {
        initializer = codeBlockOf(format, *args)
    }

    public fun build(): FieldSpec = nativeBuilder.build()
}
