@file:JvmMultifileClass
@file:JvmName("SpecBuildersKt")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationContainer
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ParameterSpec
import javax.lang.model.element.Modifier

/** Configure [this] spec with DSL. */
inline operator fun ParameterSpec.invoke(builder: ParameterSpecBuilder.() -> Unit): ParameterSpec =
    toBuilder()(builder)

/** Configure [this] builder with DSL. */
inline operator fun ParameterSpec.Builder.invoke(builder: ParameterSpecBuilder.() -> Unit): ParameterSpec =
    ParameterSpecBuilder(this).apply(builder).build()

/** Wrapper of [ParameterSpec.Builder], providing DSL support as a replacement to Java builder. */
class ParameterSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: ParameterSpec.Builder) {

    val annotations: AnnotationContainer = object : AnnotationContainer() {
        override fun add(spec: AnnotationSpec): AnnotationSpec = spec.also { nativeBuilder.addAnnotation(it) }
    }

    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    fun build(): ParameterSpec = nativeBuilder.build()
}
