package com.hendraanggrian.javapoet.dsl

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.buildField
import com.hendraanggrian.javapoet.classNameOf
import com.squareup.javapoet.FieldSpec
import kotlin.test.Test

class FieldSpecContainerTest {
    private val specs = mutableListOf<FieldSpec>()
    private val container = object : FieldSpecContainer() {
        override fun add(spec: FieldSpec) {
            specs += spec
        }
    }

    private inline fun container(configuration: FieldSpecContainerScope.() -> Unit) =
        FieldSpecContainerScope(container).configuration()

    @Test fun nativeSpec() {
        container.add(buildField<Field1>("field1"))
        container += buildField<Field2>("field2")
        assertThat(specs).containsExactly(
            buildField<Field1>("field1"),
            buildField<Field2>("field2")
        )
    }

    @Test fun className() {
        val packageName = "com.hendraanggrian.javapoet.dsl.FieldSpecContainerTest"
        container.add(classNameOf(packageName, "Field1"), "field1")
        container["field2"] = classNameOf(packageName, "Field2")
        container { "field3"(classNameOf(packageName, "Field3")) { } }
        assertThat(specs).containsExactly(
            buildField<Field1>("field1"),
            buildField<Field2>("field2"),
            buildField<Field3>("field3")
        )
    }

    @Test fun javaClass() {
        container.add(Field1::class.java, "field1")
        container["field2"] = Field2::class.java
        container { "field3"(Field3::class.java) { } }
        assertThat(specs).containsExactly(
            buildField<Field1>("field1"),
            buildField<Field2>("field2"),
            buildField<Field3>("field3")
        )
    }

    @Test fun kotlinClass() {
        container.add(Field1::class, "field1")
        container["field2"] = Field2::class
        container { "field3"(Field3::class) { } }
        assertThat(specs).containsExactly(
            buildField<Field1>("field1"),
            buildField<Field2>("field2"),
            buildField<Field3>("field3")
        )
    }

    @Test fun reifiedType() {
        container.add<Field1>("field1")
        container { "field2"<Field2> { } }
        assertThat(specs).containsExactly(
            buildField<Field1>("field1"),
            buildField<Field2>("field2")
        )
    }

    class Field1
    class Field2
    class Field3
}