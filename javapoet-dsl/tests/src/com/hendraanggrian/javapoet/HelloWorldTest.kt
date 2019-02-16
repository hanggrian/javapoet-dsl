package com.hendraanggrian.javapoet

import org.junit.Test
import kotlin.test.assertEquals

class HelloWorldTest {

    @Test
    fun helloWorld() {
        assertEquals(
            """
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
                `class`("HelloWorld") {
                    modifiers = public + final
                    method("main") {
                        modifiers = public + static
                        returns = void
                        parameter<Array<String>>("args")
                        statement("\$T.out.println(\$S)", System::class.java, "Hello, JavaPoet!")
                    }
                }
            }.toString()
        )
    }
}