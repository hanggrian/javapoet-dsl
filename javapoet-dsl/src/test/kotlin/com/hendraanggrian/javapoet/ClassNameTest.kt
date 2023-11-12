package com.hendraanggrian.javapoet

import kotlin.test.Test
import kotlin.test.assertEquals

class ClassNameTest {
    @Test
    fun staticFields() {
        assertEquals("java.lang.String", "$STRING")
        assertEquals("java.lang.CharSequence", "$CHAR_SEQUENCE")
        assertEquals("java.lang.Comparable", "$COMPARABLE")
        assertEquals("java.lang.Throwable", "$THROWABLE")
        assertEquals("java.lang.Annotation", "$ANNOTATION")
        assertEquals("java.lang.Iterable", "$ITERABLE")
        assertEquals("java.util.Collection", "$COLLECTION")
        assertEquals("java.util.List", "$LIST")
        assertEquals("java.util.Set", "$SET")
        assertEquals("java.util.Map", "$MAP")
    }

    @Test
    fun name() {
        assertEquals("java.lang.String", "${String::class.name}")
    }

    @Test
    fun classNamed() {
        assertEquals("java.lang.String", "${classNamed("java.lang.String")}")
        assertEquals("java.lang.String", "${classNamed("java.lang", "String")}")
    }
}
