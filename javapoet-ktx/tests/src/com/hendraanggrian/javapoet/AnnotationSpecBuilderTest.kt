package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.Annotation1
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import kotlin.test.Test
import kotlin.test.assertEquals

class AnnotationSpecBuilderTest {

    @Test
    fun addMember() {
        assertEquals(
            buildAnnotationSpec<Annotation1> {
                addMember("member1", "value1")
                addMember("member2", codeBlockOf("value2"))
            },
            AnnotationSpec.builder(Annotation1::class.java)
                .addMember("member1", "value1")
                .addMember("member2", CodeBlock.of("value2"))
                .build()
        )
    }

    @Test
    fun invoke() {
        assertEquals(
            buildAnnotationSpec<Annotation1> { "member1" { append("value1") } },
            AnnotationSpec.builder(Annotation1::class.java).addMember("member1", CodeBlock.of("value1")).build()
        )
    }
}