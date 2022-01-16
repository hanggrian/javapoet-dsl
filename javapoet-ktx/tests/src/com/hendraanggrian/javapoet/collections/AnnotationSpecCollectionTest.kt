package com.hendraanggrian.javapoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.asClassName
import com.squareup.javapoet.AnnotationSpec
import kotlin.test.Test

class AnnotationSpecCollectionTest {

    private val list = AnnotationSpecCollection(mutableListOf())
    private fun list(configuration: AnnotationSpecCollectionScope.() -> Unit) =
        AnnotationSpecCollectionScope(list).configuration()

    private annotation class Annotation1
    private annotation class Annotation2
    private annotation class Annotation3
    private annotation class Annotation4

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