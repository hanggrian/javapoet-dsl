package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.add
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ParameterSpec
import javax.lang.model.element.Modifier
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterSpecBuilderTest {

    @Test
    fun simple() {
        assertEquals(
            ParameterSpec.builder(String::class.java, "name", Modifier.PUBLIC, Modifier.FINAL).build(),
            ParameterSpecBuilder.of(String::class, "name") {
                modifiers = public + final
            }
        )
    }

    @Test
    fun advanced() {
        assertEquals(
            ParameterSpec.builder(String::class.java, "name")
                .addAnnotation(AnnotationSpec.builder(Deprecated::class.java).build())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .build(),
            ParameterSpecBuilder.of(String::class, "name") {
                annotations.add<Deprecated>()
                modifiers = public + final
            }
        )
    }
}