package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.Annotation1
import com.hendraanggrian.javapoet.internal.Annotation2
import com.hendraanggrian.javapoet.internal.Field1
import com.hendraanggrian.javapoet.internal.Field2
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import javax.lang.model.element.Modifier
import kotlin.test.Test
import kotlin.test.assertEquals

class FieldSpecBuilderTest {

    @Test
    fun javadoc() {
        assertEquals(
            buildFieldSpec<Field1>("field1") {
                javadoc {
                    append("javadoc1")
                    append(codeBlockOf("javadoc2"))
                }
            },
            FieldSpec.builder(Field1::class.java, "field1")
                .addJavadoc("javadoc1")
                .addJavadoc(CodeBlock.of("javadoc2"))
                .build()
        )
    }

    @Test
    fun annotations() {
        assertEquals(
            buildFieldSpec<Field1>("field1") { annotations.add<Annotation1>() },
            FieldSpec.builder(Field1::class.java, "field1").addAnnotation(Annotation1::class.java).build()
        )
    }

    @Test
    fun addModifiers() {
        assertEquals(
            buildFieldSpec<Field1>("field1") { addModifiers(PUBLIC, FINAL, STATIC) },
            FieldSpec.builder(Field1::class.java, "field1")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .build()
        )
    }

    @Test
    fun initializer() {
        assertEquals(
            buildFieldSpec<Field1>("field1") { initializer("value1") },
            FieldSpec.builder(Field1::class.java, "field1").initializer("value1").build()
        )
        assertEquals(
            buildFieldSpec<Field2>("field2") { initializer = codeBlockOf("value2") },
            FieldSpec.builder(Field2::class.java, "field2").initializer(CodeBlock.of("value2")).build()
        )
    }
}