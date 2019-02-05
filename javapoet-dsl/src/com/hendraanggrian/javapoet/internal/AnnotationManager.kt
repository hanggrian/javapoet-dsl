package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.hendraanggrian.javapoet.AnnotationSpecBuilderImpl
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName

interface AnnotationManager {

    fun annotation(type: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)? = null)

    fun annotation(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)? = null)

    fun createAnnotation(type: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)?): AnnotationSpec =
        AnnotationSpecBuilderImpl(AnnotationSpec.builder(type))
            .also { builder?.invoke(it) }
            .nativeBuilder
            .build()

    fun createAnnotation(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)?): AnnotationSpec =
        AnnotationSpecBuilderImpl(AnnotationSpec.builder(type))
            .also { builder?.invoke(it) }
            .nativeBuilder
            .build()
}