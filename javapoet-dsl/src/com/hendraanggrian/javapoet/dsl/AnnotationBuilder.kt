package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.hendraanggrian.javapoet.buildAnnotationSpec
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import kotlin.reflect.KClass

abstract class AnnotationBuilder {

    /** Add annotation to this spec builder. */
    abstract fun add(spec: AnnotationSpec)

    operator fun ClassName.invoke(builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        add(buildAnnotationSpec(this, builder))

    operator fun KClass<*>.invoke(builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        add(buildAnnotationSpec(java, builder))

    inline fun <reified T> add(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        T::class.invoke(builder)
}