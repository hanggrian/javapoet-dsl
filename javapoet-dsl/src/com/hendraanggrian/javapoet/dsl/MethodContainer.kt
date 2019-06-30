@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.squareup.javapoet.MethodSpec

abstract class MethodContainer internal constructor() : MethodContainerDelegate() {

    /** Open DSL to configure this container. */
    inline operator fun invoke(configuration: MethodContainerScope.() -> Unit) =
        MethodContainerScope(this).configuration()
}

@JavapoetDslMarker
class MethodContainerScope @PublishedApi internal constructor(private val container: MethodContainer) :
    MethodContainerDelegate() {

    override fun add(spec: MethodSpec): MethodSpec = container.add(spec)

    /** Convenient method to add method with receiver. */
    inline operator fun String.invoke(noinline builder: MethodSpecBuilder.() -> Unit): MethodSpec = add(this, builder)
}

sealed class MethodContainerDelegate {

    /** Add spec to this container, returning the spec added. */
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
}