package com.hendraanggrian.javapoet.internal

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName

interface AnnotationManager {

    fun annotations(annotationSpecs: Iterable<AnnotationSpec>)

    fun annotation(annotationSpec: AnnotationSpec)

    fun annotation(annotation: ClassName)

    fun annotation(annotation: Class<*>)
}