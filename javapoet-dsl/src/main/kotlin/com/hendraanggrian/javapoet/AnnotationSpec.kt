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
    return buildAnnotationSpec(type, configuration).also(annotations::add)
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
    return buildAnnotationSpec(type.name, configuration).also(annotations::add)
}

/** Convenient method to insert [AnnotationSpec] using reified type. */
inline fun <reified T> AnnotationSpecHandler.annotation(): AnnotationSpec =
    AnnotationSpec.builder(T::class.java).build().also(annotations::add)

/** Invokes DSL to configure [AnnotationSpec] collection. */
fun AnnotationSpecHandler.annotations(configuration: AnnotationSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    AnnotationSpecHandlerScope(this).configuration()
}

/** Responsible for managing a set of [AnnotationSpec] instances. */
sealed interface AnnotationSpecHandler {
    val annotations: MutableList<AnnotationSpec>

    fun annotation(type: ClassName): AnnotationSpec =
        AnnotationSpec.builder(type).build().also(annotations::add)

    fun annotation(type: KClass<*>): AnnotationSpec =
        AnnotationSpec.builder(type.java).build().also(annotations::add)
}

/**
 * Receiver for the `annotations` block providing an extended set of operators for the
 * configuration.
 */
@JavapoetSpecDsl
class AnnotationSpecHandlerScope internal constructor(
    handler: AnnotationSpecHandler,
) : AnnotationSpecHandler by handler {
    /** @see annotation */
    operator fun ClassName.invoke(
        configuration: AnnotationSpecBuilder.() -> Unit,
    ): AnnotationSpec =
        buildAnnotationSpec(this, configuration)
            .also { this@AnnotationSpecHandlerScope.annotations.add(it) }

    /** @see annotation */
    operator fun KClass<*>.invoke(
        configuration: AnnotationSpecBuilder.() -> Unit,
    ): AnnotationSpec =
        buildAnnotationSpec(name, configuration)
            .also { this@AnnotationSpecHandlerScope.annotations.add(it) }
}

/** Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetSpecDsl
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
