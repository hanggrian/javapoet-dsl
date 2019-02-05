package com.hendraanggrian.javapoet.internal

import com.squareup.javapoet.TypeVariableName

interface TypeVariableManager {

    fun typeVariable(typeVariable: TypeVariableName)

    fun typeVariable(typeVariables: Iterable<TypeVariableName>)
}