package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationContainer
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

class ParameterSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: ParameterSpec.Builder) {

    @PublishedApi
    @Suppress("NOTHING_TO_INLINE")
    internal companion object {
        inline fun of(
            type: TypeName,
            name: String,
            noinline builder: (ParameterSpecBuilder.() -> Unit)? = null
        ): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(type, name))
            .also { builder?.invoke(it) }
            .build()

        inline fun of(
            type: KClass<*>,
            name: String,
            noinline builder: (ParameterSpecBuilder.() -> Unit)? = null
        ): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(type.java, name))
            .also { builder?.invoke(it) }
            .build()
    }

    val annotations: AnnotationContainer = object : AnnotationContainer() {
        override fun add(spec: AnnotationSpec): AnnotationSpec = spec.also { nativeBuilder.addAnnotation(it) }
    }

    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    @PublishedApi
    internal fun build(): ParameterSpec = nativeBuilder.build()
}