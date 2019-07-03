@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.hendraanggrian.javapoet.invoke
import com.squareup.javapoet.MethodSpec

internal interface MethodCollection {

    fun add(spec: MethodSpec): MethodSpec

    fun add(name: String): MethodSpec =
        add(MethodSpec.methodBuilder(name).build())

    fun add(name: String, builder: MethodSpecBuilder.() -> Unit): MethodSpec =
        add(MethodSpec.methodBuilder(name)(builder))

    fun addConstructor(): MethodSpec =
        add(MethodSpec.constructorBuilder().build())

    fun addConstructor(builder: MethodSpecBuilder.() -> Unit): MethodSpec =
        add(MethodSpec.constructorBuilder()(builder))
}

/** A [MethodContainer] is responsible for managing a set of method instances. */
abstract class MethodContainer internal constructor() : MethodCollection {

    inline operator fun plusAssign(spec: MethodSpec) {
        add(spec)
    }

    inline operator fun plusAssign(name: String) {
        add(name)
    }

    inline operator fun invoke(configuration: MethodContainerScope.() -> Unit) =
        MethodContainerScope(this).configuration()
}

/**
 * Receiver for the `methods` block providing an extended set of operators for the configuration.
 */
class MethodContainerScope @PublishedApi internal constructor(collection: MethodCollection) :
    MethodContainer(), MethodCollection by collection {

    inline operator fun String.invoke(noinline builder: MethodSpecBuilder.() -> Unit): MethodSpec = add(this, builder)
}