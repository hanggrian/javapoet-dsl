package com.hendraanggrian.javapoet.collections

import com.google.common.truth.Truth.assertThat
import com.squareup.javapoet.MethodSpec
import kotlin.test.Test

class MethodSpecListTest {

    private val list = MethodSpecList(mutableListOf())
    private fun list(configuration: MethodSpecListScope.() -> Unit) = MethodSpecListScope(list).configuration()

    @Test
    fun add() {
        list.add("method")
        list.addConstructor()
        assertThat(list).containsExactly(
            MethodSpec.methodBuilder("method").build(),
            MethodSpec.constructorBuilder().build()
        )
    }

    @Test
    fun plusAssign() {
        list += "method"
        assertThat(list).containsExactly(
            MethodSpec.methodBuilder("method").build()
        )
    }

    @Test
    fun invoke() {
        list {
            "method" { }
        }
        assertThat(list).containsExactly(
            MethodSpec.methodBuilder("method").build()
        )
    }
}