package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.assertEqualsAll
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterizedTypeNameTest {
    private val className = Pair::class.asClassName()
    private val `class` = Pair::class.java
    private val kclass = Pair::class

    @Test
    fun vararg() {
        assertEqualsAll(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${className.parameterizedBy(INT.box(), String::class.asTypeName())}",
            "${`class`.parameterizedBy(INT.box(), String::class.asTypeName())}",
            "${kclass.parameterizedBy(INT.box(), String::class.asTypeName())}"
        )

        assertEqualsAll(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${className.parameterizedBy(java.lang.Integer::class.java, String::class.java)}",
            "${`class`.parameterizedBy(java.lang.Integer::class.java, String::class.java)}",
            "${kclass.parameterizedBy(java.lang.Integer::class.java, String::class.java)}"
        )

        assertEqualsAll(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${className.parameterizedBy(java.lang.Integer::class, String::class)}",
            "${`class`.parameterizedBy(java.lang.Integer::class, String::class)}",
            "${kclass.parameterizedBy(java.lang.Integer::class, String::class)}"
        )
    }

    @Test
    fun list() {
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${className.parameterizedBy(listOf(INT.box(), String::class.asTypeName()))}"
        )

        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${`class`.parameterizedBy(listOf(java.lang.Integer::class.java, String::class.java))}"
        )

        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${kclass.parameterizedBy(listOf(java.lang.Integer::class, String::class))}"
        )
    }
}