package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationSpecContainer
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import javax.lang.model.element.VariableElement
import kotlin.reflect.KClass

/** Converts element to [ParameterSpec]. */
fun VariableElement.toParameter(): ParameterSpec = ParameterSpec.get(this)

/** Builds a new [ParameterSpec] from [type]. */
fun buildParameter(type: TypeName, name: String, vararg modifiers: Modifier): ParameterSpec =
    ParameterSpec.builder(type, name, *modifiers).build()

/**
 * Builds a new [ParameterSpec] from [type],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildParameter(
    type: TypeName,
    name: String,
    vararg modifiers: Modifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(type, name, *modifiers)).apply(builderAction).build()

/** Builds a new [ParameterSpec] from [type]. */
fun buildParameter(type: Type, name: String, vararg modifiers: Modifier): ParameterSpec =
    ParameterSpec.builder(type, name, *modifiers).build()

/** Builds a new [ParameterSpec] from [type]. */
fun buildParameter(type: KClass<*>, name: String, vararg modifiers: Modifier): ParameterSpec =
    buildParameter(type.java, name, *modifiers)

/** Builds a new [ParameterSpec] from [T]. */
inline fun <reified T> buildParameter(name: String, vararg modifiers: Modifier): ParameterSpec =
    buildParameter(T::class, name, *modifiers)

/**
 * Builds a new [ParameterSpec] from [type],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildParameter(
    type: Type,
    name: String,
    vararg modifiers: Modifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(type, name, *modifiers)).apply(builderAction).build()

/**
 * Builds a new [ParameterSpec] from [type],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildParameter(
    type: KClass<*>,
    name: String,
    vararg modifiers: Modifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = buildParameter(type.java, name, *modifiers, builderAction = builderAction)

/**
 * Builds a new [ParameterSpec] from [T],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun <reified T> buildParameter(
    name: String,
    vararg modifiers: Modifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = buildParameter(T::class, name, *modifiers, builderAction = builderAction)

/** Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDslMarker
class ParameterSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: ParameterSpec.Builder) {

    /** Collection of annotations, may be configured with Kotlin DSL. */
    val annotations: AnnotationSpecContainer = object : AnnotationSpecContainer() {
        override fun add(spec: AnnotationSpec) {
            nativeBuilder.addAnnotation(spec)
        }
    }

    /** Add field modifiers. */
    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Returns native spec. */
    fun build(): ParameterSpec = nativeBuilder.build()
}
