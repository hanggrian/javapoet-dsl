package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.AnnotationSpecBuilder
import com.hendraanggrian.javapoet.invoke
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import kotlin.reflect.KClass

/** An [AnnotationContainer] is responsible for managing a set of annotation instances. */
abstract class AnnotationContainer internal constructor() {

    abstract fun add(spec: AnnotationSpec): AnnotationSpec

    fun add(name: ClassName): AnnotationSpec =
        add(AnnotationSpec.builder(name).build())

    inline fun add(name: ClassName, builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(AnnotationSpec.builder(name)(builder))

    fun add(type: KClass<*>): AnnotationSpec =
        add(AnnotationSpec.builder(type.java).build())

    inline fun add(type: KClass<*>, builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(AnnotationSpec.builder(type.java)(builder))

    inline fun <reified T> add(): AnnotationSpec =
        add(T::class)

    inline fun <reified T> add(builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
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

    inline operator fun invoke(configuration: AnnotationContainerScope.() -> Unit) =
        AnnotationContainerScope(this).configuration()
}

/**
 * Receiver for the `annotations` block providing an extended set of operators for the configuration.
 */
class AnnotationContainerScope @PublishedApi internal constructor(private val container: AnnotationContainer) :
    AnnotationContainer() {

    override fun add(spec: AnnotationSpec): AnnotationSpec = container.add(spec)

    inline operator fun ClassName.invoke(builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builder)

    inline operator fun KClass<*>.invoke(builder: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
        add(this, builder)
}