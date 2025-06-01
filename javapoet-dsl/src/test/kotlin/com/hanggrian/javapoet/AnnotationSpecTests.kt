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
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import kotlin.test.Test

class AnnotationSpecCreatorTest {
    @Test
    fun of() =
        assertThat(annotationSpecOf(classNamed("com.example", "MyAnnotation")))
            .isEqualTo(AnnotationSpec.builder(ClassName.get("com.example", "MyAnnotation")).build())

    @Test
    fun build() =
        assertThat(
            buildAnnotationSpec(classNamed("com.example", "MyAnnotation")) {
                addMember("name1", "value1")
            },
        ).isEqualTo(
            AnnotationSpec
                .builder(ClassName.get("com.example", "MyAnnotation"))
                .addMember("name1", "value1")
                .build(),
        )
}

@ExtendWith(MockitoExtension::class)
class AnnotationSpecHandlerTest {
    private val annotationSpecs = mutableListOf<AnnotationSpec>()

    @Spy private val annotations: AnnotationSpecHandler =
        object : AnnotationSpecHandler {
            override fun add(annotation: AnnotationSpec) {
                annotationSpecs += annotation
            }
        }

    private fun annotations(configuration: AnnotationSpecHandlerScope.() -> Unit) =
        AnnotationSpecHandlerScope
            .of(annotations)
            .configuration()

    @Test
    fun add() {
        annotations.add(Annotation1::class.name)
        annotations.add(Annotation2::class.java)
        annotations.add(Annotation3::class)
        annotations.add<Annotation4>()
        annotations.add(Annotation5::class.name) { addMember("name5", "value5") }
        annotations.add(Annotation6::class.java) { addMember("name6", "value6") }
        annotations.add(Annotation7::class) { addMember("name7", "value7") }
        assertThat(annotationSpecs).containsExactly(
            AnnotationSpec.builder(Annotation1::class.java).build(),
            AnnotationSpec.builder(Annotation2::class.java).build(),
            AnnotationSpec.builder(Annotation3::class.java).build(),
            AnnotationSpec.builder(Annotation4::class.java).build(),
            AnnotationSpec.builder(Annotation5::class.java).addMember("name5", "value5").build(),
            AnnotationSpec.builder(Annotation6::class.java).addMember("name6", "value6").build(),
            AnnotationSpec.builder(Annotation7::class.java).addMember("name7", "value7").build(),
        )
        verify(annotations, times(7)).add(any<AnnotationSpec>())
    }

    @Test
    fun invoke() {
        annotations {
            Annotation1::class.name { addMember("name1", "value1") }
            Annotation2::class.java { addMember("name2", "value2") }
            Annotation3::class { addMember("name3", "value3") }
        }
        assertThat(annotationSpecs).containsExactly(
            AnnotationSpec.builder(Annotation1::class.java).addMember("name1", "value1").build(),
            AnnotationSpec.builder(Annotation2::class.java).addMember("name2", "value2").build(),
            AnnotationSpec.builder(Annotation3::class.java).addMember("name3", "value3").build(),
        )
        verify(annotations, times(3)).add(any<AnnotationSpec>())
    }
}

class AnnotationSpecBuilderTest {
    @Test
    fun addMember() =
        assertThat(
            buildAnnotationSpec(Annotation1::class.name) {
                addMember("member1", "value1")
                addMember("member2", codeBlockOf("value2"))
                assertThat(members.isEmpty()).isFalse()
            },
        ).isEqualTo(
            AnnotationSpec
                .builder(Annotation1::class.java)
                .addMember("member1", "value1")
                .addMember("member2", CodeBlock.of("value2"))
                .build(),
        )
}
