package com.hendraanggrian.javapoet.dsl

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.constructorMethodSpecOf
import com.hendraanggrian.javapoet.methodSpecOf
import com.squareup.javapoet.MethodSpec
import kotlin.test.Test

class MethodSpecContainerTest {
    private val methods = mutableListOf<MethodSpec>()
    private val container = object : MethodSpecContainer() {
        override fun addAll(specs: Iterable<MethodSpec>): Boolean = methods.addAll(specs)
        override fun add(spec: MethodSpec) {
            methods += spec
        }
    }

    private inline fun container(configuration: MethodSpecContainerScope.() -> Unit) =
        MethodSpecContainerScope(container).configuration()

    @Test fun nativeSpec() {
        container += methodSpecOf("method")
        container += listOf(constructorMethodSpecOf())
        assertThat(methods).containsExactly(
            methodSpecOf("method"),
            constructorMethodSpecOf()
        )
    }

    @Test fun string() {
        container.add("method1")
        container += "method2"
        container { "method3" { } }
        assertThat(methods).containsExactly(
            methodSpecOf("method1"),
            methodSpecOf("method2"),
            methodSpecOf("method3")
        )
    }

    @Test fun others() {
        container.addConstructor()
        assertThat(methods).containsExactly(
            constructorMethodSpecOf()
        )
    }
}