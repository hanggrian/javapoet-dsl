package com.hendraanggrian.javapoet

import kotlin.test.Test
import kotlin.test.assertEquals

class WildcardTypeNameTest {

    @Test
    fun toUpperWildcardTypeName() {
        assertEquals(
            "? extends java.lang.CharSequence",
            "${CharSequence::class.asTypeName().toUpperWildcardTypeName()}"
        )
        assertEquals(
            "? extends java.lang.CharSequence",
            "${CharSequence::class.java.toUpperWildcardTypeName()}"
        )
        assertEquals(
            "? extends java.lang.CharSequence",
            "${CharSequence::class.toUpperWildcardTypeName()}"
        )
    }

    @Test
    fun wildcardTypeNameUpperOf() {
        assertEquals("? extends java.lang.CharSequence", "${wildcardTypeNameUpperOf<CharSequence>()}")
    }

    @Test
    fun toLowerWildcardTypeName() {
        assertEquals(
            "? super java.lang.CharSequence",
            "${CharSequence::class.asTypeName().toLowerWildcardTypeName()}"
        )
        assertEquals(
            "? super java.lang.CharSequence",
            "${CharSequence::class.java.toLowerWildcardTypeName()}"
        )
        assertEquals(
            "? super java.lang.CharSequence",
            "${CharSequence::class.toLowerWildcardTypeName()}"
        )
    }

    @Test
    fun wildcardTypeNameLowerOf() {
        assertEquals("? super java.lang.CharSequence", "${wildcardTypeNameLowerOf<CharSequence>()}")
    }
}