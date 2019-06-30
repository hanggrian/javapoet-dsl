package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import kotlin.reflect.KClass

abstract class AnnotationContainer internal constructor() : AnnotationContainerDelegate() {

    operator fun invoke(configuration: AnnotationContainerScope.() -> Unit) =
        AnnotationContainerScope(this).configuration()
}

@JavapoetDslMarker
@Suppress("NOTHING_TO_INLINE")
class AnnotationContainerScope @PublishedApi internal constructor(private val container: AnnotationContainer) :
    AnnotationContainerDelegate() {

    override fun add(spec: AnnotationSpec): AnnotationSpec = container.add(spec)

    operator fun ClassName.invoke(builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builder)

    operator fun KClass<*>.invoke(builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builder)
}

sealed class AnnotationContainerDelegate {

    abstract fun add(spec: AnnotationSpec): AnnotationSpec

    fun add(name: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
        add(AnnotationSpecBuilder.of(name, builder))

    fun add(type: KClass<*>, builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
        add(AnnotationSpecBuilder.of(type, builder))

    inline fun <reified T> add(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null): AnnotationSpec =
        add(T::class, builder)

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