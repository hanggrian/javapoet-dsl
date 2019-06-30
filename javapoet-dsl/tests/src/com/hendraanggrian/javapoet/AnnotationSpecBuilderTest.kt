package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import kotlin.test.Test
import kotlin.test.assertEquals

class AnnotationSpecBuilderTest {

    @Test
    fun simple() {
        assertEquals(
            AnnotationSpec.builder(Deprecated::class.java).build(),
            AnnotationSpecBuilder.of(Deprecated::class)
        )
    }

    @Test
    fun advanced() {
        assertEquals(
            AnnotationSpec.builder(ClassName.OBJECT)
                .addMember("string", "stringValue")
                .addMember(
                    "code", CodeBlock.builder()
                        .add("codeValue")
                        .build()
                )
                .build(),
            AnnotationSpecBuilder.of(ClassName.OBJECT) {
                members {
                    add("string", "stringValue")
                    "code" {
                        codes.add("codeValue")
                    }
                }
            }
        )
    }
}