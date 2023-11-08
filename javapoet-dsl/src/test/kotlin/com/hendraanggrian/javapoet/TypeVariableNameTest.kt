package com.hendraanggrian.javapoet

import kotlin.test.Test
import kotlin.test.assertEquals

class TypeVariableNameTest {
    @Test
    fun genericsBy() {
        assertEquals("T", "${"T".genericsBy()}")

        assertEquals(
            """
            <T extends java.lang.CharSequence> void go() {
            }

            """.trimIndent(),
            "${
                buildMethodSpec("go") {
                    typeVariables.add("T".genericsBy(CharSequence::class.asClassName()))
                }
            }",
        )
        assertEquals(
            """
            <T extends java.lang.CharSequence> void go() {
            }

            """.trimIndent(),
            "${
                buildMethodSpec("go") {
                    typeVariables.add("T".genericsBy(CharSequence::class.java))
                }
            }",
        )
        assertEquals(
            """
            <T extends java.lang.CharSequence> void go() {
            }

            """.trimIndent(),
            "${buildMethodSpec("go") { typeVariables.add("T".genericsBy(CharSequence::class)) }}",
        )

        assertEquals(
            """
            <T extends java.lang.CharSequence> void go() {
            }

            """.trimIndent(),
            "${
                buildMethodSpec("go") {
                    typeVariables.add("T".genericsBy(listOf(CharSequence::class.asClassName())))
                }
            }",
        )
        assertEquals(
            """
            <T extends java.lang.CharSequence> void go() {
            }

            """.trimIndent(),
            "${
                buildMethodSpec("go") {
                    typeVariables.add("T".genericsBy(listOf(CharSequence::class.java)))
                }
            }",
        )
        assertEquals(
            """
            <T extends java.lang.CharSequence> void go() {
            }

            """.trimIndent(),
            "${
                buildMethodSpec("go") {
                    typeVariables.add("T".genericsBy(listOf(CharSequence::class)))
                }
            }",
        )
    }
}
