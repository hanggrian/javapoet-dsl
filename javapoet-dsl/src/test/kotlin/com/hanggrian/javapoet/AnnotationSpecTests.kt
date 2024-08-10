package com.hanggrian.javapoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Annotation3
import com.example.Annotation4
import com.example.Annotation5
import com.example.Annotation6
import com.example.Annotation7
import com.google.common.truth.Truth.assertThat
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import kotlin.test.Test
import kotlin.test.assertFalse

class AnnotationSpecHandlerTest {
    @Test
    fun add() {
        assertThat(
            buildFieldSpec(STRING, "test") {
                annotations.add(Annotation1::class.name)
                annotations.add(Annotation2::class.java)
                annotations.add(Annotation3::class)
                annotations.add<Annotation4>()
                annotations.add(Annotation5::class.name) { addMember("name5", "value5") }
                annotations.add(Annotation6::class.java) { addMember("name6", "value6") }
                annotations.add(Annotation7::class) { addMember("name7", "value7") }
            }.annotations,
        ).containsExactly(
            AnnotationSpec.builder(Annotation1::class.java).build(),
            AnnotationSpec.builder(Annotation2::class.java).build(),
            AnnotationSpec.builder(Annotation3::class.java).build(),
            AnnotationSpec.builder(Annotation4::class.java).build(),
            AnnotationSpec.builder(Annotation5::class.java).addMember("name5", "value5").build(),
            AnnotationSpec.builder(Annotation6::class.java).addMember("name6", "value6").build(),
            AnnotationSpec.builder(Annotation7::class.java).addMember("name7", "value7").build(),
        )
    }

    @Test
    fun invoke() {
        assertThat(
            buildFieldSpec(STRING, "test") {
                annotations {
                    Annotation1::class.name { addMember("name1", "value1") }
                    Annotation2::class.java { addMember("name2", "value2") }
                    Annotation3::class { addMember("name3", "value3") }
                }
            }.annotations,
        ).containsExactly(
            AnnotationSpec.builder(Annotation1::class.java).addMember("name1", "value1").build(),
            AnnotationSpec.builder(Annotation2::class.java).addMember("name2", "value2").build(),
            AnnotationSpec.builder(Annotation3::class.java).addMember("name3", "value3").build(),
        )
    }
}

class AnnotationSpecBuilderTest {
    @Test
    fun addMember() {
        assertThat(
            buildAnnotationSpec(Annotation1::class.name) {
                addMember("member1", "value1")
                addMember("member2", codeBlockOf("value2"))
                assertFalse(members.isEmpty())
            },
        ).isEqualTo(
            AnnotationSpec
                .builder(Annotation1::class.java)
                .addMember("member1", "value1")
                .addMember("member2", CodeBlock.of("value2"))
                .build(),
        )
    }
}
