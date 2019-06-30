package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.squareup.javapoet.MethodSpec

abstract class MethodContainer internal constructor() : MethodContainerDelegate() {

    operator fun invoke(configuration: MethodContainerScope.() -> Unit) =
        MethodContainerScope(this).configuration()
}

@JavapoetDslMarker
@Suppress("NOTHING_TO_INLINE")
class MethodContainerScope @PublishedApi internal constructor(private val container: MethodContainer) :
    MethodContainerDelegate() {

    override fun add(spec: MethodSpec): MethodSpec = container.add(spec)

    operator fun String.invoke(builder: MethodSpecBuilder.() -> Unit): MethodSpec = add(this, builder)
}

sealed class MethodContainerDelegate {

    abstract fun add(spec: MethodSpec): MethodSpec

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