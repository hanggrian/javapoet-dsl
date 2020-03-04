package com.hendraanggrian.javapoet

import kotlin.test.Test
import kotlin.test.assertEquals

class ClassNameTest {
    private companion object {
        const val EXPECTED = "com.hendraanggrian.javapoet.ClassNameTest.MyClass"
    }

    @Test fun classOf() {
        assertEquals(EXPECTED, "${"com.hendraanggrian.javapoet".classOf("ClassNameTest", "MyClass")}")
    }

    @Test fun asClassName() {
        assertEquals(EXPECTED, "${MyClass::class.java.asClassName()}")
        assertEquals(EXPECTED, "${MyClass::class.asClassName()}")
        assertEquals(EXPECTED, "${classNameOf<MyClass>()}")
    }

    class MyClass
}