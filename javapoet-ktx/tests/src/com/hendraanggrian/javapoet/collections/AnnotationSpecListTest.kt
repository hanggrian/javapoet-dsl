package com.hendraanggrian.javapoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.asClassName
import com.hendraanggrian.javapoet.internal.Annotation1
import com.hendraanggrian.javapoet.internal.Annotation2
import com.hendraanggrian.javapoet.internal.Annotation3
import com.hendraanggrian.javapoet.internal.Annotation4
import com.squareup.javapoet.AnnotationSpec
import kotlin.test.Test

class AnnotationSpecListTest {

    private val list = AnnotationSpecList(mutableListOf())
    private fun list(configuration: AnnotationSpecListScope.() -> Unit) = AnnotationSpecListScope(list).configuration()

    @Test
    fun add() {
        list.add(Annotation1::class.asClassName())
        list.add(Annotation2::class.java)
        list.add(Annotation3::class)
        list.add<Annotation4>()
        assertThat(list).containsExactly(
            AnnotationSpec.builder(Annotation1::class.java).build(),
            AnnotationSpec.builder(Annotation2::class.java).build(),
            AnnotationSpec.builder(Annotation3::class.java).build(),
            AnnotationSpec.builder(Annotation4::class.java).build()
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

    @Test
    fun invoke() {
        list {
            (Annotation1::class.asClassName()) { }
            Annotation2::class.java { }
            Annotation3::class { }
        }
        assertThat(list).containsExactly(
            AnnotationSpec.builder(Annotation1::class.java).build(),
            AnnotationSpec.builder(Annotation2::class.java).build(),
            AnnotationSpec.builder(Annotation3::class.java).build()
        )
    }
}