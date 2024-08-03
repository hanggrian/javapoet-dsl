package com.hanggrian.javapoet

import kotlin.test.Test
import kotlin.test.assertEquals

class TypeNameTest {
    @Test
    fun staticFields() {
        assertEquals("void", "$VOID")
        assertEquals("boolean", "$BOOLEAN")
        assertEquals("byte", "$BYTE")
        assertEquals("short", "$SHORT")
        assertEquals("int", "$INT")
        assertEquals("long", "$LONG")
        assertEquals("char", "$CHAR")
        assertEquals("float", "$FLOAT")
        assertEquals("double", "$DOUBLE")
        assertEquals("java.lang.Object", "$OBJECT")
    }
}
