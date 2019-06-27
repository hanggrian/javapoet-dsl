package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import javax.lang.model.element.Modifier
import kotlin.test.Test
import kotlin.test.assertEquals

class FieldSpecBuilderTest {

    @Test
    fun simple() {
        assertEquals(
            FieldSpec.builder(String::class.java, "name", Modifier.PUBLIC, Modifier.FINAL).build(),
            buildFieldSpec<String>("name") {
                modifiers = public + final
            }
        )
    }

    @Test
    fun advanced() {
        assertEquals(
            FieldSpec.builder(String::class.java, "name")
                .addJavadoc("firstJavadoc")
                .addJavadoc(
                    CodeBlock.builder()
                        .add("secondJavadoc")
                        .build()
                )
                .addAnnotation(AnnotationSpec.builder(Deprecated::class.java).build())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .initializer("value")
                .build(),
            buildFieldSpec<String>("name") {
                javadocs {
                    add("firstJavadoc")
                    add {
                        codes.add("secondJavadoc")
                    }
                }
                annotations.add<Deprecated>()
                modifiers = public + final
                initializer = "value"
            }
        )
    }
}