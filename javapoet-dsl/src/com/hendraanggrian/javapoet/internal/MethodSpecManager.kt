package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.hendraanggrian.javapoet.MethodSpecBuilderImpl
import com.squareup.javapoet.MethodSpec

interface MethodSpecManager {

    fun method(name: String, builder: MethodSpecBuilder.() -> Unit)

    fun constructor(builder: MethodSpecBuilder.() -> Unit)

    fun createMethod(name: String, builder: MethodSpecBuilder.() -> Unit): MethodSpec =
        MethodSpecBuilderImpl(MethodSpec.methodBuilder(name))
            .nativeBuilder
            .build()

    fun createConstructor(builder: MethodSpecBuilder.() -> Unit): MethodSpec =
        MethodSpecBuilderImpl(MethodSpec.constructorBuilder())
            .nativeBuilder
            .build()
}