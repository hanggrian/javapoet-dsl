@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationContainer
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

inline fun buildParameterSpec(
    type: TypeName,
    name: String,
    noinline builder: (ParameterSpecBuilder.() -> Unit)? = null
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(type, name))
    .also { builder?.invoke(it) }
    .build()

inline fun buildParameterSpec(
    type: KClass<*>,
    name: String,
    noinline builder: (ParameterSpecBuilder.() -> Unit)? = null
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(type.java, name))
    .also { builder?.invoke(it) }
    .build()

inline fun <reified T> buildParameterSpec(
    name: String,
    noinline builder: (ParameterSpecBuilder.() -> Unit)? = null
): ParameterSpec = buildParameterSpec(T::class, name, builder)

class ParameterSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: ParameterSpec.Builder) {

    val annotations: AnnotationContainer = object : AnnotationContainer() {
        override fun add(spec: AnnotationSpec): AnnotationSpec = spec.also { nativeBuilder.addAnnotation(it) }
    }

    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    fun build(): ParameterSpec = nativeBuilder.build()
}