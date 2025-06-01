package com.hanggrian.javapoet

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class WildcardTypeNameTest {
    @Test
    fun subtype() =
        assertThat("${CHAR_SEQUENCE.subtype}").isEqualTo("? extends java.lang.CharSequence")

    @Test
    fun supertype() =
        assertThat("${CHAR_SEQUENCE.supertype}").isEqualTo("? super java.lang.CharSequence")
}
