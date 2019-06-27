package com.hendraanggrian.javapoet.container

import com.hendraanggrian.javapoet.ParameterSpecBuilder
import com.hendraanggrian.javapoet.buildParameterSpec
import com.hendraanggrian.javapoet.scope.ParameterContainerScope
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

abstract class ParameterContainer {

    abstract operator fun plusAssign(spec: ParameterSpec)

    fun add(type: TypeName, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null) =
        plusAssign(buildParameterSpec(type, name, builder))

    fun add(type: KClass<*>, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null) =
        plusAssign(buildParameterSpec(type.java, name, builder))

    inline fun <reified T> add(name: String, noinline builder: (ParameterSpecBuilder.() -> Unit)? = null) =
        add(T::class, name, builder)

    operator fun invoke(configuration: ParameterContainerScope.() -> Unit) =
        configuration(ParameterContainerScope(this))
}