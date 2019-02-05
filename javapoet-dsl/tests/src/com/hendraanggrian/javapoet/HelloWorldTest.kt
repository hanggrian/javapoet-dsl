package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeName
import org.junit.Test
import javax.lang.model.element.Modifier
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
                type("HelloWorld") {
                    modifiers(Modifier.PUBLIC, Modifier.FINAL)
                    method("main") {
                        modifiers(Modifier.PUBLIC, Modifier.STATIC)
                        returns = TypeName.VOID
                        parameter(Array<String>::class.java, "args")
                        statement("\$T.out.println(\$S)", System::class.java, "Hello, JavaPoet!")
                    }
                }
            }.toString()
        )
    }
}