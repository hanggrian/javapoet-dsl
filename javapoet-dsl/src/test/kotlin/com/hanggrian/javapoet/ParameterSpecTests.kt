package com.hanggrian.javapoet

import com.example.Annotation1
import com.example.Field1
import com.example.Parameter1
import com.example.Parameter2
import com.example.Parameter3
import com.example.Parameter4
import com.example.Parameter5
import com.example.Parameter6
import com.example.Parameter7
import com.google.common.truth.Truth.assertThat
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.ParameterSpec
import kotlin.test.Test
import kotlin.test.assertFalse

class ParameterSpecHandlerTest {
    @Test
    fun parameter() {
        assertThat(
            buildMethodSpec("test") {
                parameter(Parameter1::class.name, "parameter1")
                parameter(Parameter2::class.java, "parameter2")
                parameter(Parameter3::class, "parameter3")
                parameter<Parameter4>("parameter4")
                parameter(Parameter5::class.name, "parameter5") { javadoc("text5") }
                parameter(Parameter6::class.java, "parameter6") { javadoc("text6") }
                parameter(Parameter7::class, "parameter7") { javadoc("text7") }
            }.parameters,
        ).containsExactly(
            ParameterSpec.builder(Parameter1::class.java, "parameter1").build(),
            ParameterSpec.builder(Parameter2::class.java, "parameter2").build(),
            ParameterSpec.builder(Parameter3::class.java, "parameter3").build(),
            ParameterSpec.builder(Parameter4::class.java, "parameter4").build(),
            ParameterSpec.builder(Parameter5::class.java, "parameter5").addJavadoc("text5").build(),
            ParameterSpec.builder(Parameter6::class.java, "parameter6").addJavadoc("text6").build(),
            ParameterSpec.builder(Parameter7::class.java, "parameter7").addJavadoc("text7").build(),
        )
    }

    @Test
    fun parametering() {
        assertThat(
            buildMethodSpec("test") {
                val parameter1 by parametering(Parameter1::class.name)
                val parameter2 by parametering(Parameter2::class.java)
                val parameter3 by parametering(Parameter3::class)
                val parameter4 by parametering(Parameter4::class.name) { javadoc("text4") }
                val parameter5 by parametering(Parameter5::class.java) { javadoc("text5") }
                val parameter6 by parametering(Parameter6::class) { javadoc("text6") }
            }.parameters,
        ).containsExactly(
            ParameterSpec.builder(Parameter1::class.java, "parameter1").build(),
            ParameterSpec.builder(Parameter2::class.java, "parameter2").build(),
            ParameterSpec.builder(Parameter3::class.java, "parameter3").build(),
            ParameterSpec.builder(Parameter4::class.java, "parameter4").addJavadoc("text4").build(),
            ParameterSpec.builder(Parameter5::class.java, "parameter5").addJavadoc("text5").build(),
            ParameterSpec.builder(Parameter6::class.java, "parameter6").addJavadoc("text6").build(),
        )
    }

    @Test
    fun invoke() {
        assertThat(
            buildMethodSpec("test") {
                parameters {
                    "parameter1"(Parameter1::class.name) { javadoc("text1") }
                    "parameter2"(Parameter2::class.java) { javadoc("text2") }
                    "parameter3"(Parameter3::class) { javadoc("text3") }
                }
            }.parameters,
        ).containsExactly(
            ParameterSpec.builder(Parameter1::class.java, "parameter1").addJavadoc("text1").build(),
            ParameterSpec.builder(Parameter2::class.java, "parameter2").addJavadoc("text2").build(),
            ParameterSpec.builder(Parameter3::class.java, "parameter3").addJavadoc("text3").build(),
        )
    }
}

class ParameterSpecBuilderTest {
    @Test
    fun javadoc() {
        assertThat(
            buildParameterSpec(Field1::class.name, "parameter1") {
                javadoc("javadoc1")
                javadoc(codeBlockOf("javadoc2"))
            },
        ).isEqualTo(
            ParameterSpec
                .builder(Field1::class.java, "parameter1")
                .addJavadoc("javadoc1")
                .addJavadoc(CodeBlock.of("javadoc2"))
                .build(),
        )
    }

    @Test
    fun annotation() {
        assertThat(
            buildParameterSpec(Field1::class.name, "parameter1") {
                annotation(annotationSpecOf(Annotation1::class.name))
                assertFalse(annotations.isEmpty())
            },
        ).isEqualTo(
            ParameterSpec
                .builder(Field1::class.java, "parameter1")
                .addAnnotation(Annotation1::class.java)
                .build(),
        )
    }

    @Test
    fun modifiers() {
        assertThat(
            buildParameterSpec(Field1::class.name, "parameter1") {
                modifiers(PUBLIC)
                modifiers += listOf(FINAL)
                assertFalse(modifiers.isEmpty())
            },
        ).isEqualTo(
            ParameterSpec
                .builder(Field1::class.java, "parameter1")
                .addModifiers(PUBLIC)
                .addModifiers(listOf(FINAL))
                .build(),
        )
    }
}
