package io.github.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ParameterSpec
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterSpecBuilderTest {
    private val expected = ParameterSpec.builder(String::class.java, "name")
        .addAnnotation(AnnotationSpec.builder(Deprecated::class.java).build())
        .addModifiers(PUBLIC, FINAL)
        .build()

    @Test
    fun simple() {
        assertEquals(expected, buildParameterSpec<String>("name") {
            annotations.add<Deprecated>()
            addModifiers(PUBLIC, FINAL)
        })
    }

    @Test
    fun invocation() {
        assertEquals(expected, buildParameterSpec<String>("name") {
            annotations {
                add<Deprecated>()
            }
            addModifiers(PUBLIC, FINAL)
        })
    }
}