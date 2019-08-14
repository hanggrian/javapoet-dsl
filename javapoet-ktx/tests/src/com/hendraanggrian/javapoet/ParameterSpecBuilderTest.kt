package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ParameterSpec
import javax.lang.model.element.Modifier
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterSpecBuilderTest {
    private val getBuilder = { ParameterSpec.builder(String::class.java, "name") }
    private val expected = getBuilder()
        .addAnnotation(AnnotationSpec.builder(Deprecated::class.java).build())
        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        .build()

    @Test
    fun simple() {
        assertEquals(expected, (getBuilder()) {
            annotations.add<Deprecated>()
            addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        })
    }

    @Test
    fun invocation() {
        assertEquals(expected, (getBuilder()) {
            annotations {
                add<Deprecated>()
            }
            addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        })
    }
}