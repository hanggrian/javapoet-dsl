package com.hendraanggrian.javapoet.internal

import com.squareup.javapoet.TypeVariableName

internal interface TypeVariable {

    fun typeVariable(typeVariable: TypeVariableName)

    fun typeVariable(typeVariables: Iterable<TypeVariableName>)
}