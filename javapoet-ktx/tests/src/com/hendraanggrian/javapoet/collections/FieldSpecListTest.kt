package com.hendraanggrian.javapoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.classOf
import com.hendraanggrian.javapoet.fieldSpecOf
import kotlin.test.Test

class FieldSpecListTest {
    private val container = FieldSpecList(mutableListOf())

    private inline fun container(configuration: FieldSpecListScope.() -> Unit) =
        FieldSpecListScope(container).configuration()

    @Test fun nativeSpec() {
        container += fieldSpecOf<Field1>("field1")
        container += listOf(fieldSpecOf<Field2>("field2"))
        assertThat(container).containsExactly(
            fieldSpecOf<Field1>("field1"),
            fieldSpecOf<Field2>("field2")
        )
    }

    @Test fun className() {
        val packageName = "com.hendraanggrian.javapoet.collections.FieldSpecListTest"
        container.add(packageName.classOf("Field1"), "field1")
        container["field2"] = packageName.classOf("Field2")
        container { "field3"(packageName.classOf("Field3")) { } }
        assertThat(container).containsExactly(
            fieldSpecOf<Field1>("field1"),
            fieldSpecOf<Field2>("field2"),
            fieldSpecOf<Field3>("field3")
        )
    }

    @Test fun javaClass() {
        container.add(Field1::class.java, "field1")
        container["field2"] = Field2::class.java
        container { "field3"(Field3::class.java) { } }
        assertThat(container).containsExactly(
            fieldSpecOf<Field1>("field1"),
            fieldSpecOf<Field2>("field2"),
            fieldSpecOf<Field3>("field3")
        )
    }

    @Test fun kotlinClass() {
        container.add(Field1::class, "field1")
        container["field2"] = Field2::class
        container { "field3"(Field3::class) { } }
        assertThat(container).containsExactly(
            fieldSpecOf<Field1>("field1"),
            fieldSpecOf<Field2>("field2"),
            fieldSpecOf<Field3>("field3")
        )
    }

    @Test fun reifiedType() {
        container.add<Field1>("field1")
        container { "field2"<Field2> { } }
        assertThat(container).containsExactly(
            fieldSpecOf<Field1>("field1"),
            fieldSpecOf<Field2>("field2")
        )
    }

    class Field1
    class Field2
    class Field3
}