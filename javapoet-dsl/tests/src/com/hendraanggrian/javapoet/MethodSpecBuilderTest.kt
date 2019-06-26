package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import org.jetbrains.annotations.NotNull
import java.io.IOException
import javax.lang.model.element.Modifier
import kotlin.test.Test
import kotlin.test.assertEquals

class MethodSpecBuilderTest {

    @Test
    fun simple() {
        assertEquals(
            MethodSpec.constructorBuilder().build(),
            buildConstructorMethodSpec()
        )
    }

    @Test
    fun advanced() {
        buildMethodSpec("asd") {
            annotations {
                Deprecated::class {
                    addMember("asd", "")
                }
                NotNull::class {

                }
                ClassName.OBJECT {

                }
            }
        }

        assertEquals(
            MethodSpec.methodBuilder("main")
                .addJavadoc("firstJavadoc")
                .addJavadoc(
                    CodeBlock.builder()
                        .add("secondJavadoc")
                        .build()
                )
                .addAnnotation(AnnotationSpec.builder(Deprecated::class.java).build())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeName.VOID)
                .addParameter(ParameterSpec.builder(Array<String>::class.java, "param").build())
                .varargs(true)
                .addException(IOException::class.java)
                .addComment("Some comment")
                .addCode("doSomething()")
                .build(),
            buildMethodSpec("main") {
                addJavadoc("firstJavadoc")
                javadoc {
                    addCode("secondJavadoc")
                }
                annotations {
                    Deprecated::class()
                }
                modifiers = public + static
                returns = void
                parameter<Array<String>>("param")
                varargs = true
                exception<IOException>()
                comment("Some comment")
                addCode("doSomething()")
            }
        )
    }
}