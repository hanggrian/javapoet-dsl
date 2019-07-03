package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.hendraanggrian.javapoet.invoke
import com.squareup.javapoet.MethodSpec

/** A [MethodContainer] is responsible for managing a set of method instances. */
abstract class MethodContainer internal constructor() {

    abstract fun add(spec: MethodSpec): MethodSpec

    fun add(name: String): MethodSpec =
        add(MethodSpec.methodBuilder(name).build())

    inline fun add(name: String, builder: MethodSpecBuilder.() -> Unit): MethodSpec =
        add(MethodSpec.methodBuilder(name)(builder))

    fun addConstructor(): MethodSpec =
        add(MethodSpec.constructorBuilder().build())

    inline fun addConstructor(builder: MethodSpecBuilder.() -> Unit): MethodSpec =
        add(MethodSpec.constructorBuilder()(builder))

    operator fun plusAssign(spec: MethodSpec) {
        add(spec)
    }

    operator fun plusAssign(name: String) {
        add(name)
    }

    inline operator fun invoke(configuration: MethodContainerScope.() -> Unit) =
        MethodContainerScope(this).configuration()
}

/**
 * Receiver for the `methods` block providing an extended set of operators for the configuration.
 */
class MethodContainerScope @PublishedApi internal constructor(private val container: MethodContainer) :
    MethodContainer() {

    override fun add(spec: MethodSpec): MethodSpec = container.add(spec)

    inline operator fun String.invoke(builder: MethodSpecBuilder.() -> Unit): MethodSpec =
        add(this, builder)
}