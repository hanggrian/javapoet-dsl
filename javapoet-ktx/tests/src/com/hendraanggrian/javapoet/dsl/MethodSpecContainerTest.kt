package com.hendraanggrian.javapoet.dsl

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.buildConstructorMethod
import com.hendraanggrian.javapoet.buildMethod
import com.squareup.javapoet.MethodSpec
import kotlin.test.Test

class MethodSpecContainerTest {
    private val specs = mutableListOf<MethodSpec>()
    private val container = object : MethodSpecContainer() {
        override fun add(spec: MethodSpec) {
            specs += spec
        }
    }

    private inline fun container(configuration: MethodSpecContainerScope.() -> Unit) =
        MethodSpecContainerScope(container).configuration()

    @Test fun nativeSpec() {
        container.add(buildMethod("method"))
        container += buildConstructorMethod()
        assertThat(specs).containsExactly(
            buildMethod("method"),
            buildConstructorMethod()
        )
    }

    @Test fun string() {
        container.add("method1")
        container += "method2"
        container { "method3" { } }
        assertThat(specs).containsExactly(
            buildMethod("method1"),
            buildMethod("method2"),
            buildMethod("method3")
        )
    }

    @Test fun others() {
        container.addConstructor()
        assertThat(specs).containsExactly(
            buildConstructorMethod()
        )
    }
}