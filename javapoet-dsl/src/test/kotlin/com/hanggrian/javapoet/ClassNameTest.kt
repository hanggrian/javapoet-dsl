package com.hanggrian.javapoet

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class ClassNameTest {
    @Test
    fun staticFields() {
        assertThat("$STRING").isEqualTo("java.lang.String")
        assertThat("$CHAR_SEQUENCE").isEqualTo("java.lang.CharSequence")
        assertThat("$COMPARABLE").isEqualTo("java.lang.Comparable")
        assertThat("$THROWABLE").isEqualTo("java.lang.Throwable")
        assertThat("$ANNOTATION").isEqualTo("java.lang.Annotation")
        assertThat("$ITERABLE").isEqualTo("java.lang.Iterable")
        assertThat("$COLLECTION").isEqualTo("java.util.Collection")
        assertThat("$LIST").isEqualTo("java.util.List")
        assertThat("$SET").isEqualTo("java.util.Set")
        assertThat("$MAP").isEqualTo("java.util.Map")
    }

    @Test
    fun name() = assertThat("${String::class.name}").isEqualTo("java.lang.String")

    @Test
    fun classNamed() {
        assertThat("${classNamed("java.lang.String")}").isEqualTo("java.lang.String")
        assertThat("${classNamed("java.lang", "String")}").isEqualTo("java.lang.String")
    }
}
