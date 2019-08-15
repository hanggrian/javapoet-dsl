package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationContainer
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier
import javax.lang.model.element.VariableElement
import kotlin.reflect.KClass

fun VariableElement.toParameterSpec(): ParameterSpec =
    ParameterSpec.get(this)

fun TypeName.toParameterSpec(name: String, vararg modifiers: Modifier): ParameterSpec =
    ParameterSpec.builder(this, name, *modifiers).build()

inline fun buildParameterSpec(
    type: TypeName,
    name: String,
    vararg modifiers: Modifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec =
    ParameterSpecBuilder(ParameterSpec.builder(type, name, *modifiers)).apply(builderAction).build()

fun KClass<*>.toParameterSpec(name: String, vararg modifiers: Modifier): ParameterSpec =
    ParameterSpec.builder(java, name, *modifiers).build()

inline fun buildParameterSpec(
    type: KClass<*>,
    name: String,
    vararg modifiers: Modifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec =
    ParameterSpecBuilder(ParameterSpec.builder(type.java, name, *modifiers)).apply(builderAction).build()

inline fun <reified T> buildParameterSpec(
    name: String,
    vararg modifiers: Modifier,
    builderAction: ParameterSpecBuilder.() -> Unit
): ParameterSpec =
    buildParameterSpec(T::class, name, *modifiers, builderAction = builderAction)

/** Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDslMarker
class ParameterSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: ParameterSpec.Builder) {

    val annotations: AnnotationContainer = object : AnnotationContainer() {
        override fun add(spec: AnnotationSpec): AnnotationSpec = spec.also { nativeBuilder.addAnnotation(it) }
    }

    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    fun build(): ParameterSpec = nativeBuilder.build()
}
