package com.hendraanggrian.javapoet

import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterizedTypeNameTest {

    @Test
    fun parameterizedBy() {
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${Pair::class.asClassName().parameterizedBy(INT.box(), String::class.asTypeName())}"
        )
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${Pair::class.java.parameterizedBy(java.lang.Integer::class.java, String::class.java)}"
        )
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${Pair::class.parameterizedBy(java.lang.Integer::class, String::class)}"
        )

        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${
            Pair::class.asClassName()
                .parameterizedBy(listOf(INT.box(), String::class.asTypeName()))
            }"
        )
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${
            Pair::class.java.parameterizedBy(
                listOf(java.lang.Integer::class.java, String::class.java)
            )
            }"
        )
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${Pair::class.parameterizedBy(listOf(java.lang.Integer::class, String::class))}"
        )
    }

    @Test
    fun plusParameter() {
        assertEquals(
            "java.util.List<java.lang.String>",
            "${List::class.asClassName().plusParameter<String>()}"
        )
        assertEquals(
            "java.util.List<java.lang.String>",
            "${List::class.java.plusParameter<String>()}"
        )
        assertEquals(
            "java.util.List<java.lang.String>",
            "${List::class.plusParameter<String>()}"
        )
    }
}
