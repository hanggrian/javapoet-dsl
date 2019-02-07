package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.squareup.javapoet.ClassName

/** Don't forget to add inline reified function. */
internal interface Annotable {

    fun annotation(type: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)? = null)

    fun annotation(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)? = null)
}