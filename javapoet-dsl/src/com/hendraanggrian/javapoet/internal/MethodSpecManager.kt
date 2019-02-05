package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.hendraanggrian.javapoet.MethodSpecBuilderImpl
import com.squareup.javapoet.MethodSpec

interface MethodSpecManager {

    fun method(name: String, builder: (MethodSpecBuilder.() -> Unit)? = null)

    fun constructor(builder: (MethodSpecBuilder.() -> Unit)? = null)

    fun createMethod(name: String, builder: (MethodSpecBuilder.() -> Unit)?): MethodSpec =
        MethodSpecBuilderImpl(MethodSpec.methodBuilder(name))
            .also { builder?.invoke(it) }
            .nativeBuilder
            .build()

    fun createConstructor(builder: (MethodSpecBuilder.() -> Unit)?): MethodSpec =
        MethodSpecBuilderImpl(MethodSpec.constructorBuilder())
            .also { builder?.invoke(it) }
            .nativeBuilder
            .build()
}