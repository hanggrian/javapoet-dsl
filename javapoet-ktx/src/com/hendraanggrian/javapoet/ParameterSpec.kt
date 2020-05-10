package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.collections.AnnotationSpecList
import com.hendraanggrian.javapoet.collections.AnnotationSpecListScope
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import javax.lang.model.element.VariableElement
import kotlin.reflect.KClass

/** Converts element to [ParameterSpec]. */
fun VariableElement.asParameterSpec(): ParameterSpec =
    ParameterSpec.get(this)

/** Builds a new [ParameterSpec] from [type]. */
fun parameterSpecOf(type: TypeName, name: String, vararg modifiers: Modifier): ParameterSpec =
    ParameterSpec.builder(type, name, *modifiers).build()

/**
 * Builds a new [ParameterSpec] from [type],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildParameterSpec(
    type: TypeName,
    name: String,
    vararg modifiers: Modifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpec.builder(type, name, *modifiers).build(builderAction)

/** Builds a new [ParameterSpec] from [type]. */
fun parameterSpecOf(type: Type, name: String, vararg modifiers: Modifier): ParameterSpec =
    ParameterSpec.builder(type, name, *modifiers).build()

/** Builds a new [ParameterSpec] from [type]. */
fun parameterSpecOf(type: KClass<*>, name: String, vararg modifiers: Modifier): ParameterSpec =
    parameterSpecOf(type.java, name, *modifiers)

/** Builds a new [ParameterSpec] from [T]. */
inline fun <reified T> parameterSpecOf(name: String, vararg modifiers: Modifier): ParameterSpec =
    parameterSpecOf(T::class, name, *modifiers)

/**
 * Builds a new [ParameterSpec] from [type],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildParameterSpec(
    type: Type,
    name: String,
    vararg modifiers: Modifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = ParameterSpec.builder(type, name, *modifiers).build(builderAction)

/**
 * Builds a new [ParameterSpec] from [type],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildParameterSpec(
    type: KClass<*>,
    name: String,
    vararg modifiers: Modifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = buildParameterSpec(type.java, name, *modifiers, builderAction = builderAction)

/**
 * Builds a new [ParameterSpec] from [T],
 * by populating newly created [ParameterSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun <reified T> buildParameterSpec(
    name: String,
    vararg modifiers: Modifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec = buildParameterSpec(T::class, name, *modifiers, builderAction = builderAction)

/** Modify existing [ParameterSpec.Builder] using provided [builderAction] and then building it. */
inline fun ParameterSpec.Builder.build(builderAction: ParameterSpecBuilder.() -> Unit): ParameterSpec =
    ParameterSpecBuilder(this).apply(builderAction).build()

/** Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDslMarker
class ParameterSpecBuilder(private val nativeBuilder: ParameterSpec.Builder) {

    /** Modifiers of this parameter. */
    val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers

    /** Configure annotations without DSL. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configure annotations with DSL. */
    inline fun annotations(configuration: AnnotationSpecListScope.() -> Unit) =
        AnnotationSpecListScope(annotations).configuration()

    /** Add parameter modifiers. */
    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Returns native spec. */
    fun build(): ParameterSpec = nativeBuilder.build()
}
