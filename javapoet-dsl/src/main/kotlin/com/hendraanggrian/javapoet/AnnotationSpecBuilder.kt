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
inline fun Annotation.asAnnotationSpec(includeDefaultValues: Boolean = false): AnnotationSpec =
    AnnotationSpec.get(this, includeDefaultValues)

/** Converts [AnnotationMirror] to [AnnotationSpec]. */
inline fun AnnotationMirror.asAnnotationSpec(): AnnotationSpec = AnnotationSpec.get(this)

/**
 * Builds new [AnnotationSpec] from [ClassName],
 * by populating newly created [AnnotationSpecBuilder] using provided [configuration].
 */
inline fun buildAnnotationSpec(type: ClassName, configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(configuration).build()
}

/**
 * Builds new [AnnotationSpec] from [Class],
 * by populating newly created [AnnotationSpecBuilder] using provided [configuration].
 */
inline fun buildAnnotationSpec(type: Class<*>, configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return AnnotationSpecBuilder(AnnotationSpec.builder(type)).apply(configuration).build()
}

/**
 * Builds new [AnnotationSpec] from [KClass],
 * by populating newly created [AnnotationSpecBuilder] using provided [configuration].
 */
inline fun buildAnnotationSpec(type: KClass<*>, configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return AnnotationSpecBuilder(AnnotationSpec.builder(type.java)).apply(configuration).build()
}

/**
 * Builds new [AnnotationSpec] from [T],
 * by populating newly created [AnnotationSpecBuilder] using provided [configuration].
 */
inline fun <reified T> buildAnnotationSpec(configuration: AnnotationSpecBuilder.() -> Unit): AnnotationSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return AnnotationSpecBuilder(AnnotationSpec.builder(T::class.java)).apply(configuration).build()
}

/**
 * Wrapper of [AnnotationSpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@JavapoetSpecMarker
class AnnotationSpecBuilder(private val nativeBuilder: AnnotationSpec.Builder) {
    val members: Map<String, List<CodeBlock>> get() = nativeBuilder.members

    /** Add code as a member of this annotation. */
    fun addMember(name: String, format: String, vararg args: Any): Unit = addMember(name, codeBlockOf(format, *args))

    /** Add code as a member of this annotation. */
    fun addMember(name: String, code: CodeBlock) {
        nativeBuilder.addMember(name, code)
    }

    /** Returns native spec. */
    fun build(): AnnotationSpec = nativeBuilder.build()
}
