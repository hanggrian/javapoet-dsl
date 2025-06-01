package com.hanggrian.javapoet

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class TypeVariableNameTest {
    @Test
    fun generics() = assertThat("T".generics.toString()).isEqualTo("T")

    @Test
    fun genericsBy() {
        assertThat(
            "${
                buildMethodSpec("go") {
                    typeVariables.add("T".genericsBy(CHAR_SEQUENCE))
                }
            }",
        ).isEqualTo(
            """
            <T extends java.lang.CharSequence> void go() {
            }

            """.trimIndent(),
        )
        assertThat(
            "${
                buildMethodSpec("go") {
                    typeVariables.add("T".genericsBy(CharSequence::class))
                }
            }",
        ).isEqualTo(
            """
            <T extends java.lang.CharSequence> void go() {
            }

            """.trimIndent(),
        )
    }
}
