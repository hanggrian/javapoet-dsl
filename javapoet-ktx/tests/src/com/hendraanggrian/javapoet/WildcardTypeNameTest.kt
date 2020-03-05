package com.hendraanggrian.javapoet

import com.example.MyClass
import kotlin.test.Test
import kotlin.test.assertEquals

class WildcardTypeNameTest {
    private companion object {
        const val EXPECTED_SUBTYPE = "? extends com.example.MyClass"
        const val EXPECTED_SUPERTYPE = "? super com.example.MyClass"
    }

    @Test fun subtype() {
        assertEquals(EXPECTED_SUBTYPE, "${MyClass::class.asTypeName().subtypeOf()}")
        assertEquals(EXPECTED_SUBTYPE, "${MyClass::class.java.subtypeOf()}")
        assertEquals(EXPECTED_SUBTYPE, "${MyClass::class.subtypeOf()}")
    }

    @Test fun supertype() {
        assertEquals(EXPECTED_SUPERTYPE, "${MyClass::class.asTypeName().supertypeOf()}")
        assertEquals(EXPECTED_SUPERTYPE, "${MyClass::class.java.supertypeOf()}")
        assertEquals(EXPECTED_SUPERTYPE, "${MyClass::class.supertypeOf()}")
    }
}