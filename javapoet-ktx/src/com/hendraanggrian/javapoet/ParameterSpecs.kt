package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationContainer
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier
import javax.lang.model.element.VariableElement
import kotlin.reflect.KClass

object ParameterSpecs {

    operator fun get(element: VariableElement): ParameterSpec =
        ParameterSpec.get(element)

    fun of(type: TypeName, name: String, vararg modifiers: Modifier): ParameterSpec =
        ParameterSpec.builder(type, name, *modifiers).build()

    inline fun of(
        type: TypeName,
        name: String,
        vararg modifiers: Modifier,
        builderAction: Builder.() -> Unit
    ): ParameterSpec =
        Builder(ParameterSpec.builder(type, name, *modifiers)).apply(builderAction).build()

    fun of(type: KClass<*>, name: String, vararg modifiers: Modifier): ParameterSpec =
        ParameterSpec.builder(type.java, name, *modifiers).build()

    inline fun of(
        type: KClass<*>,
        name: String,
        vararg modifiers: Modifier,
        builderAction: Builder.() -> Unit
    ): ParameterSpec =
        Builder(ParameterSpec.builder(type.java, name, *modifiers)).apply(builderAction).build()

    inline fun <reified T> of(name: String, vararg modifiers: Modifier) =
        of(T::class, name, *modifiers)

    inline fun <reified T> of(name: String, vararg modifiers: Modifier, builderAction: Builder.() -> Unit) =
        of(T::class, name, *modifiers, builderAction = builderAction)

    /** Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder. */
    @JavapoetDslMarker
    class Builder @PublishedApi internal constructor(private val nativeBuilder: ParameterSpec.Builder) {

        val annotations: AnnotationContainer = object : AnnotationContainer() {
            override fun add(spec: AnnotationSpec): AnnotationSpec = spec.also { nativeBuilder.addAnnotation(it) }
        }

        fun addModifiers(vararg modifiers: Modifier) {
            nativeBuilder.addModifiers(*modifiers)
        }

        fun build(): ParameterSpec = nativeBuilder.build()
    }
}
