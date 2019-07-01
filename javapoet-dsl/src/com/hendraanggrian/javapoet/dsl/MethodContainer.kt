@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.squareup.javapoet.MethodSpec

@JavapoetDslMarker
class MethodContainerScope @PublishedApi internal constructor(container: MethodContainer) :
    MethodContainer by container {

    /** Convenient method to add method with receiver. */
    inline operator fun String.invoke(noinline builder: MethodSpecBuilder.() -> Unit): MethodSpec = add(this, builder)
}

interface MethodContainer {

    /** Add spec to this container, returning the spec added. */
    fun add(spec: MethodSpec): MethodSpec

    fun add(name: String, builder: (MethodSpecBuilder.() -> Unit)? = null): MethodSpec =
        add(MethodSpecBuilder.of(name, builder))

    fun addConstructor(builder: (MethodSpecBuilder.() -> Unit)? = null): MethodSpec =
        add(MethodSpecBuilder.ofConstructor(builder))
}

/** Configures the methods of this container. */
inline operator fun MethodContainer.invoke(configuration: MethodContainerScope.() -> Unit) =
    MethodContainerScope(this).configuration()

inline operator fun MethodContainer.plusAssign(spec: MethodSpec) {
    add(spec)
}

inline operator fun MethodContainer.plusAssign(name: String) {
    add(name)
}