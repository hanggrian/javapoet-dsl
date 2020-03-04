package com.hendraanggrian.javapoet

import kotlin.test.Test
import kotlin.test.assertEquals

class TypeVariableNameTest {

    @Test fun test() {
        println("T".typeVariableBy(CharSequence::class))
        assertEquals("T", "${"T".typeVariableBy()}")
    }
}