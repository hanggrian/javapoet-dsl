package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ParameterSpec
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterSpecBuilderTest {
    private val expected = ParameterSpec.builder(String::class.java, "name")
        .addAnnotation(AnnotationSpec.builder(Deprecated::class.java).build())
        .addModifiers(public, final)
        .build()

    @Test
    fun simple() {
        assertEquals(expected, buildParameter<String>("name") {
            annotations.add<Deprecated>()
            addModifiers(public, final)
        })
    }

    @Test
    fun invocation() {
        assertEquals(expected, buildParameter<String>("name") {
            annotations {
                add<Deprecated>()
            }
            addModifiers(public, final)
        })
    }
}