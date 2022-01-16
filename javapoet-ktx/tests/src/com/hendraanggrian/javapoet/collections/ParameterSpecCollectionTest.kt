package com.hendraanggrian.javapoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.asTypeName
import com.squareup.javapoet.ParameterSpec
import kotlin.test.Test

class ParameterSpecCollectionTest {

    private val list = ParameterSpecCollection(mutableListOf())
    private fun list(configuration: ParameterSpecCollectionScope.() -> Unit) =
        ParameterSpecCollectionScope(list).configuration()

    private class Parameter1
    private class Parameter2
    private class Parameter3
    private class Parameter4

    @Test
    fun add() {
        list.add(Parameter1::class.asTypeName(), "parameter1")
        list.add(Parameter2::class.java, "parameter2")
        list.add(Parameter3::class, "parameter3")
        list.add<Parameter4>("parameter4")
        assertThat(list).containsExactly(
            ParameterSpec.builder(Parameter1::class.java, "parameter1").build(),
            ParameterSpec.builder(Parameter2::class.java, "parameter2").build(),
            ParameterSpec.builder(Parameter3::class.java, "parameter3").build(),
            ParameterSpec.builder(Parameter4::class.java, "parameter4").build()
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
    fun invoke() {
        list {
            "parameter1"(Parameter1::class.asTypeName()) { }
            "parameter2"(Parameter2::class.java) { }
            "parameter3"(Parameter3::class) { }
            "parameter4"<Parameter4> { }
        }
        assertThat(list).containsExactly(
            ParameterSpec.builder(Parameter1::class.java, "parameter1").build(),
            ParameterSpec.builder(Parameter2::class.java, "parameter2").build(),
            ParameterSpec.builder(Parameter3::class.java, "parameter3").build(),
            ParameterSpec.builder(Parameter4::class.java, "parameter4").build()
        )
    }
}