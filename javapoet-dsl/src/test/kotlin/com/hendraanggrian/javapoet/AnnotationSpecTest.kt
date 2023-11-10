package com.hendraanggrian.javapoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Annotation3
import com.example.Annotation4
import com.example.Annotation5
import com.google.common.truth.Truth.assertThat
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import kotlin.test.Test
import kotlin.test.assertFalse

class AnnotationSpecHandlerTest {
    @Test
    fun annotation() {
        assertThat(
            buildFieldSpec(String::class.name, "test") {
                annotation(Annotation1::class.name)
                annotation(Annotation2::class)
                annotation<Annotation3>()
                annotation(Annotation4::class.name) { member("name4", "value4") }
                annotation(Annotation5::class) { member("name5", "value5") }
            }.annotations,
        ).containsExactly(
            AnnotationSpec.builder(Annotation1::class.java).build(),
            AnnotationSpec.builder(Annotation2::class.java).build(),
            AnnotationSpec.builder(Annotation3::class.java).build(),
            AnnotationSpec.builder(Annotation4::class.java).addMember("name4", "value4").build(),
            AnnotationSpec.builder(Annotation5::class.java).addMember("name5", "value5").build(),
        )
    }

    @Test
    fun invoke() {
        assertThat(
            buildFieldSpec(String::class.name, "test") {
                annotations {
                    Annotation1::class.name { member("name1", "value1") }
                    Annotation2::class { member("name2", "value2") }
                }
            }.annotations,
        ).containsExactly(
            AnnotationSpec.builder(Annotation1::class.java).addMember("name1", "value1").build(),
            AnnotationSpec.builder(Annotation2::class.java).addMember("name2", "value2").build(),
        )
    }
}

class AnnotationSpecBuilderTest {
    @Test
    fun member() {
        assertThat(
            buildAnnotationSpec(Annotation1::class.name) {
                member("member1", "value1")
                member("member2", codeBlockOf("value2"))
                assertFalse(members.isEmpty())
            },
        ).isEqualTo(
            AnnotationSpec.builder(Annotation1::class.java)
                .addMember("member1", "value1")
                .addMember("member2", CodeBlock.of("value2"))
                .build(),
        )
    }
}
