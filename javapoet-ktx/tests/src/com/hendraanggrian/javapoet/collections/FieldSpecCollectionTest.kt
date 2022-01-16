package com.hendraanggrian.javapoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.asTypeName
import com.squareup.javapoet.FieldSpec
import kotlin.test.Test

class FieldSpecCollectionTest {

    private val list = FieldSpecCollection(mutableListOf())
    private fun list(configuration: FieldSpecCollectionScope.() -> Unit) =
        FieldSpecCollectionScope(list).configuration()

    private class Field1
    private class Field2
    private class Field3
    private class Field4

    @Test
    fun add() {
        list.add(Field1::class.asTypeName(), "field1")
        list.add(Field2::class.java, "field2")
        list.add(Field3::class, "field3")
        list.add<Field4>("field4")
        assertThat(list).containsExactly(
            FieldSpec.builder(Field1::class.java, "field1").build(),
            FieldSpec.builder(Field2::class.java, "field2").build(),
            FieldSpec.builder(Field3::class.java, "field3").build(),
            FieldSpec.builder(Field4::class.java, "field4").build()
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
    fun invoke() {
        list {
            "field1"(Field1::class.asTypeName()) { }
            "field2"(Field2::class.java) { }
            "field3"(Field3::class) { }
            "field4"<Field4> { }
        }
        assertThat(list).containsExactly(
            FieldSpec.builder(Field1::class.java, "field1").build(),
            FieldSpec.builder(Field2::class.java, "field2").build(),
            FieldSpec.builder(Field3::class.java, "field3").build(),
            FieldSpec.builder(Field4::class.java, "field4").build()
        )
    }
}