package com.hendraanggrian.javapoet.collections

import com.example.Parameter1
import com.example.Parameter2
import com.example.Parameter3
import com.example.Parameter4
import com.example.Parameter5
import com.example.Parameter6
import com.example.Parameter7
import com.example.Parameter8
import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.asTypeName
import com.squareup.javapoet.ParameterSpec
import kotlin.test.Test

class ParameterSpecListTest {
    private val list = ParameterSpecList(mutableListOf())
    private fun list(configuration: ParameterSpecListScope.() -> Unit) = ParameterSpecListScope(list).configuration()

    @Test
    fun add() {
        list.add(Parameter1::class.asTypeName(), "parameter1")
        list.add(Parameter2::class.asTypeName(), "parameter2") { javadoc.append("text2") }
        list.add(Parameter3::class.java, "parameter3")
        list.add(Parameter4::class.java, "parameter4") { javadoc.append("text4") }
        list.add(Parameter5::class, "parameter5")
        list.add(Parameter6::class, "parameter6") { javadoc.append("text6") }
        list.add<Parameter7>("parameter7")
        list.add<Parameter8>("parameter8") { javadoc.append("text8") }
        assertThat(list).containsExactly(
            ParameterSpec.builder(Parameter1::class.java, "parameter1").build(),
            ParameterSpec.builder(Parameter2::class.java, "parameter2").addJavadoc("text2").build(),
            ParameterSpec.builder(Parameter3::class.java, "parameter3").build(),
            ParameterSpec.builder(Parameter4::class.java, "parameter4").addJavadoc("text4").build(),
            ParameterSpec.builder(Parameter5::class.java, "parameter5").build(),
            ParameterSpec.builder(Parameter6::class.java, "parameter6").addJavadoc("text6").build(),
            ParameterSpec.builder(Parameter7::class.java, "parameter7").build(),
            ParameterSpec.builder(Parameter8::class.java, "parameter8").addJavadoc("text8").build()
        )
    }

    @Test
    fun set() {
        list["parameter1"] = Parameter1::class.asTypeName()
        list["parameter2"] = Parameter2::class.java
        list["parameter3"] = Parameter3::class
        assertThat(list).containsExactly(
            ParameterSpec.builder(Parameter1::class.java, "parameter1").build(),
            ParameterSpec.builder(Parameter2::class.java, "parameter2").build(),
            ParameterSpec.builder(Parameter3::class.java, "parameter3").build(),
        )
    }

    @Test
    @Suppress("UNUSED_VARIABLE")
    fun adding() {
        val parameter1 by list.adding(Parameter1::class.asTypeName())
        val parameter2 by list.adding(Parameter2::class.asTypeName()) { javadoc.append("text2") }
        val parameter3 by list.adding(Parameter3::class.asTypeName())
        val parameter4 by list.adding(Parameter4::class.asTypeName()) { javadoc.append("text4") }
        val parameter5 by list.adding(Parameter5::class.asTypeName())
        val parameter6 by list.adding(Parameter6::class.asTypeName()) { javadoc.append("text6") }
        assertThat(list).containsExactly(
            ParameterSpec.builder(Parameter1::class.java, "parameter1").build(),
            ParameterSpec.builder(Parameter2::class.java, "parameter2").addJavadoc("text2").build(),
            ParameterSpec.builder(Parameter3::class.java, "parameter3").build(),
            ParameterSpec.builder(Parameter4::class.java, "parameter4").addJavadoc("text4").build(),
            ParameterSpec.builder(Parameter5::class.java, "parameter5").build(),
            ParameterSpec.builder(Parameter6::class.java, "parameter6").addJavadoc("text6").build(),
        )
    }

    @Test
    fun invoke() {
        list {
            "parameter1"(Parameter1::class.asTypeName()) { javadoc.append("text1") }
            "parameter2"(Parameter2::class.java) { javadoc.append("text2") }
            "parameter3"(Parameter3::class) { javadoc.append("text3") }
        }
        assertThat(list).containsExactly(
            ParameterSpec.builder(Parameter1::class.java, "parameter1").addJavadoc("text1").build(),
            ParameterSpec.builder(Parameter2::class.java, "parameter2").addJavadoc("text2").build(),
            ParameterSpec.builder(Parameter3::class.java, "parameter3").addJavadoc("text3").build()
        )
    }
}
