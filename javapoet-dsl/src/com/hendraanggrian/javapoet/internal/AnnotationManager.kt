package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.squareup.javapoet.ClassName

interface AnnotationManager {

    fun annotation(type: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)? = null)

    fun annotation(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)? = null)
}