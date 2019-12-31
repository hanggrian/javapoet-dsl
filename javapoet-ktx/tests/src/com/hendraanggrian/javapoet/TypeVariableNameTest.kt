package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeVariableName
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeVariableNameTest {

    @Test fun typeVariableNameOf() {
        assertEquals(
            TypeVariableName.get("Hello"),
            typeVariableNameOf("Hello")
        )
        assertEquals(
            TypeVariableName.get("Hello", asClassName<String>()),
            typeVariableNameOf("Hello", asClassName<String>())
        )
        assertEquals(
            TypeVariableName.get("Hello", String::class.java),
            typeVariableNameOf("Hello", String::class.java)
        )
        assertEquals(
            TypeVariableName.get("Hello", String::class.java),
            typeVariableNameOf("Hello", String::class)
        )
    }
}