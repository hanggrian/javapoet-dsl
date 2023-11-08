package com.hendraanggrian.javapoet

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
import kotlin.test.assertEquals

class ParameterSpecHandlerTest {
    @Test
    fun parameter() {
        val method =
            buildMethodSpec("test") {
                parameter(Parameter1::class.asTypeName(), "parameter1")
                parameter(Parameter2::class.asTypeName(), "parameter2") { javadoc.append("text2") }
                parameter(Parameter3::class.java, "parameter3")
                parameter(Parameter4::class.java, "parameter4") { javadoc.append("text4") }
                parameter(Parameter5::class, "parameter5")
                parameter(Parameter6::class, "parameter6") { javadoc.append("text6") }
                parameter<Parameter7>("parameter7")
            }
        assertThat(method.parameters).containsExactly(
            ParameterSpec.builder(Parameter1::class.java, "parameter1").build(),
            ParameterSpec.builder(Parameter2::class.java, "parameter2").addJavadoc("text2").build(),
            ParameterSpec.builder(Parameter3::class.java, "parameter3").build(),
            ParameterSpec.builder(Parameter4::class.java, "parameter4").addJavadoc("text4").build(),
            ParameterSpec.builder(Parameter5::class.java, "parameter5").build(),
            ParameterSpec.builder(Parameter6::class.java, "parameter6").addJavadoc("text6").build(),
            ParameterSpec.builder(Parameter7::class.java, "parameter7").build(),
        )
    }

    @Test
    fun parametering() {
        val method =
            buildMethodSpec("test") {
                val parameter1 by parametering(Parameter1::class.asTypeName())
                val parameter2 by parametering(Parameter2::class.asTypeName()) {
                    javadoc.append("text2")
                }
                val parameter3 by parametering(Parameter3::class.java)
                val parameter4 by parametering(Parameter4::class.java) { javadoc.append("text4") }
                val parameter5 by parametering(Parameter5::class)
                val parameter6 by parametering(Parameter6::class) { javadoc.append("text6") }
            }
        assertThat(method.parameters).containsExactly(
            ParameterSpec.builder(Parameter1::class.java, "parameter1").build(),
            ParameterSpec.builder(Parameter2::class.java, "parameter2").addJavadoc("text2").build(),
            ParameterSpec.builder(Parameter3::class.java, "parameter3").build(),
            ParameterSpec.builder(Parameter4::class.java, "parameter4").addJavadoc("text4").build(),
            ParameterSpec.builder(Parameter5::class.java, "parameter5").build(),
            ParameterSpec.builder(Parameter6::class.java, "parameter6").addJavadoc("text6").build(),
        )
    }
}

class ParameterSpecBuilderTest {
    @Test
    fun javadoc() {
        assertEquals(
            buildParameterSpec(Field1::class.asTypeName(), "parameter1") {
                javadoc.append("javadoc1")
                javadoc.append(codeBlockOf("javadoc2"))
            },
            ParameterSpec.builder(Field1::class.java, "parameter1")
                .addJavadoc("javadoc1")
                .addJavadoc(CodeBlock.of("javadoc2"))
                .build(),
        )
    }

    @Test
    fun annotations() {
        assertEquals(
            buildParameterSpec(Field1::class.asTypeName(), "parameter1") {
                annotation<Annotation1>()
            },
            ParameterSpec.builder(Field1::class.java, "parameter1")
                .addAnnotation(Annotation1::class.java)
                .build(),
        )
    }

    @Test
    fun addModifiers() {
        assertEquals(
            buildParameterSpec(Field1::class.asTypeName(), "parameter1") { modifiers(FINAL) },
            ParameterSpec.builder(Field1::class.java, "parameter1").addModifiers(FINAL).build(),
        )
    }
}
