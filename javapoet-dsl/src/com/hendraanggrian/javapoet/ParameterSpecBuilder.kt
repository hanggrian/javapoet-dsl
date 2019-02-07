package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.AnnotationManager
import com.hendraanggrian.javapoet.internal.ModifierManager
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier

/** Returns a parameter with custom initialization block. */
fun buildParameterSpec(
    type: TypeName,
    name: String,
    builder: (ParameterSpecBuilder.() -> Unit)? = null
): ParameterSpec = ParameterSpecBuilderImpl(ParameterSpec.builder(type, name))
    .also { builder?.invoke(it) }
    .build()

/** Returns a parameter with custom initialization block. */
fun buildParameterSpec(
    type: Type,
    name: String,
    builder: (ParameterSpecBuilder.() -> Unit)? = null
): ParameterSpec = ParameterSpecBuilderImpl(ParameterSpec.builder(type, name))
    .also { builder?.invoke(it) }
    .build()

interface ParameterSpecBuilder : AnnotationManager, ModifierManager {

    val nativeBuilder: ParameterSpec.Builder

    override fun annotation(type: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(buildAnnotationSpec(type, builder))
    }

    override fun annotation(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(buildAnnotationSpec(type, builder))
    }

    override fun modifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    fun build(): ParameterSpec = nativeBuilder.build()
}

internal class ParameterSpecBuilderImpl(override val nativeBuilder: ParameterSpec.Builder) : ParameterSpecBuilder