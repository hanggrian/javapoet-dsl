@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.ParameterSpecBuilder
import com.hendraanggrian.javapoet.buildParameterSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

abstract class ParameterContainer : ParameterContainerDelegate {

    operator fun plusAssign(spec: ParameterSpec) = add(spec)

    inline fun <reified T> add(name: String, noinline builder: (ParameterSpecBuilder.() -> Unit)? = null) =
        add(T::class, name, builder)

    inline operator fun invoke(configuration: ParameterContainerScope.() -> Unit) =
        configuration(ParameterContainerScope(this))
}

@JavapoetDslMarker
class ParameterContainerScope @PublishedApi internal constructor(private val container: ParameterContainer) :
    ParameterContainerDelegate {

    override fun add(spec: ParameterSpec) = container.add(spec)

    inline operator fun String.invoke(type: TypeName, noinline builder: (ParameterSpecBuilder.() -> Unit)? = null) =
        add(type, this, builder)

    inline operator fun String.invoke(type: KClass<*>, noinline builder: (ParameterSpecBuilder.() -> Unit)? = null) =
        add(type, this, builder)

    inline operator fun <reified T> String.invoke(noinline builder: (ParameterSpecBuilder.() -> Unit)? = null) =
        invoke(T::class, builder)
}

internal interface ParameterContainerDelegate {

    fun add(spec: ParameterSpec)

    fun add(type: TypeName, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null) =
        add(buildParameterSpec(type, name, builder))

    fun add(type: KClass<*>, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null) =
        add(buildParameterSpec(type, name, builder))
}