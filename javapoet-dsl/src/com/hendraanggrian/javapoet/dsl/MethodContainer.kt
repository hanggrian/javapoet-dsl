@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.hendraanggrian.javapoet.buildConstructorMethodSpec
import com.hendraanggrian.javapoet.buildMethodSpec
import com.squareup.javapoet.MethodSpec

internal interface MethodCollection {

    fun add(spec: MethodSpec): MethodSpec

    fun add(name: String, builder: (MethodSpecBuilder.() -> Unit)? = null): MethodSpec =
        add(buildMethodSpec(name, builder))

    fun addConstructor(builder: (MethodSpecBuilder.() -> Unit)? = null): MethodSpec =
        add(buildConstructorMethodSpec(builder))
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