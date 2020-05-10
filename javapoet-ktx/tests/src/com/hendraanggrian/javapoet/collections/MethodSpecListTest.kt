package com.hendraanggrian.javapoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.constructorMethodSpecOf
import com.hendraanggrian.javapoet.methodSpecOf
import kotlin.test.Test

class MethodSpecListTest {
    private val container = MethodSpecList(mutableListOf())

    private inline fun container(configuration: MethodSpecListScope.() -> Unit) =
        MethodSpecListScope(container).configuration()

    @Test fun nativeSpec() {
        container += methodSpecOf("method")
        container += listOf(constructorMethodSpecOf())
        assertThat(container).containsExactly(
            methodSpecOf("method"),
            constructorMethodSpecOf()
        )
    }

    @Test fun string() {
        container.add("method1")
        container += "method2"
        container { "method3" { } }
        assertThat(container).containsExactly(
            methodSpecOf("method1"),
            methodSpecOf("method2"),
            methodSpecOf("method3")
        )
    }

    @Test fun others() {
        container.addConstructor()
        assertThat(container).containsExactly(
            constructorMethodSpecOf()
        )
    }
}