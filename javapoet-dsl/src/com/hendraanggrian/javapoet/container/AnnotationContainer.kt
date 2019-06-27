package com.hendraanggrian.javapoet.container

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.hendraanggrian.javapoet.buildAnnotationSpec
import com.hendraanggrian.javapoet.scope.AnnotationContainerScope
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import kotlin.reflect.KClass

abstract class AnnotationContainer {

    abstract operator fun plusAssign(spec: AnnotationSpec)

    /** Add annotation to this spec builder. */
    fun add(name: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        plusAssign(buildAnnotationSpec(name, builder))

    /** Add annotation to this spec builder. */
    fun add(type: KClass<*>, builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        plusAssign(buildAnnotationSpec(type.java, builder))

    inline fun <reified T> add(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        add(T::class, builder)

    operator fun invoke(configuration: AnnotationContainerScope.() -> Unit) =
        configuration(AnnotationContainerScope(this))
}