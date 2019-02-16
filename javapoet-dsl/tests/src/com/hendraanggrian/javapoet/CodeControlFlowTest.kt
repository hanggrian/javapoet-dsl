package com.hendraanggrian.javapoet

import org.junit.Test
import kotlin.test.assertEquals

class CodeControlFlowTest {

    private val expected1 =
        """
            package com.example;

            class HelloWorld {
              void flow() {
                int total = 0;
                for (int i = 0; i < 10; i++) {
                  total += i;
                }
              }
            }

        """.trimIndent()

    private val expected2 =
        """
            package com.example;

            class HelloWorld {
              int multiply10to20() {
                int result = 1;
                for (int i = 10; i < 20; i++) {
                  result = result * i;
                }
                return result;
              }
            }

        """.trimIndent()

    @Test
    fun byCode() {
        assertEquals(
            expected1,
            buildJavaFile("com.example") {
                `class`("HelloWorld") {
                    method("flow") {
                        returns = void
                        code(
                            """
                                int total = 0;
                                for (int i = 0; i < 10; i++) {
                                  total += i;
                                }

                            """.trimIndent()
                        )
                    }
                }
            }.toString()
        )
    }

    @Test
    fun byControlFlow() {
        assertEquals(
            expected1,
            buildJavaFile("com.example") {
                `class`("HelloWorld") {
                    method("flow") {
                        returns = void
                        statement("int total = 0")
                        beginControlFlow("for (int i = 0; i < 10; i++)")
                        statement("total += i")
                        endControlFlow()
                    }
                }
            }.toString()
        )
    }

    @Test
    fun computeRange() {
        assertEquals(
            expected2,
            buildJavaFile("com.example") {
                `class`("HelloWorld") {
                    method("multiply10to20") {
                        returns = int
                        statement("int result = 1")
                        beginControlFlow("for (int i = 10; i < 20; i++)")
                        statement("result = result * i")
                        endControlFlow()
                        statement("return result")
                    }
                }
            }.toString()
        )
    }
}