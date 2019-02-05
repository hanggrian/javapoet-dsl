package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.ParameterSpecBuilder
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type

interface ParameterSpecManager {

    fun parameter(type: TypeName, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null)

    fun parameter(type: Type, name: String, builder: (ParameterSpecBuilder.() -> Unit)? = null)
}