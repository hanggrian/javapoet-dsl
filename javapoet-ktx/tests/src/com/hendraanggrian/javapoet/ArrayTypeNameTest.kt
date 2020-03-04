package com.hendraanggrian.javapoet

import com.example.MyClass
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayTypeNameTest {
    private companion object {
        const val EXPECTED = "com.example.MyClass[]"
    }

    @Test fun test() {
        assertEquals(EXPECTED, "${"com.hendraanggrian.javapoet".classOf("ArrayTypeNameTest", "MyClass").arrayOf()}")
        assertEquals(EXPECTED, "${MyClass::class.java.arrayOf()}")
        assertEquals(EXPECTED, "${MyClass::class.arrayOf()}")
        assertEquals(EXPECTED, "${arrayTypeNameOf<MyClass>()}")
    }
}