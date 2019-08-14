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

    fun of(type: TypeName, name: String): ParameterSpec =
        ParameterSpec.builder(type, name).build()

    inline fun of(type: TypeName, name: String, builderAction: Builder.() -> Unit): ParameterSpec =
        Builder(ParameterSpec.builder(type, name)).apply(builderAction).build()

    fun of(type: KClass<*>, name: String): ParameterSpec =
        ParameterSpec.builder(type.java, name).build()

    inline fun of(type: KClass<*>, name: String, builderAction: Builder.() -> Unit): ParameterSpec =
        Builder(ParameterSpec.builder(type.java, name)).apply(builderAction).build()

    inline fun <reified T> of(name: String) =
        of(T::class, name)

    inline fun <reified T> of(name: String, builderAction: Builder.() -> Unit) =
        of(T::class, name, builderAction = builderAction)

    /** Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder. */
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
