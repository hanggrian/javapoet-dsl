package com.hendraanggrian.javapoet

import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterizedTypeNameTest {
    @Test
    fun parameterizedBy() {
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${Pair::class.name.parameterizedBy(INT.box(), STRING)}",
        )
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${Pair::class.name.parameterizedBy(java.lang.Integer::class, String::class)}",
        )
        assertEquals(
            "java.util.List<java.lang.String>",
            "${LIST.parameterizedBy<String>()}",
        )
    }
}
