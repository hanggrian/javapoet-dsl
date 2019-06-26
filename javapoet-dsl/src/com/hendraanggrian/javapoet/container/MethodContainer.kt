package com.hendraanggrian.javapoet.container

import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.hendraanggrian.javapoet.buildConstructorMethodSpec
import com.hendraanggrian.javapoet.buildMethodSpec
import com.squareup.javapoet.MethodSpec

abstract class MethodContainer {

    abstract operator fun plusAssign(spec: MethodSpec)

    fun add(name: String, builder: (MethodSpecBuilder.() -> Unit)? = null) =
        plusAssign(buildMethodSpec(name, builder))

    fun addConstructor(builder: (MethodSpecBuilder.() -> Unit)? = null) =
        plusAssign(buildConstructorMethodSpec(builder))
}