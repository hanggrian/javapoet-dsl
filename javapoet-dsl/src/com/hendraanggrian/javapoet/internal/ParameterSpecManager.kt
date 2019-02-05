package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.ParameterSpecBuilder
import com.hendraanggrian.javapoet.ParameterSpecBuilderImpl
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type

interface ParameterSpecManager {

    fun parameter(type: TypeName, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null)

    fun parameter(type: Type, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null)

    fun createParameter(type: TypeName, name: String, builder: (ParameterSpecBuilder.() -> Unit)?): ParameterSpec =
        ParameterSpecBuilderImpl(ParameterSpec.builder(type, name))
            .also { builder?.invoke(it) }
            .nativeBuilder
            .build()

    fun createParameter(type: Type, name: String, builder: (ParameterSpecBuilder.() -> Unit)?): ParameterSpec =
        ParameterSpecBuilderImpl(ParameterSpec.builder(type, name))
            .also { builder?.invoke(it) }
            .nativeBuilder
            .build()
}