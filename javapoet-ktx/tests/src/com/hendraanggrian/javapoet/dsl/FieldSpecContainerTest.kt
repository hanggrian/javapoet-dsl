package com.hendraanggrian.javapoet.dsl

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.classOf
import com.hendraanggrian.javapoet.fieldSpecOf
import com.squareup.javapoet.FieldSpec
import kotlin.test.Test

class FieldSpecContainerTest {
    private val fields = mutableListOf<FieldSpec>()
    private val container = object : FieldSpecContainer() {
        override fun addAll(specs: Iterable<FieldSpec>): Boolean = fields.addAll(specs)
        override fun add(spec: FieldSpec) {
            fields += spec
        }
    }

    private inline fun container(configuration: FieldSpecContainerScope.() -> Unit) =
        FieldSpecContainerScope(container).configuration()

    @Test fun nativeSpec() {
        container += fieldSpecOf<Field1>("field1")
        container += listOf(fieldSpecOf<Field2>("field2"))
        assertThat(fields).containsExactly(
            fieldSpecOf<Field1>("field1"),
            fieldSpecOf<Field2>("field2")
        )
    }

    @Test fun className() {
        val packageName = "com.hendraanggrian.javapoet.dsl.FieldSpecContainerTest"
        container.add(packageName.classOf("Field1"), "field1")
        container["field2"] = packageName.classOf("Field2")
        container { "field3"(packageName.classOf("Field3")) { } }
        assertThat(fields).containsExactly(
            fieldSpecOf<Field1>("field1"),
            fieldSpecOf<Field2>("field2"),
            fieldSpecOf<Field3>("field3")
        )
    }

    @Test fun javaClass() {
        container.add(Field1::class.java, "field1")
        container["field2"] = Field2::class.java
        container { "field3"(Field3::class.java) { } }
        assertThat(fields).containsExactly(
            fieldSpecOf<Field1>("field1"),
            fieldSpecOf<Field2>("field2"),
            fieldSpecOf<Field3>("field3")
        )
    }

    @Test fun kotlinClass() {
        container.add(Field1::class, "field1")
        container["field2"] = Field2::class
        container { "field3"(Field3::class) { } }
        assertThat(fields).containsExactly(
            fieldSpecOf<Field1>("field1"),
            fieldSpecOf<Field2>("field2"),
            fieldSpecOf<Field3>("field3")
        )
    }

    @Test fun reifiedType() {
        container.add<Field1>("field1")
        container { "field2"<Field2> { } }
        assertThat(fields).containsExactly(
            fieldSpecOf<Field1>("field1"),
            fieldSpecOf<Field2>("field2")
        )
    }

    class Field1
    class Field2
    class Field3
}