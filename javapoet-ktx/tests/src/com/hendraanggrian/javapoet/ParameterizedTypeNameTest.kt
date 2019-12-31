package com.hendraanggrian.javapoet

import com.squareup.javapoet.ParameterizedTypeName
import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterizedTypeNameTest {

    @Test fun parameterizedBy() {
        assertEquals(
            ParameterizedTypeName.get(List::class.asClassName(), OBJECT),
            List::class.asClassName().parameterizedBy(OBJECT)
        )
        assertEquals(
            ParameterizedTypeName.get(List::class.java, String::class.java),
            List::class.java.parameterizedBy(String::class.java)
        )
        assertEquals(
            ParameterizedTypeName.get(List::class.java, String::class.java),
            List::class.parameterizedBy(String::class)
        )
        assertEquals(
            ParameterizedTypeName.get(List::class.java, String::class.java),
            List::class.parameterizedBy<String>()
        )
    }
}