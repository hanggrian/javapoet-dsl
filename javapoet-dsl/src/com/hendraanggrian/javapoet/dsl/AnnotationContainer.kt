@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.buildAnnotationSpec
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import kotlin.reflect.KClass

abstract class AnnotationContainer : AnnotationContainerDelegate {

    inline operator fun plusAssign(spec: AnnotationSpec) = add(spec)

    inline operator fun plusAssign(type: ClassName) = add(type)

    inline operator fun plusAssign(type: KClass<*>) = add(type)

    inline fun <reified T> add(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null) = add(T::class, builder)

    inline operator fun invoke(configuration: AnnotationContainerScope.() -> Unit) =
        configuration(AnnotationContainerScope(this))
}

@JavapoetDslMarker
class AnnotationContainerScope @PublishedApi internal constructor(private val container: AnnotationContainer) :
    AnnotationContainerDelegate {

    override fun add(spec: AnnotationSpec) = container.add(spec)

    inline operator fun ClassName.invoke(noinline builder: AnnotationSpecBuilder.() -> Unit) = add(this, builder)

    inline operator fun KClass<*>.invoke(noinline builder: AnnotationSpecBuilder.() -> Unit) = add(this, builder)

    inline fun <reified T> add(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null) = add(T::class, builder)
}

internal interface AnnotationContainerDelegate {

    fun add(spec: AnnotationSpec)

    fun add(type: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        add(buildAnnotationSpec(type, builder))

    fun add(type: KClass<*>, builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        add(buildAnnotationSpec(type, builder))
}