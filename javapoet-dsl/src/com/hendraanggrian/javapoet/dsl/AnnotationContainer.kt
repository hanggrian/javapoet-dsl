@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.buildAnnotationSpec
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import kotlin.reflect.KClass

abstract class AnnotationContainer : AnnotationContainerDelegate {

    operator fun plusAssign(spec: AnnotationSpec) = add(spec)

    operator fun plusAssign(name: ClassName) = add(name)

    operator fun plusAssign(type: KClass<*>) = add(type)

    inline fun <reified T> add(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        add(T::class, builder)

    inline operator fun invoke(configuration: AnnotationContainerScope.() -> Unit) =
        configuration(AnnotationContainerScope(this))
}

@JavapoetDslMarker
class AnnotationContainerScope @PublishedApi internal constructor(private val container: AnnotationContainer) :
    AnnotationContainerDelegate {

    override fun add(spec: AnnotationSpec) = container.add(spec)

    inline operator fun ClassName.invoke(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        add(this, builder)

    inline operator fun KClass<*>.invoke(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        add(this, builder)

    inline fun <reified T> add(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        T::class.invoke(builder)
}

internal interface AnnotationContainerDelegate {

    fun add(spec: AnnotationSpec)

    /** Add annotation to this spec builder. */
    fun add(name: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        add(buildAnnotationSpec(name, builder))

    /** Add annotation to this spec builder. */
    fun add(type: KClass<*>, builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        add(buildAnnotationSpec(type, builder))
}