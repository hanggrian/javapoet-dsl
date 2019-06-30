package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.squareup.javapoet.MethodSpec

abstract class MethodContainer internal constructor() : MethodContainerDelegate {

    inline operator fun invoke(configuration: MethodContainerScope.() -> Unit) =
        configuration(MethodContainerScope(this))
}

@JavapoetDslMarker
@Suppress("NOTHING_TO_INLINE")
class MethodContainerScope @PublishedApi internal constructor(private val container: MethodContainer) :
    MethodContainerDelegate by container {

    inline operator fun String.invoke(noinline builder: MethodSpecBuilder.() -> Unit): MethodSpec = add(this, builder)
}

interface MethodContainerDelegate {

    fun add(spec: MethodSpec): MethodSpec

    fun add(name: String, builder: (MethodSpecBuilder.() -> Unit)? = null): MethodSpec =
        add(MethodSpecBuilder.of(name, builder))

    fun addConstructor(builder: (MethodSpecBuilder.() -> Unit)? = null): MethodSpec =
        add(MethodSpecBuilder.ofConstructor(builder))

    operator fun plusAssign(spec: MethodSpec) {
        add(spec)
    }

    operator fun plusAssign(name: String) {
        add(name)
    }
}