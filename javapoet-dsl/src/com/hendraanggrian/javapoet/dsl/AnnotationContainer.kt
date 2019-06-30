package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import kotlin.reflect.KClass

abstract class AnnotationContainer : AnnotationContainerDelegate {

    inline operator fun invoke(configuration: AnnotationContainerScope.() -> Unit) =
        configuration(AnnotationContainerScope(this))
}

@JavapoetDslMarker
@Suppress("NOTHING_TO_INLINE")
class AnnotationContainerScope @PublishedApi internal constructor(private val container: AnnotationContainer) :
    AnnotationContainerDelegate by container {

    inline operator fun ClassName.invoke(noinline builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builder)

    inline operator fun KClass<*>.invoke(noinline builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builder)
}

interface AnnotationContainerDelegate {

    fun add(spec: AnnotationSpec): AnnotationSpec

    fun add(type: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
        add(AnnotationSpecBuilder.of(type, builder))

    fun add(type: KClass<*>, builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
        add(AnnotationSpecBuilder.of(type, builder))

    operator fun plusAssign(spec: AnnotationSpec) {
        add(spec)
    }

    operator fun plusAssign(type: ClassName) {
        add(type)
    }

    operator fun plusAssign(type: KClass<*>) {
        add(type)
    }
}

inline fun <reified T> AnnotationContainerDelegate.add(
    noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null
): AnnotationSpec = add(T::class, builder)