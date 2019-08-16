package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import kotlin.test.Test
import kotlin.test.assertEquals

class AnnotationSpecBuilderTest {
    private val expected = AnnotationSpec.builder(Deprecated::class.java)
        .addMember("message", "Old stuff")
        .addMember(
            "code", CodeBlock.builder()
                .add("codeValue")
                .build()
        )
        .build()

    @Test
    fun simple() {
        assertEquals(expected, buildAnnotation<Deprecated> {
            addMember("message", "Old stuff")
            addMember("code") {
                append("codeValue")
            }
        })
    }

    @Test
    fun invocation() {
        assertEquals(expected, buildAnnotation<Deprecated> {
            addMember("message", "Old stuff")
            "code" {
                append("codeValue")
            }
        })
    }
}