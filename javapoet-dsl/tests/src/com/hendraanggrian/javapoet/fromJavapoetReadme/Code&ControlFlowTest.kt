package com.hendraanggrian.javapoet.fromJavapoetReadme

import com.hendraanggrian.javapoet.buildJavaFile
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("ClassName")
class `Code&ControlFlowTest` {

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
                classType("HelloWorld") {
                    methods {
                        "flow" {
                            returns = void
                            addCode(
                                """
                                int total = 0;
                                for (int i = 0; i < 10; i++) {
                                  total += i;
                                }

                            """.trimIndent()
                            )
                        }
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
                classType("HelloWorld") {
                    methods {
                        constructor {

                        }
                        "flow" {
                            returns = void
                            addStatement("int total = 0")
                            beginControlFlow("for (int i = 0; i < 10; i++)")
                            addStatement("total += i")
                            endControlFlow()
                        }
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
                classType("HelloWorld") {
                    methods {
                        "multiply10to20" {
                            returns = int
                            addStatement("int result = 1")
                            beginControlFlow("for (int i = 10; i < 20; i++)")
                            addStatement("result = result * i")
                            endControlFlow()
                            addStatement("return result")
                        }
                    }
                }
            }.toString()
        )
    }
}