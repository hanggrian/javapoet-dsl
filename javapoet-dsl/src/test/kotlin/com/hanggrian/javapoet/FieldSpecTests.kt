package com.hanggrian.javapoet

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
import kotlin.test.assertFalse

class FieldSpecHandlerTest {
    @Test
    fun field() {
        assertThat(
            buildClassTypeSpec("test") {
                field(Field1::class.name, "field1")
                field(Field2::class.java, "field2")
                field(Field3::class, "field3")
                field<Field4>("field4")
                field(Field5::class.name, "field5") { initializer("value5") }
                field(Field6::class.java, "field6") { initializer("value6") }
                field(Field7::class, "field7") { initializer("value7") }
            }.fieldSpecs,
        ).containsExactly(
            FieldSpec.builder(Field1::class.java, "field1").build(),
            FieldSpec.builder(Field2::class.java, "field2").build(),
            FieldSpec.builder(Field3::class.java, "field3").build(),
            FieldSpec.builder(Field4::class.java, "field4").build(),
            FieldSpec.builder(Field5::class.java, "field5").initializer("value5").build(),
            FieldSpec.builder(Field6::class.java, "field6").initializer("value6").build(),
            FieldSpec.builder(Field7::class.java, "field7").initializer("value7").build(),
        )
    }

    @Test
    fun fielding() {
        assertThat(
            buildClassTypeSpec("test") {
                val field1 by fielding(Field1::class.name)
                val field2 by fielding(Field2::class.java)
                val field3 by fielding(Field3::class)
                val field4 by fielding(Field4::class.name) { initializer("value4") }
                val field5 by fielding(Field5::class.java) { initializer("value5") }
                val field6 by fielding(Field6::class) { initializer("value6") }
            }.fieldSpecs,
        ).containsExactly(
            FieldSpec.builder(Field1::class.java, "field1").build(),
            FieldSpec.builder(Field2::class.java, "field2").build(),
            FieldSpec.builder(Field3::class.java, "field3").build(),
            FieldSpec.builder(Field4::class.java, "field4").initializer("value4").build(),
            FieldSpec.builder(Field5::class.java, "field5").initializer("value5").build(),
            FieldSpec.builder(Field6::class.java, "field6").initializer("value6").build(),
        )
    }

    @Test
    fun invoke() {
        assertThat(
            buildClassTypeSpec("test") {
                fields {
                    "field1"(Field1::class.name) { initializer("value1") }
                    "field2"(Field2::class.java) { initializer("value2") }
                    "field3"(Field3::class) { initializer("value3") }
                }
            }.fieldSpecs,
        ).containsExactly(
            FieldSpec.builder(Field1::class.java, "field1").initializer("value1").build(),
            FieldSpec.builder(Field2::class.java, "field2").initializer("value2").build(),
            FieldSpec.builder(Field3::class.java, "field3").initializer("value3").build(),
        )
    }
}

class FieldSpecBuilderTest {
    @Test
    fun javadoc() {
        assertThat(
            buildFieldSpec(Field1::class.name, "field1") {
                javadoc("javadoc1")
                javadoc(codeBlockOf("javadoc2"))
            },
        ).isEqualTo(
            FieldSpec
                .builder(Field1::class.java, "field1")
                .addJavadoc("javadoc1")
                .addJavadoc(CodeBlock.of("javadoc2"))
                .build(),
        )
    }

    @Test
    fun annotation() {
        assertThat(
            buildFieldSpec(Field1::class.name, "field1") {
                annotation(annotationSpecOf(Annotation1::class.name))
                assertFalse(annotations.isEmpty())
            },
        ).isEqualTo(
            FieldSpec
                .builder(Field1::class.java, "field1")
                .addAnnotation(Annotation1::class.java)
                .build(),
        )
    }

    @Test
    fun modifiers() {
        assertThat(
            buildFieldSpec(Field1::class.name, "field1") {
                modifiers(PUBLIC)
                modifiers += listOf(STATIC, FINAL)
                assertFalse(modifiers.isEmpty())
            },
        ).isEqualTo(
            FieldSpec
                .builder(Field1::class.java, "field1")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC, Modifier.FINAL)
                .build(),
        )
    }

    @Test
    fun initializer() {
        assertThat(
            buildFieldSpec(Field1::class.name, "field1") { initializer("value1") },
        ).isEqualTo(
            FieldSpec
                .builder(Field1::class.java, "field1")
                .initializer("value1")
                .build(),
        )
        assertThat(
            buildFieldSpec(Field2::class.name, "field2") { initializer = codeBlockOf("value2") },
        ).isEqualTo(
            FieldSpec
                .builder(Field2::class.java, "field2")
                .initializer(CodeBlock.of("value2"))
                .build(),
        )
    }
}
