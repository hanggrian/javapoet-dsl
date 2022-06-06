package com.hendraanggrian.javapoet

import com.example.Annotation1
import com.example.Field1
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.ParameterSpec
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterSpecBuilderTest {

    @Test
    fun javadoc() {
        assertEquals(
            buildParameterSpec<Field1>("parameter1") {
                javadoc {
                    append("javadoc1")
                    append(codeBlockOf("javadoc2"))
                }
            },
            ParameterSpec.builder(Field1::class.java, "parameter1")
                .addJavadoc("javadoc1")
                .addJavadoc(CodeBlock.of("javadoc2"))
                .build()
        )
    }

    @Test
    fun annotations() {
        assertEquals(
            buildParameterSpec<Field1>("parameter1") { annotations.add<Annotation1>() },
            ParameterSpec.builder(Field1::class.java, "parameter1").addAnnotation(Annotation1::class.java).build()
        )
    }

    @Test
    fun addModifiers() {
        assertEquals(
            buildParameterSpec<Field1>("parameter1") { addModifiers(FINAL) },
            ParameterSpec.builder(Field1::class.java, "parameter1").addModifiers(FINAL).build()
        )
    }
}