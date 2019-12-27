package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import java.io.IOException
import javax.lang.model.element.Modifier
import kotlin.test.Test
import kotlin.test.assertEquals

class MethodSpecBuilderTest {
    private val expected = MethodSpec.methodBuilder("main")
        .addJavadoc("firstJavadoc")
        .addJavadoc(
            CodeBlock.builder()
                .add("secondJavadoc")
                .build()
        )
        .addAnnotation(AnnotationSpec.builder(Deprecated::class.java).build())
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
        .returns(VOID)
        .addParameter(ParameterSpec.builder(Array<String>::class.java, "param").build())
        .varargs(true)
        .addException(IOException::class.java)
        .addComment("Some comment")
        .addCode("doSomething()")
        .build()

    @Test fun simple() {
        assertEquals(expected, buildMethod("main") {
            javadoc.append("firstJavadoc")
            javadoc.append {
                append("secondJavadoc")
            }
            annotations.add<Deprecated>()
            addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            returns = VOID
            parameters.add<Array<String>>("param")
            varargs = true
            addException<IOException>()
            addComment("Some comment")
            append("doSomething()")
        })
    }

    @Test fun invocation() {
        assertEquals(expected, buildMethod("main") {
            javadoc {
                append("firstJavadoc")
                append {
                    append("secondJavadoc")
                }
            }
            annotations {
                add<Deprecated>()
            }
            addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            returns = VOID
            parameters {
                add<Array<String>>("param")
            }
            varargs = true
            addException<IOException>()
            addComment("Some comment")
            append("doSomething()")
        })
    }
}