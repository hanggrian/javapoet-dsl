package com.hendraanggrian.javapoet

import com.squareup.javapoet.ParameterizedTypeName
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterizedTypeNamesTest {

    @Test
    fun parameterizedBy() {
        assertEquals(
            ParameterizedTypeName.get(List::class.java.asClassName(), OBJECT),
            List::class.java.asClassName().parameterizedBy(OBJECT)
        )
        assertEquals(
            ParameterizedTypeName.get(List::class.java, String::class.java),
            List::class.java.parameterizedBy(String::class.java)
        )
        assertEquals(
            ParameterizedTypeName.get(List::class.java, String::class.java),
            List::class.parameterizedBy(String::class)
        )
    }
}