package com.hanggrian.javapoet

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class ParameterizedTypeNameTest {
    @Test
    fun parameterizedBy() {
        assertThat("${Pair::class.name.parameterizedBy(INT.box(), STRING)}")
            .isEqualTo("kotlin.Pair<java.lang.Integer, java.lang.String>")
        assertThat("${Pair::class.name.parameterizedBy(java.lang.Integer::class, String::class)}")
            .isEqualTo("kotlin.Pair<java.lang.Integer, java.lang.String>")
        assertThat(
            "${
                Pair::class.name.parameterizedBy(
                    java.lang.Integer::class.java,
                    String::class.java,
                )
            }",
        ).isEqualTo("kotlin.Pair<java.lang.Integer, java.lang.String>")
        assertThat("${LIST.parameterizedBy<String>()}")
            .isEqualTo("java.util.List<java.lang.String>")
    }
}
