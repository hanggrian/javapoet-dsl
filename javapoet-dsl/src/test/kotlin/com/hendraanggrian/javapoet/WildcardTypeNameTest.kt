package com.hendraanggrian.javapoet

import kotlin.test.Test
import kotlin.test.assertEquals

class WildcardTypeNameTest {
    @Test
    fun subtype() {
        assertEquals("? extends java.lang.CharSequence", "${CHAR_SEQUENCE.subtype}")
    }

    @Test
    fun supertype() {
        assertEquals("? super java.lang.CharSequence", "${CHAR_SEQUENCE.supertype}")
    }
}
