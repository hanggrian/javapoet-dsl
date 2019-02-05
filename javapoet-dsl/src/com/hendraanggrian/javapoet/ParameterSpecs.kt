package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.AnnotationManager
import com.hendraanggrian.javapoet.internal.ModifierManager
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterSpec
import javax.lang.model.element.Modifier

interface ParameterSpecBuilder : AnnotationManager, ModifierManager {

    val nativeBuilder: ParameterSpec.Builder

    override fun annotation(type: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(createAnnotation(type, builder))
    }

    override fun annotation(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(createAnnotation(type, builder))
    }

    override fun modifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }
}

internal class ParameterSpecBuilderImpl(override val nativeBuilder: ParameterSpec.Builder) : ParameterSpecBuilder