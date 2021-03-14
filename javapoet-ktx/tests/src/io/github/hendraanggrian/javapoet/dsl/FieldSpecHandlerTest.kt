package io.github.hendraanggrian.javapoet.dsl

import com.google.common.truth.Truth.assertThat
import io.github.hendraanggrian.javapoet.classOf
import io.github.hendraanggrian.javapoet.fieldSpecOf
import kotlin.test.Test

class FieldSpecHandlerTest {
    private val list = FieldSpecHandler(mutableListOf())

    private inline fun container(configuration: FieldSpecHandlerScope.() -> Unit) =
        FieldSpecHandlerScope(list).configuration()

    @Test fun nativeSpec() {
        list += fieldSpecOf<Field1>("field1")
        list += listOf(fieldSpecOf<Field2>("field2"))
        assertThat(list).containsExactly(
            fieldSpecOf<Field1>("field1"),
            fieldSpecOf<Field2>("field2")
        )
    }

    @Test fun className() {
        val packageName = "io.github.hendraanggrian.javapoet.dsl.FieldSpecHandlerTest"
        list.add(packageName.classOf("Field1"), "field1")
        list["field2"] = packageName.classOf("Field2")
        container { "field3"(packageName.classOf("Field3")) { } }
        assertThat(list).containsExactly(
            fieldSpecOf<Field1>("field1"),
            fieldSpecOf<Field2>("field2"),
            fieldSpecOf<Field3>("field3")
        )
    }

    @Test fun javaClass() {
        list.add(Field1::class.java, "field1")
        list["field2"] = Field2::class.java
        container { "field3"(Field3::class.java) { } }
        assertThat(list).containsExactly(
            fieldSpecOf<Field1>("field1"),
            fieldSpecOf<Field2>("field2"),
            fieldSpecOf<Field3>("field3")
        )
    }

    @Test fun kotlinClass() {
        list.add(Field1::class, "field1")
        list["field2"] = Field2::class
        container { "field3"(Field3::class) { } }
        assertThat(list).containsExactly(
            fieldSpecOf<Field1>("field1"),
            fieldSpecOf<Field2>("field2"),
            fieldSpecOf<Field3>("field3")
        )
    }

    @Test fun reifiedType() {
        list.add<Field1>("field1")
        container { "field2"<Field2> { } }
        assertThat(list).containsExactly(
            fieldSpecOf<Field1>("field1"),
            fieldSpecOf<Field2>("field2")
        )
    }

    class Field1
    class Field2
    class Field3
}