package com.hendraanggrian.javapoet.dsl

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.annotationSpecOf
import com.hendraanggrian.javapoet.classOf
import com.squareup.javapoet.AnnotationSpec
import kotlin.test.Test

class AnnotationSpecContainerTest {
    private val annotations = mutableListOf<AnnotationSpec>()
    private val container = object : AnnotationSpecContainer() {
        override fun addAll(specs: Iterable<AnnotationSpec>): Boolean = annotations.addAll(specs)
        override fun add(spec: AnnotationSpec) {
            annotations += spec
        }
    }

    private inline fun container(configuration: AnnotationSpecContainerScope.() -> Unit) =
        AnnotationSpecContainerScope(container).configuration()

    @Test fun nativeSpec() {
        container.add(annotationSpecOf<Annotation1>())
        container += annotationSpecOf<Annotation2>()
        assertThat(annotations).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>()
        )
    }

    @Test fun className() {
        val packageName = "com.hendraanggrian.javapoet.dsl.AnnotationSpecContainerTest"
        container.add(packageName.classOf("Annotation1"))
        container += packageName.classOf("Annotation2")
        container { (packageName.classOf("Annotation3")) { } }
        assertThat(annotations).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>(),
            annotationSpecOf<Annotation3>()
        )
    }

    @Test fun javaClass() {
        container.add(Annotation1::class.java)
        container += Annotation2::class.java
        container { Annotation3::class.java { } }
        assertThat(annotations).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>(),
            annotationSpecOf<Annotation3>()
        )
    }

    @Test fun kotlinClass() {
        container.add(Annotation1::class)
        container += Annotation2::class
        container { Annotation3::class { } }
        assertThat(annotations).containsExactly(
            annotationSpecOf<Annotation1>(),
            annotationSpecOf<Annotation2>(),
            annotationSpecOf<Annotation3>()
        )
    }

    @Test fun reifiedType() {
        container.add<Annotation1>()
        assertThat(annotations).containsExactly(annotationSpecOf<Annotation1>())
    }

    annotation class Annotation1
    annotation class Annotation2
    annotation class Annotation3
}