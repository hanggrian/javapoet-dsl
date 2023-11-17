@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

public fun ClassName.asAnnotationSpec(): AnnotationSpec = AnnotationSpec.builder(this).build()

/**
 * Creates new [AnnotationSpec] by populating newly created [AnnotationSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildAnnotationSpec(
    type: ClassName,
    configuration: AnnotationSpecBuilder.() -> Unit,
): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(configuration).build()
}

/**
 * Inserts new [AnnotationSpec] by populating newly created [AnnotationSpecBuilder] using provided
 * [configuration].
 */
public inline fun AnnotationSpecHandler.annotation(
    type: ClassName,
    configuration: AnnotationSpecBuilder.() -> Unit,
): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildAnnotationSpec(type, configuration).also(::annotation)
}

/**
 * Inserts new [AnnotationSpec] by populating newly created [AnnotationSpecBuilder] using provided
 * [configuration].
 */
public inline fun AnnotationSpecHandler.annotation(
    type: Class<*>,
    configuration: AnnotationSpecBuilder.() -> Unit,
): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildAnnotationSpec(type.name2, configuration).also(::annotation)
}

/**
 * Inserts new [AnnotationSpec] by populating newly created [AnnotationSpecBuilder] using provided
 * [configuration].
 */
public inline fun AnnotationSpecHandler.annotation(
    type: KClass<*>,
    configuration: AnnotationSpecBuilder.() -> Unit,
): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildAnnotationSpec(type.name, configuration).also(::annotation)
}

/** Convenient method to insert [AnnotationSpec] using reified type. */
public inline fun <reified T> AnnotationSpecHandler.annotation(): AnnotationSpec =
    T::class.name.asAnnotationSpec().also(::annotation)

/** Invokes DSL to configure [AnnotationSpec] collection. */
public fun AnnotationSpecHandler.annotations(configuration: AnnotationSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    AnnotationSpecHandlerScope.of(this).configuration()
}

/** Responsible for managing a set of [AnnotationSpec] instances. */
public interface AnnotationSpecHandler {
    public fun annotation(annotation: AnnotationSpec)

    public fun annotation(type: ClassName): AnnotationSpec =
        type.asAnnotationSpec().also(::annotation)

    public fun annotation(type: Class<*>): AnnotationSpec =
        type.name2.asAnnotationSpec().also(::annotation)

    public fun annotation(type: KClass<*>): AnnotationSpec =
        type.name.asAnnotationSpec().also(::annotation)
}

/**
 * Receiver for the `annotations` block providing an extended set of operators for the
 * configuration.
 */
@JavapoetDsl
public open class AnnotationSpecHandlerScope private constructor(
    handler: AnnotationSpecHandler,
) : AnnotationSpecHandler by handler {
    public companion object {
        public fun of(handler: AnnotationSpecHandler): AnnotationSpecHandlerScope =
            AnnotationSpecHandlerScope(handler)
    }

    /** @see annotation */
    public operator fun ClassName.invoke(
        configuration: AnnotationSpecBuilder.() -> Unit,
    ): AnnotationSpec = buildAnnotationSpec(this, configuration).also(::annotation)

    /** @see annotation */
    public operator fun Class<*>.invoke(
        configuration: AnnotationSpecBuilder.() -> Unit,
    ): AnnotationSpec = buildAnnotationSpec(name2, configuration).also(::annotation)

    /** @see annotation */
    public operator fun KClass<*>.invoke(
        configuration: AnnotationSpecBuilder.() -> Unit,
    ): AnnotationSpec = buildAnnotationSpec(name, configuration).also(::annotation)
}

/** Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDsl
public class AnnotationSpecBuilder(
    private val nativeBuilder: AnnotationSpec.Builder,
) {
    public val members: Map<String, List<CodeBlock>> get() = nativeBuilder.members

    public fun member(name: String, format: String, vararg args: Any): Unit =
        member(name, codeBlockOf(format, *args))

    public fun member(name: String, code: CodeBlock) {
        nativeBuilder.addMember(name, code)
    }

    public fun build(): AnnotationSpec = nativeBuilder.build()
}
