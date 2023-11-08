@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import javax.lang.model.element.AnnotationMirror
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/** Converts [Annotation] to [AnnotationSpec]. */
inline fun Annotation.toAnnotationSpec(includeDefaultValues: Boolean = false): AnnotationSpec =
    AnnotationSpec.get(this, includeDefaultValues)

/** Converts [AnnotationMirror] to [AnnotationSpec]. */
inline fun AnnotationMirror.toAnnotationSpec(): AnnotationSpec = AnnotationSpec.get(this)

/** Converts [ClassName] to [AnnotationSpec]. */
inline fun ClassName.toAnnotationSpec(): AnnotationSpec = AnnotationSpec.builder(this).build()

/** Converts [Class] to [AnnotationSpec]. */
inline fun Class<*>.toAnnotationSpec(): AnnotationSpec = AnnotationSpec.builder(this).build()

/** Converts [KClass] to [AnnotationSpec]. */
inline fun KClass<*>.toAnnotationSpec(): AnnotationSpec = AnnotationSpec.builder(java).build()

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
    type: Class<*>,
    configuration: AnnotationSpecBuilder.() -> Unit,
): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildAnnotationSpec(type.asClassName(), configuration).also(annotations::add)
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
    return buildAnnotationSpec(type.asClassName(), configuration).also(annotations::add)
}

/** Convenient method to insert [AnnotationSpec] using reified type. */
inline fun <reified T> AnnotationSpecHandler.annotation(): AnnotationSpec =
    AnnotationSpec.builder(T::class.java).build().also(annotations::add)

/** Invokes DSL to configure [AnnotationSpec] collection. */
fun AnnotationSpecHandler.annotations(configuration: AnnotationSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    AnnotationSpecHandlerScope(annotations).configuration()
}

/** Responsible for managing a set of [AnnotationSpec] instances. */
sealed interface AnnotationSpecHandler {
    val annotations: MutableList<AnnotationSpec>

    fun annotation(type: ClassName): AnnotationSpec =
        AnnotationSpec.builder(type).build().also(annotations::add)

    fun annotation(type: Class<*>): AnnotationSpec =
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
    actualList: MutableList<AnnotationSpec>,
) : MutableList<AnnotationSpec> by actualList

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
