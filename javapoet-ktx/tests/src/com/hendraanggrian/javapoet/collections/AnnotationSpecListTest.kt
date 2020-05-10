package com.hendraanggrian.javapoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.annotationSpecOf
import com.hendraanggrian.javapoet.classOf
import kotlin.test.Test

class AnnotationSpecListTest {
    private val list = AnnotationSpecList(mutableListOf())

    private inline fun container(configuration: AnnotationSpecListScope.() -> Unit) =
        AnnotationSpecListScope(list).configuration()

    @Test fun nativeSpec() {
        list += annotationSpecOf<Annotation1>()
        list += listOf(annotationSpecOf<Annotation2>())
        assertThat(list).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>()
        )
    }

    @Test fun className() {
        val packageName = "com.hendraanggrian.javapoet.collections.AnnotationSpecListTest"
        list.add(packageName.classOf("Annotation1"))
        list += packageName.classOf("Annotation2")
        container { (packageName.classOf("Annotation3")) { } }
        assertThat(list).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>(),
            annotationSpecOf<Annotation3>()
        )
    }

    @Test fun javaClass() {
        list.add(Annotation1::class.java)
        list += Annotation2::class.java
        container { Annotation3::class.java { } }
        assertThat(list).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>(),
            annotationSpecOf<Annotation3>()
        )
    }

    @Test fun kotlinClass() {
        list.add(Annotation1::class)
        list += Annotation2::class
        container { Annotation3::class { } }
        assertThat(list).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>(),
            annotationSpecOf<Annotation3>()
        )
    }

    @Test fun reifiedType() {
        list.add<Annotation1>()
        assertThat(list).containsExactly(annotationSpecOf<Annotation1>())
    }

    annotation class Annotation1
    annotation class Annotation2
    annotation class Annotation3
}