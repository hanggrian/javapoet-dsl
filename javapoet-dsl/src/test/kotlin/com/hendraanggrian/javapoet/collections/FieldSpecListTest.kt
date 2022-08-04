package com.hendraanggrian.javapoet.collections

import com.example.Field1
import com.example.Field2
import com.example.Field3
import com.example.Field4
import com.example.Field5
import com.example.Field6
import com.example.Field7
import com.example.Field8
import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.asTypeName
import com.squareup.javapoet.FieldSpec
import kotlin.test.Test

class FieldSpecListTest {
    private val list = FieldSpecList(mutableListOf())
    private fun list(configuration: FieldSpecListScope.() -> Unit) = FieldSpecListScope(list).configuration()

    @Test
    fun add() {
        list.add(Field1::class.asTypeName(), "field1")
        list.add(Field2::class.asTypeName(), "field2") { initializer("value2") }
        list.add(Field3::class.java, "field3")
        list.add(Field4::class.java, "field4") { initializer("value4") }
        list.add(Field5::class, "field5")
        list.add(Field6::class, "field6") { initializer("value6") }
        list.add<Field7>("field7")
        list.add<Field8>("field8") { initializer("value8") }
        assertThat(list).containsExactly(
            FieldSpec.builder(Field1::class.java, "field1").build(),
            FieldSpec.builder(Field2::class.java, "field2").initializer("value2").build(),
            FieldSpec.builder(Field3::class.java, "field3").build(),
            FieldSpec.builder(Field4::class.java, "field4").initializer("value4").build(),
            FieldSpec.builder(Field5::class.java, "field5").build(),
            FieldSpec.builder(Field6::class.java, "field6").initializer("value6").build(),
            FieldSpec.builder(Field7::class.java, "field7").build(),
            FieldSpec.builder(Field8::class.java, "field8").initializer("value8").build()
        )
    }

    @Test
    fun set() {
        list["field1"] = Field1::class.asTypeName()
        list["field2"] = Field2::class.java
        list["field3"] = Field3::class
        assertThat(list).containsExactly(
            FieldSpec.builder(Field1::class.java, "field1").build(),
            FieldSpec.builder(Field2::class.java, "field2").build(),
            FieldSpec.builder(Field3::class.java, "field3").build()
        )
    }

    @Test
    @Suppress("UNUSED_VARIABLE")
    fun adding() {
        val field1 by list.adding(Field1::class.asTypeName())
        val field2 by list.adding(Field2::class.asTypeName()) { initializer("value2") }
        val field3 by list.adding(Field3::class.java)
        val field4 by list.adding(Field4::class.java) { initializer("value4") }
        val field5 by list.adding(Field5::class)
        val field6 by list.adding(Field6::class) { initializer("value6") }
        assertThat(list).containsExactly(
            FieldSpec.builder(Field1::class.java, "field1").build(),
            FieldSpec.builder(Field2::class.java, "field2").initializer("value2").build(),
            FieldSpec.builder(Field3::class.java, "field3").build(),
            FieldSpec.builder(Field4::class.java, "field4").initializer("value4").build(),
            FieldSpec.builder(Field5::class.java, "field5").build(),
            FieldSpec.builder(Field6::class.java, "field6").initializer("value6").build()
        )
    }

    @Test
    fun invoke() {
        list {
            "field1"(Field1::class.asTypeName()) { initializer("value1") }
            "field2"(Field2::class.java) { initializer("value2") }
            "field3"(Field3::class) { initializer("value3") }
        }
        assertThat(list).containsExactly(
            FieldSpec.builder(Field1::class.java, "field1").initializer("value1").build(),
            FieldSpec.builder(Field2::class.java, "field2").initializer("value2").build(),
            FieldSpec.builder(Field3::class.java, "field3").initializer("value3").build()
        )
    }
}
