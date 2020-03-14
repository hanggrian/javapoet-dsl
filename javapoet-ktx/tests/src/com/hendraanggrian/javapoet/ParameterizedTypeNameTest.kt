package com.hendraanggrian.javapoet

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class ParameterizedTypeNameTest {

    @Test fun classNameReceiver() {
        val className = Pair::class.asClassName()
        assertFails { className.parameterizedBy() }
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${className.parameterizedBy(INT.box(), String::class.asTypeName())}"
        )
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${className.parameterizedBy(java.lang.Integer::class.java, String::class.java)}"
        )
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${className.parameterizedBy(java.lang.Integer::class, String::class)}"
        )
    }

    @Test fun classReceiver() {
        val `class` = Pair::class.java
        assertFails { `class`.parameterizedBy() }
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${`class`.parameterizedBy(INT.box(), String::class.asTypeName())}"
        )
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${`class`.parameterizedBy(java.lang.Integer::class.java, String::class.java)}"
        )
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${`class`.parameterizedBy(java.lang.Integer::class, String::class)}"
        )
    }

    @Test fun kclassReceiver() {
        val kclass = Pair::class
        assertFails { kclass.parameterizedBy() }
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${kclass.parameterizedBy(INT.box(), String::class.asTypeName())}"
        )
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${kclass.parameterizedBy(java.lang.Integer::class.java, String::class.java)}"
        )
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${kclass.parameterizedBy(java.lang.Integer::class, String::class)}"
        )
    }
}