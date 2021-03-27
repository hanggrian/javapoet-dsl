package io.github.hendraanggrian.javapoet

import com.example.MyClass
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrayTypeNameTest {
    private companion object {
        const val EXPECTED = "com.example.MyClass[]"
    }

    @Test
    fun arrayOf() {
        assertEquals(EXPECTED, "${"com.example".arrayOf("MyClass")}")
        assertEquals(EXPECTED, "${"com.example".classOf("MyClass").arrayOf()}")
        assertEquals(EXPECTED, "${MyClass::class.java.arrayOf()}")
        assertEquals(EXPECTED, "${MyClass::class.arrayOf()}")
    }
}