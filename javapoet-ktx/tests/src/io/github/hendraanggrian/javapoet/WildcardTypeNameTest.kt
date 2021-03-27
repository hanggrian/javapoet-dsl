package io.github.hendraanggrian.javapoet

import com.example.MyClass
import kotlin.test.Test
import kotlin.test.assertEquals

class WildcardTypeNameTest {
    private companion object {
        const val EXPECTED_SUBTYPE = "? extends com.example.MyClass"
        const val EXPECTED_SUPERTYPE = "? super com.example.MyClass"
    }

    @Test
    fun subtype() {
        assertEquals(EXPECTED_SUBTYPE, "${MyClass::class.asTypeName().wildcardSubtypeOf()}")
        assertEquals(EXPECTED_SUBTYPE, "${MyClass::class.java.wildcardSubtypeOf()}")
        assertEquals(EXPECTED_SUBTYPE, "${MyClass::class.wildcardSubtypeOf()}")
    }

    @Test
    fun supertype() {
        assertEquals(EXPECTED_SUPERTYPE, "${MyClass::class.asTypeName().wildcardSupertypeOf()}")
        assertEquals(EXPECTED_SUPERTYPE, "${MyClass::class.java.wildcardSupertypeOf()}")
        assertEquals(EXPECTED_SUPERTYPE, "${MyClass::class.wildcardSupertypeOf()}")
    }
}