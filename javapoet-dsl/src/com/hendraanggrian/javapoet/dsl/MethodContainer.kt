package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.JavapoetDslMarker
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

    operator fun invoke(configuration: MethodContainerScope.() -> Unit) =
        configuration(MethodContainerScope(this))
}

@JavapoetDslMarker
class MethodContainerScope internal constructor(private val container: MethodContainer) {

    operator fun String.invoke(builder: (MethodSpecBuilder.() -> Unit)? = null) {
        container += buildMethodSpec(this, builder)
    }

    fun constructor(builder: (MethodSpecBuilder.() -> Unit)? = null) {
        container += buildConstructorMethodSpec(builder)
    }
}