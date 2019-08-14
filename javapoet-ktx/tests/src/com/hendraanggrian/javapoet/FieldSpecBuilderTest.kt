package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import javax.lang.model.element.Modifier
import kotlin.test.Test
import kotlin.test.assertEquals

class FieldSpecBuilderTest {
    private val expected = FieldSpec.builder(String::class.java, "name")
        .addJavadoc("firstJavadoc")
        .addJavadoc(
            CodeBlock.builder()
                .add("secondJavadoc")
                .build()
        )
        .addAnnotation(AnnotationSpec.builder(Deprecated::class.java).build())
        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
        .initializer("value")
        .build()

    @Test
    fun simple() {
        assertEquals(expected, FieldSpecs.of<String>("name") {
            javadoc.add("firstJavadoc")
            javadoc.add {
                append("secondJavadoc")
            }
            annotations.add<Deprecated>()
            addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            initializer = "value"
        })
    }

    @Test
    fun invocation() {
        assertEquals(expected, FieldSpecs.of<String>("name") {
            javadoc {
                add("firstJavadoc")
                add {
                    append("secondJavadoc")
                }
            }
            annotations {
                add<Deprecated>()
            }
            addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            initializer = "value"
        })
    }
}