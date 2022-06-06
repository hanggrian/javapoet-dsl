package com.hendraanggrian.javapoet.collections

import com.google.common.truth.Truth.assertThat
import com.squareup.javapoet.MethodSpec
import kotlin.test.Test

class MethodSpecListTest {
    private val list = MethodSpecList(mutableListOf())
    private fun list(configuration: MethodSpecListScope.() -> Unit) = MethodSpecListScope(list).configuration()

    @Test
    fun add() {
        list.add("method1")
        list.add("method2") { javadoc.append("text2") }
        list.addConstructor()
        list.addConstructor { javadoc.append("text4") }
        assertThat(list).containsExactly(
            MethodSpec.methodBuilder("method1").build(),
            MethodSpec.methodBuilder("method2").addJavadoc("text2").build(),
            MethodSpec.constructorBuilder().build(),
            MethodSpec.constructorBuilder().addJavadoc("text4").build()
        )
    }

    @Test
    fun plusAssign() {
        list += "method1"
        assertThat(list).containsExactly(
            MethodSpec.methodBuilder("method1").build()
        )
    }

    @Test
    @Suppress("UNUSED_VARIABLE")
    fun adding() {
        val method1 by list.adding
        val method2 by list.adding { javadoc.append("text2") }
        assertThat(list).containsExactly(
            MethodSpec.methodBuilder("method1").build(),
            MethodSpec.methodBuilder("method2").addJavadoc("text2").build(),
        )
    }

    @Test
    fun invoke() {
        list {
            "method1" { javadoc.append("text1") }
        }
        assertThat(list).containsExactly(
            MethodSpec.methodBuilder("method1").addJavadoc("text1").build()
        )
    }
}