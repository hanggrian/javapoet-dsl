package com.hanggrian.javapoet

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class TypeNameTest {
    @Test
    fun staticFields() {
        assertThat("$VOID").isEqualTo("void")
        assertThat("$BOOLEAN").isEqualTo("boolean")
        assertThat("$BYTE").isEqualTo("byte")
        assertThat("$SHORT").isEqualTo("short")
        assertThat("$INT").isEqualTo("int")
        assertThat("$LONG").isEqualTo("long")
        assertThat("$CHAR").isEqualTo("char")
        assertThat("$FLOAT").isEqualTo("float")
        assertThat("$DOUBLE").isEqualTo("double")
        assertThat("$OBJECT").isEqualTo("java.lang.Object")
    }
}
