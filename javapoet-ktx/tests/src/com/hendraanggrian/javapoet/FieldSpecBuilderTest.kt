package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
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
        .addModifiers(PUBLIC, FINAL)
        .initializer("value")
        .build()

    @Test
    fun simple() {
        assertEquals(expected, buildFieldSpec<String>("name") {
            javadoc.append("firstJavadoc")
            javadoc.append {
                append("secondJavadoc")
            }
            annotations.add<Deprecated>()
            addModifiers(PUBLIC, FINAL)
            initializer("value")
        })
    }

    @Test
    fun invocation() {
        assertEquals(expected, buildFieldSpec<String>("name") {
            javadoc {
                append("firstJavadoc")
                append {
                    append("secondJavadoc")
                }
            }
            annotations {
                add<Deprecated>()
            }
            addModifiers(PUBLIC, FINAL)
            initializer("value")
        })
    }
}