package com.hanggrian.javapoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Annotation3
import com.example.Field1
import com.example.Parameter1
import com.example.Parameter2
import com.example.Parameter3
import com.example.Parameter4
import com.example.Parameter5
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
                parameter(Parameter2::class.name, "parameter2") { javadoc("text2") }
                parameter(Parameter3::class, "parameter3")
                parameter(Parameter4::class, "parameter4") { javadoc("text4`") }
                parameter<Parameter5>("parameter5")
            }.parameters,
        ).containsExactly(
            ParameterSpec.builder(Parameter1::class.java, "parameter1").build(),
            ParameterSpec.builder(Parameter2::class.java, "parameter2").addJavadoc("text2").build(),
            ParameterSpec.builder(Parameter3::class.java, "parameter3").build(),
            ParameterSpec.builder(Parameter4::class.java, "parameter4").addJavadoc("text4").build(),
            ParameterSpec.builder(Parameter5::class.java, "parameter5").build(),
        )
    }

    @Test
    fun parametering() {
        assertThat(
            buildMethodSpec("test") {
                val parameter1 by parametering(Parameter1::class.name)
                val parameter2 by parametering(Parameter2::class.name) { javadoc("text2") }
                val parameter3 by parametering(Parameter3::class)
                val parameter4 by parametering(Parameter4::class) { javadoc("text4") }
            }.parameters,
        ).containsExactly(
            ParameterSpec.builder(Parameter1::class.java, "parameter1").build(),
            ParameterSpec.builder(Parameter2::class.java, "parameter2").addJavadoc("text2").build(),
            ParameterSpec.builder(Parameter3::class.java, "parameter3").build(),
            ParameterSpec.builder(Parameter4::class.java, "parameter4").addJavadoc("text4").build(),
        )
    }

    @Test
    fun invoke() {
        assertThat(
            buildMethodSpec("test") {
                parameters {
                    "parameter1"(Parameter1::class.name) { javadoc("text1") }
                    "parameter2"(Parameter2::class) { javadoc("text2") }
                }
            }.parameters,
        ).containsExactly(
            ParameterSpec.builder(Parameter1::class.java, "parameter1").addJavadoc("text1").build(),
            ParameterSpec.builder(Parameter2::class.java, "parameter2").addJavadoc("text2").build(),
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
                annotation(Annotation1::class.name)
                annotation(Annotation2::class)
                annotation<Annotation3>()
                assertFalse(annotations.isEmpty())
            },
        ).isEqualTo(
            ParameterSpec
                .builder(Field1::class.java, "parameter1")
                .addAnnotation(Annotation1::class.java)
                .addAnnotation(Annotation2::class.java)
                .addAnnotation(Annotation3::class.java)
                .build(),
        )
    }

    @Test
    fun modifiers() {
        assertThat(
            buildParameterSpec(Field1::class.name, "parameter1") {
                modifiers(PUBLIC, STATIC, FINAL)
                assertFalse(modifiers.isEmpty())
            },
        ).isEqualTo(
            ParameterSpec
                .builder(Field1::class.java, "parameter1")
                .addModifiers(PUBLIC, STATIC, FINAL)
                .build(),
        )
    }
}
