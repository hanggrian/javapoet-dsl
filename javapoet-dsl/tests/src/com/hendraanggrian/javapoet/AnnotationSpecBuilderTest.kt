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
        assertEquals(expected, AnnotationSpecBuilder.of(Deprecated::class) {
            members.add("message", "Old stuff")
            members.add("code") {
                add("codeValue")
            }
        })
    }

    @Test
    fun invocation() {
        assertEquals(expected, AnnotationSpecBuilder.of(Deprecated::class) {
            members {
                add("message", "Old stuff")
                "code" {
                    add("codeValue")
                }
            }
        })
    }
}