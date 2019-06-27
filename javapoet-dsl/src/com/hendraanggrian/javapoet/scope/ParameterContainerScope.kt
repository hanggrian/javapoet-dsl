package com.hendraanggrian.javapoet.scope

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.ParameterSpecBuilder
import com.hendraanggrian.javapoet.buildParameterSpec
import com.hendraanggrian.javapoet.container.ParameterContainer
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

@JavapoetDslMarker
class ParameterContainerScope internal constructor(private val container: ParameterContainer) {

    operator fun String.invoke(type: TypeName, builder: (ParameterSpecBuilder.() -> Unit)? = null) {
        container += buildParameterSpec(type, this, builder)
    }

    operator fun String.invoke(type: KClass<*>, builder: (ParameterSpecBuilder.() -> Unit)? = null) {
        container += buildParameterSpec(type.java, this, builder)
    }

    inline operator fun <reified T> String.invoke(noinline builder: (ParameterSpecBuilder.() -> Unit)? = null) =
        invoke(T::class, builder)
}