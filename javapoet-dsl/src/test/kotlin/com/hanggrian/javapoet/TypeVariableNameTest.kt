package com.hanggrian.javapoet

import kotlin.test.Test
import kotlin.test.assertEquals

class TypeVariableNameTest {
    @Test
    fun generics() {
        assertEquals("T", "T".generics.toString())
    }

    @Test
    fun genericsBy() {
        assertEquals(
            """
            <T extends java.lang.CharSequence> void go() {
            }

            """.trimIndent(),
            "${
                buildMethodSpec("go") {
                    typeVariables.add("T".genericsBy(CHAR_SEQUENCE))
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
                    typeVariables.add("T".genericsBy(CharSequence::class))
                }
            }",
        )
    }
}
