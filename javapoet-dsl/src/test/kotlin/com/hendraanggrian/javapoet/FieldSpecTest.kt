package com.hendraanggrian.javapoet

import com.example.Annotation1
import com.example.Field1
import com.example.Field2
import com.example.Field3
import com.example.Field4
import com.example.Field5
import com.example.Field6
import com.example.Field7
import com.google.common.truth.Truth.assertThat
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import javax.lang.model.element.Modifier
import kotlin.test.Test
import kotlin.test.assertEquals

class FieldSpecHandlerTest {
    @Test
    fun field() {
        val type =
            buildClassTypeSpec("test") {
                field(Field1::class.asTypeName(), "field1")
                field(Field2::class.asTypeName(), "field2") { initializer("value2") }
                field(Field3::class.java, "field3")
                field(Field4::class.java, "field4") { initializer("value4") }
                field(Field5::class, "field5")
                field(Field6::class, "field6") { initializer("value6") }
                field<Field7>("field7")
            }
        assertThat(type.fieldSpecs).containsExactly(
            FieldSpec.builder(Field1::class.java, "field1").build(),
            FieldSpec.builder(Field2::class.java, "field2").initializer("value2").build(),
            FieldSpec.builder(Field3::class.java, "field3").build(),
            FieldSpec.builder(Field4::class.java, "field4").initializer("value4").build(),
            FieldSpec.builder(Field5::class.java, "field5").build(),
            FieldSpec.builder(Field6::class.java, "field6").initializer("value6").build(),
            FieldSpec.builder(Field7::class.java, "field7").build(),
        )
    }

    @Test
    fun fielding() {
        val type =
            buildClassTypeSpec("test") {
                val field1 by fielding(Field1::class.asTypeName())
                val field2 by fielding(Field2::class.asTypeName()) { initializer("value2") }
                val field3 by fielding(Field3::class.java)
                val field4 by fielding(Field4::class.java) { initializer("value4") }
                val field5 by fielding(Field5::class)
                val field6 by fielding(Field6::class) { initializer("value6") }
            }
        assertThat(type.fieldSpecs).containsExactly(
            FieldSpec.builder(Field1::class.java, "field1").build(),
            FieldSpec.builder(Field2::class.java, "field2").initializer("value2").build(),
            FieldSpec.builder(Field3::class.java, "field3").build(),
            FieldSpec.builder(Field4::class.java, "field4").initializer("value4").build(),
            FieldSpec.builder(Field5::class.java, "field5").build(),
            FieldSpec.builder(Field6::class.java, "field6").initializer("value6").build(),
        )
    }
}

class FieldSpecBuilderTest {
    @Test
    fun javadoc() {
        assertEquals(
            buildFieldSpec(Field1::class.asTypeName(), "field1") {
                javadoc.append("javadoc1")
                javadoc.append(codeBlockOf("javadoc2"))
            },
            FieldSpec.builder(Field1::class.java, "field1")
                .addJavadoc("javadoc1")
                .addJavadoc(CodeBlock.of("javadoc2"))
                .build(),
        )
    }

    @Test
    fun annotations() {
        assertEquals(
            buildFieldSpec(Field1::class.asTypeName(), "field1") {
                annotation<Annotation1>()
            },
            FieldSpec.builder(Field1::class.java, "field1")
                .addAnnotation(Annotation1::class.java)
                .build(),
        )
    }

    @Test
    fun addModifiers() {
        assertEquals(
            buildFieldSpec(Field1::class.asTypeName(), "field1") {
                modifiers(PUBLIC, FINAL, STATIC)
            },
            FieldSpec.builder(Field1::class.java, "field1")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .build(),
        )
    }

    @Test
    fun initializer() {
        assertEquals(
            buildFieldSpec(Field1::class.asTypeName(), "field1") {
                initializer("value1")
            },
            FieldSpec.builder(Field1::class.java, "field1")
                .initializer("value1")
                .build(),
        )
        assertEquals(
            buildFieldSpec(Field2::class.asTypeName(), "field2") {
                initializer = codeBlockOf("value2")
            },
            FieldSpec.builder(Field2::class.java, "field2")
                .initializer(CodeBlock.of("value2"))
                .build(),
        )
    }
}
