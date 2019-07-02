package com.hendraanggrian.javapoet.fromJavapoetReadme

import com.hendraanggrian.javapoet.buildJavaFile
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
                    modifiers = public + final
                    methods {
                        "main" {
                            modifiers = public + static
                            returns = void
                            parameters.add<Array<String>>("args")
                            statements.add("\$T.out.println(\$S)", System::class.java, "Hello, JavaPoet!")
                        }
                    }
                }
            }.toString()
        )
    }
}