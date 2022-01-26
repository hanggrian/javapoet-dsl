package com.hendraanggrian.javapoet

import kotlin.test.Test
import kotlin.test.assertEquals

class ClassNameTest {

    @Test
    fun classNameOf() {
        assertEquals("java.lang.String", "${classNameOf("java.lang.String")}")
        assertEquals("java.lang.String", "${classNameOf("java.lang", "String")}")
        assertEquals("java.lang.String", "${classNameOf<String>()}")
    }

    @Test
    fun asClassName() {
        assertEquals("java.lang.String", "${String::class.java.asClassName()}")
        assertEquals("java.lang.String", "${String::class.asClassName()}")
    }
}