package com.hendraanggrian.javapoet.fromJavapoetReadme

import com.hendraanggrian.javapoet.buildJavaFile
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier
import kotlin.test.Test
import kotlin.test.assertEquals

class HelloWorldTest {

    @Test
    fun helloWorld() {
        assertEquals(
            """
                // A boring HelloWorld class
                package com.example;

                import java.lang.String;
                import java.lang.System;

                public final class HelloWorld {
                  public static void main(String[] args) {
                    System.out.println("Hello, JavaPoet!");
                  }
                }

            """.trimIndent(),
            buildJavaFile("com.example") {
                comment = "A boring HelloWorld class"
                addClass("HelloWorld") {
                    addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    methods {
                        "main" {
                            addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                            returns = TypeName.VOID
                            parameters.add<Array<String>>("args")
                            statements.add("\$T.out.println(\$S)", System::class.java, "Hello, JavaPoet!")
                        }
                    }
                }
            }.toString()
        )
    }
}