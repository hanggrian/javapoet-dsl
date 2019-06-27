package com.hendraanggrian.javapoet.scope

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.hendraanggrian.javapoet.buildConstructorMethodSpec
import com.hendraanggrian.javapoet.buildMethodSpec
import com.hendraanggrian.javapoet.container.MethodContainer

@JavapoetDslMarker
class MethodContainerScope internal constructor(private val container: MethodContainer) {

    operator fun String.invoke(builder: (MethodSpecBuilder.() -> Unit)? = null) {
        container += buildMethodSpec(this, builder)
    }

    fun constructor(builder: (MethodSpecBuilder.() -> Unit)? = null) {
        container += buildConstructorMethodSpec(builder)
    }
}