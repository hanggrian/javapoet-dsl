@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.hendraanggrian.javapoet.buildConstructorMethodSpec
import com.hendraanggrian.javapoet.buildMethodSpec
import com.squareup.javapoet.MethodSpec

abstract class MethodContainer internal constructor() : MethodContainerDelegate {

    inline operator fun plusAssign(spec: MethodSpec) = add(spec)

    inline operator fun plusAssign(name: String) = add(name)

    inline operator fun invoke(configuration: MethodContainerScope.() -> Unit) =
        configuration(MethodContainerScope(this))
}

@JavapoetDslMarker
class MethodContainerScope @PublishedApi internal constructor(private val container: MethodContainer) :
    MethodContainerDelegate {

    override fun add(spec: MethodSpec) = container.add(spec)

    inline operator fun String.invoke(noinline builder: MethodSpecBuilder.() -> Unit) = add(this, builder)
}

internal interface MethodContainerDelegate {

    fun add(spec: MethodSpec)

    fun add(name: String, builder: (MethodSpecBuilder.() -> Unit)? = null) = add(buildMethodSpec(name, builder))

    fun addConstructor(builder: (MethodSpecBuilder.() -> Unit)? = null) = add(buildConstructorMethodSpec(builder))
}