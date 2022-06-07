package com.squareup.javapoet

internal fun getParameterizedTypeName(rawType: ClassName, typeArguments: List<TypeName>): ParameterizedTypeName =
    ParameterizedTypeName(null, rawType, typeArguments)
