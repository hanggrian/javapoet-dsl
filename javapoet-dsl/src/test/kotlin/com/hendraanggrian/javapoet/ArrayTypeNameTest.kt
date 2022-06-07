package com.hendraanggrian.javapoet

import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayTypeNameTest {

    @Test
    fun toArrayTypeName() {
        assertEquals("java.lang.String[]", "${String::class.asTypeName().toArrayTypeName()}")
        assertEquals("java.lang.String[]", "${String::class.java.toArrayTypeName()}")
        assertEquals("java.lang.String[]", "${String::class.toArrayTypeName()}")
    }

    @Test
    fun arrayTypeNameOf() {
        assertEquals("java.lang.String[]", "${arrayTypeNameOf<String>()}")
    }
}
