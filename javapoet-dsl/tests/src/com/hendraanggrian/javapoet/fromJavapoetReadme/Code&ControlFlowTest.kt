package com.hendraanggrian.javapoet.fromJavapoetReadme

import com.hendraanggrian.javapoet.buildJavaFile
import com.hendraanggrian.javapoet.dsl.invoke
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
                addClass("HelloWorld") {
                    methods {
                        "flow" {
                            returns = void
                            codes.add(
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
                addClass("HelloWorld") {
                    methods {
                        "flow" {
                            returns = void
                            statements.add("int total = 0")
                            beginControlFlow("for (int i = 0; i < 10; i++)")
                            statements.add("total += i")
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
                addClass("HelloWorld") {
                    methods {
                        "multiply10to20" {
                            returns = int
                            statements.add("int result = 1")
                            beginControlFlow("for (int i = 10; i < 20; i++)")
                            statements.add("result = result * i")
                            endControlFlow()
                            statements.add("return result")
                        }
                    }
                }
            }.toString()
        )
    }
}