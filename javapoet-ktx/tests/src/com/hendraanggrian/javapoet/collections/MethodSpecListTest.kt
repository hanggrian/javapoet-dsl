package com.hendraanggrian.javapoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.constructorMethodSpecOf
import com.hendraanggrian.javapoet.methodSpecOf
import kotlin.test.Test

class MethodSpecListTest {
    private val list = MethodSpecList(mutableListOf())

    private inline fun container(configuration: MethodSpecListScope.() -> Unit) =
        MethodSpecListScope(list).configuration()

    @Test fun nativeSpec() {
        list += methodSpecOf("method")
        list += listOf(constructorMethodSpecOf())
        assertThat(list).containsExactly(
            methodSpecOf("method"),
            constructorMethodSpecOf()
        )
    }

    @Test fun string() {
        list.add("method1")
        list += "method2"
        container { "method3" { } }
        assertThat(list).containsExactly(
            methodSpecOf("method1"),
            methodSpecOf("method2"),
            methodSpecOf("method3")
        )
    }

    @Test fun others() {
        list.addConstructor()
        assertThat(list).containsExactly(
            constructorMethodSpecOf()
        )
    }
}