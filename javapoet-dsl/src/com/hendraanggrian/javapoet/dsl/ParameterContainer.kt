package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.ParameterSpecBuilder
import com.hendraanggrian.javapoet.buildParameterSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

abstract class ParameterContainer {

    abstract operator fun plusAssign(spec: ParameterSpec)

    fun add(type: TypeName, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null) =
        plusAssign(buildParameterSpec(type, name, builder))

    fun add(type: KClass<*>, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null) =
        plusAssign(buildParameterSpec(type, name, builder))

    inline fun <reified T> add(name: String, noinline builder: (ParameterSpecBuilder.() -> Unit)? = null) =
        add(T::class, name, builder)

    operator fun invoke(configuration: ParameterContainerScope.() -> Unit) =
        configuration(ParameterContainerScope(this))
}

@JavapoetDslMarker
class ParameterContainerScope internal constructor(private val container: ParameterContainer) {

    operator fun String.invoke(type: TypeName, builder: (ParameterSpecBuilder.() -> Unit)? = null) {
        container += buildParameterSpec(type, this, builder)
    }

    operator fun String.invoke(type: KClass<*>, builder: (ParameterSpecBuilder.() -> Unit)? = null) {
        container += buildParameterSpec(type, this, builder)
    }

    inline operator fun <reified T> String.invoke(noinline builder: (ParameterSpecBuilder.() -> Unit)? = null) =
        invoke(T::class, builder)
}