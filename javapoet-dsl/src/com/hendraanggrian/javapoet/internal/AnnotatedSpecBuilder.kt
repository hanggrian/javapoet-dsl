package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.squareup.javapoet.ClassName

/** Don't forget to add inline reified function. */
internal interface AnnotatedSpecBuilder {

    /** Add annotation to this spec builder */
    fun annotation(type: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)? = null)

    /** Add annotation to this spec builder */
    fun annotation(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)? = null)
}