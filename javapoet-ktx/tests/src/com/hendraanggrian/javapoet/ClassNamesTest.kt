package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import kotlin.test.Test
import kotlin.test.assertEquals

class ClassNamesTest {

    @Test fun asClassName() {
        assertEquals(ClassName.get(String::class.java), String::class.java.asClassName())
        assertEquals(ClassName.get(String::class.java), String::class.asClassName())
        assertEquals(ClassName.get(String::class.java), asClassName<String>())
    }
}