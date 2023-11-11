package com.hendraanggrian.javapoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Annotation3
import com.example.Field1
import com.example.Field2
import com.example.Field3
import com.example.Field4
import com.example.Field5
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
                field(Field2::class.name, "field2") { initializer("value2") }
                field(Field3::class, "field3")
                field(Field4::class, "field4") { initializer("value4") }
                field<Field5>("field5")
            }.fieldSpecs,
        ).containsExactly(
            FieldSpec.builder(Field1::class.java, "field1").build(),
            FieldSpec.builder(Field2::class.java, "field2").initializer("value2").build(),
            FieldSpec.builder(Field3::class.java, "field3").build(),
            FieldSpec.builder(Field4::class.java, "field4").initializer("value4").build(),
            FieldSpec.builder(Field5::class.java, "field5").build(),
        )
    }

    @Test
    fun fielding() {
        assertThat(
            buildClassTypeSpec("test") {
                val field1 by fielding(Field1::class.name)
                val field2 by fielding(Field2::class.name) { initializer("value2") }
                val field3 by fielding(Field3::class)
                val field4 by fielding(Field4::class) { initializer("value4") }
            }.fieldSpecs,
        ).containsExactly(
            FieldSpec.builder(Field1::class.java, "field1").build(),
            FieldSpec.builder(Field2::class.java, "field2").initializer("value2").build(),
            FieldSpec.builder(Field3::class.java, "field3").build(),
            FieldSpec.builder(Field4::class.java, "field4").initializer("value4").build(),
        )
    }

    @Test
    fun invoke() {
        assertThat(
            buildClassTypeSpec("test") {
                fields {
                    "field1"(Field1::class.name) { initializer("value1") }
                    "field2"(Field2::class) { initializer("value2") }
                }
            }.fieldSpecs,
        ).containsExactly(
            FieldSpec.builder(Field1::class.java, "field1").initializer("value1").build(),
            FieldSpec.builder(Field2::class.java, "field2").initializer("value2").build(),
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
            FieldSpec.builder(Field1::class.java, "field1")
                .addJavadoc("javadoc1")
                .addJavadoc(CodeBlock.of("javadoc2"))
                .build(),
        )
    }

    @Test
    fun annotation() {
        assertThat(
            buildFieldSpec(Field1::class.name, "field1") {
                annotation(Annotation1::class.name)
                annotation(Annotation2::class)
                annotation<Annotation3>()
                assertFalse(annotations.isEmpty())
            },
        ).isEqualTo(
            FieldSpec.builder(Field1::class.java, "field1")
                .addAnnotation(Annotation1::class.java)
                .addAnnotation(Annotation2::class.java)
                .addAnnotation(Annotation3::class.java)
                .build(),
        )
    }

    @Test
    fun modifiers() {
        assertThat(
            buildFieldSpec(Field1::class.name, "field1") {
                modifiers(PUBLIC, FINAL, STATIC)
                assertFalse(modifiers.isEmpty())
            },
        ).isEqualTo(
            FieldSpec.builder(Field1::class.java, "field1")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .build(),
        )
    }

    @Test
    fun initializer() {
        assertThat(
            buildFieldSpec(Field1::class.name, "field1") {
                initializer("value1")
            },
        ).isEqualTo(
            FieldSpec.builder(Field1::class.java, "field1")
                .initializer("value1")
                .build(),
        )
        assertThat(
            buildFieldSpec(Field2::class.name, "field2") {
                initializer = codeBlockOf("value2")
            },
        ).isEqualTo(
            FieldSpec.builder(Field2::class.java, "field2")
                .initializer(CodeBlock.of("value2"))
                .build(),
        )
    }
}
