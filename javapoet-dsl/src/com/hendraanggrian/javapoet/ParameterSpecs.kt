package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.AnnotationManager
import com.hendraanggrian.javapoet.internal.ModifierManager
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterSpec
import javax.lang.model.element.Modifier

interface ParameterSpecBuilder : AnnotationManager, ModifierManager {

    val nativeBuilder: ParameterSpec.Builder

    override fun annotations(annotationSpecs: Iterable<AnnotationSpec>) {
        nativeBuilder.addAnnotations(annotationSpecs)
    }

    override fun annotation(annotationSpec: AnnotationSpec) {
        nativeBuilder.addAnnotation(annotationSpec)
    }

    override fun annotation(annotation: ClassName) {
        nativeBuilder.addAnnotation(annotation)
    }

    override fun annotation(annotation: Class<*>) {
        nativeBuilder.addAnnotation(annotation)
    }

    override fun modifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }
}

class ParameterSpecBuilderImpl(override val nativeBuilder: ParameterSpec.Builder) : ParameterSpecBuilder