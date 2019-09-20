package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeName
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeNamesTest {

    @Test
    fun staticFields() {
        assertEquals(TypeName.VOID, VOID)
        assertEquals(TypeName.BOOLEAN, BOOLEAN)
        assertEquals(TypeName.BYTE, BYTE)
        assertEquals(TypeName.SHORT, SHORT)
        assertEquals(TypeName.INT, INT)
        assertEquals(TypeName.LONG, LONG)
        assertEquals(TypeName.CHAR, CHAR)
        assertEquals(TypeName.FLOAT, FLOAT)
        assertEquals(TypeName.DOUBLE, DOUBLE)
        assertEquals(TypeName.OBJECT, OBJECT)
    }

    @Test
    fun test() {
        assertEquals(TypeName.get(String::class.java), asTypeName<String>())
    }
}