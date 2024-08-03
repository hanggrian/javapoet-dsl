package com.hanggrian.javapoet

import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayTypeNameTest {
    @Test
    fun array() {
        assertEquals("java.lang.String[]", "${STRING.array}")
    }
}
