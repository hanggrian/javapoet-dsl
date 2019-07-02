@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.squareup.javapoet.MethodSpec

/** An [MethodContainer] is responsible for managing a set of method instances. */
abstract class MethodContainer internal constructor() {

    abstract fun add(spec: MethodSpec): MethodSpec

    fun add(name: String, builder: (MethodSpecBuilder.() -> Unit)? = null): MethodSpec =
        add(MethodSpecBuilder.of(name, builder))

    fun addConstructor(builder: (MethodSpecBuilder.() -> Unit)? = null): MethodSpec =
        add(MethodSpecBuilder.ofConstructor(builder))

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
class MethodContainerScope @PublishedApi internal constructor(private val container: MethodContainer) :
    MethodContainer() {

    override fun add(spec: MethodSpec): MethodSpec = container.add(spec)

    inline operator fun String.invoke(noinline builder: MethodSpecBuilder.() -> Unit): MethodSpec = add(this, builder)
}