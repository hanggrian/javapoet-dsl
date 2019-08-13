package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import kotlin.test.Test
import kotlin.test.assertEquals

class AnnotationSpecBuilderTest {
    private val getBuilder = { AnnotationSpec.builder(Deprecated::class.java) }
    private val expected = getBuilder()
        .addMember("message", "Old stuff")
        .addMember(
            "code", CodeBlock.builder()
                .add("codeValue")
                .build()
        )
        .build()

    @Test
    fun simple() {
        assertEquals(expected, (getBuilder()) {
            members.add("message", "Old stuff")
            members.add("code") {
                append("codeValue")
            }
        })
    }

    @Test
    fun invocation() {
        assertEquals(expected, (getBuilder()) {
            members {
                add("message", "Old stuff")
                "code" {
                    append("codeValue")
                }
            }
        })
    }
}