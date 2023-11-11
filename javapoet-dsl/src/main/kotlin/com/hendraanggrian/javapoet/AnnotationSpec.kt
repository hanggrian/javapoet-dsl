@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/**
 * Creates new [AnnotationSpec] by populating newly created [AnnotationSpecBuilder] using provided
 * [configuration].
 */
inline fun buildAnnotationSpec(
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
inline fun AnnotationSpecHandler.annotation(
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
inline fun AnnotationSpecHandler.annotation(
    type: KClass<*>,
    configuration: AnnotationSpecBuilder.() -> Unit,
): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildAnnotationSpec(type.name, configuration).also(::annotation)
}

/** Convenient method to insert [AnnotationSpec] using reified type. */
inline fun <reified T> AnnotationSpecHandler.annotation(): AnnotationSpec =
    AnnotationSpec.builder(T::class.java).build().also(::annotation)

/** Invokes DSL to configure [AnnotationSpec] collection. */
fun AnnotationSpecHandler.annotations(configuration: AnnotationSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    AnnotationSpecHandlerScope(this).configuration()
}

/** Responsible for managing a set of [AnnotationSpec] instances. */
sealed interface AnnotationSpecHandler {
    fun annotation(annotation: AnnotationSpec)

    fun annotation(type: ClassName): AnnotationSpec =
        AnnotationSpec.builder(type).build().also(::annotation)

    fun annotation(type: KClass<*>): AnnotationSpec =
        AnnotationSpec.builder(type.java).build().also(::annotation)
}

/**
 * Receiver for the `annotations` block providing an extended set of operators for the
 * configuration.
 */
@JavapoetDsl
class AnnotationSpecHandlerScope internal constructor(
    handler: AnnotationSpecHandler,
) : AnnotationSpecHandler by handler {
    /** @see annotation */
    operator fun ClassName.invoke(
        configuration: AnnotationSpecBuilder.() -> Unit,
    ): AnnotationSpec = buildAnnotationSpec(this, configuration).also(::annotation)

    /** @see annotation */
    operator fun KClass<*>.invoke(
        configuration: AnnotationSpecBuilder.() -> Unit,
    ): AnnotationSpec = buildAnnotationSpec(name, configuration).also(::annotation)
}

/** Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDsl
class AnnotationSpecBuilder(
    private val nativeBuilder: AnnotationSpec.Builder,
) {
    val members: Map<String, List<CodeBlock>> get() = nativeBuilder.members

    fun member(name: String, format: String, vararg args: Any): Unit =
        member(name, codeBlockOf(format, *args))

    fun member(name: String, code: CodeBlock) {
        nativeBuilder.addMember(name, code)
    }

    fun build(): AnnotationSpec = nativeBuilder.build()
}
