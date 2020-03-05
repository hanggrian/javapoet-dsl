package com.hendraanggrian.javapoet

import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterizedTypeNameTest {

    @Test fun parameterizedBy() {
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.Double>",
            "${Pair::class.java.asClassName().parameterizedBy(INT.box(), DOUBLE.box())}"
        )
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.Double>",
            "${Pair::class.java.parameterizedBy(java.lang.Integer::class.java, java.lang.Double::class.java)}"
        )
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.Double>",
            "${Pair::class.parameterizedBy(java.lang.Integer::class, java.lang.Double::class)}"
        )
    }
}