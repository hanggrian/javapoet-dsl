package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.hendraanggrian.javapoet.SpecBuilderDslMarker
import com.hendraanggrian.javapoet.buildConstructorMethodSpec
import com.hendraanggrian.javapoet.buildMethodSpec
import com.squareup.javapoet.MethodSpec

@SpecBuilderDslMarker
abstract class MethodBuilder {

    abstract fun add(spec: MethodSpec)

    operator fun String.invoke(builder: (MethodSpecBuilder.() -> Unit)? = null) =
        add(buildMethodSpec(this, builder))

    fun constructor(builder: (MethodSpecBuilder.() -> Unit)? = null) =
        add(buildConstructorMethodSpec(builder))
}