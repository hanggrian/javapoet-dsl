package io.github.hendraanggrian.javapoet

import com.squareup.javapoet.TypeName
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeNameTest {

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
    fun asTypeName() {
        assertEquals("java.lang.String", "${String::class.java.asTypeName()}")
        assertEquals("java.lang.String", "${String::class.asTypeName()}")
        assertEquals("java.lang.String", "${typeNameOf<String>()}")
    }
}