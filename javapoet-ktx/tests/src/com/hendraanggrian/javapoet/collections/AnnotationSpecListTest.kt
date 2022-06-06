package com.hendraanggrian.javapoet.collections

import com.example.Annotation1
import com.example.Annotation2
import com.example.Annotation3
import com.example.Annotation4
import com.example.Annotation5
import com.example.Annotation6
import com.example.Annotation7
import com.example.Annotation8
import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.asClassName
import com.squareup.javapoet.AnnotationSpec
import kotlin.test.Test

class AnnotationSpecListTest {
    private val list = AnnotationSpecList(mutableListOf())

    @Test
    fun add() {
        list.add(Annotation1::class.asClassName())
        list.add(Annotation2::class.java)
        list.add(Annotation3::class)
        list.add<Annotation4>()
        list.add(Annotation5::class.asClassName()) { addMember("name5", "value5") }
        list.add(Annotation6::class.java) { addMember("name6", "value6") }
        list.add(Annotation7::class) { addMember("name7", "value7") }
        list.add<Annotation8> { addMember("name8", "value8") }
        assertThat(list).containsExactly(
            AnnotationSpec.builder(Annotation1::class.java).build(),
            AnnotationSpec.builder(Annotation2::class.java).build(),
            AnnotationSpec.builder(Annotation3::class.java).build(),
            AnnotationSpec.builder(Annotation4::class.java).build(),
            AnnotationSpec.builder(Annotation5::class.java).addMember("name5", "value5").build(),
            AnnotationSpec.builder(Annotation6::class.java).addMember("name6", "value6").build(),
            AnnotationSpec.builder(Annotation7::class.java).addMember("name7", "value7").build(),
            AnnotationSpec.builder(Annotation8::class.java).addMember("name8", "value8").build()
        )
    }

    @Test
    fun plusAssign() {
        list += Annotation1::class.asClassName()
        list += Annotation2::class.java
        list += Annotation3::class
        assertThat(list).containsExactly(
            AnnotationSpec.builder(Annotation1::class.java).build(),
            AnnotationSpec.builder(Annotation2::class.java).build(),
            AnnotationSpec.builder(Annotation3::class.java).build()
        )
    }
}