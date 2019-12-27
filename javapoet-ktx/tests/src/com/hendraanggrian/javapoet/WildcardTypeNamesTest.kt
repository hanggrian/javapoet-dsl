package com.hendraanggrian.javapoet

import com.squareup.javapoet.WildcardTypeName
import kotlin.test.Test
import kotlin.test.assertEquals

class WildcardTypeNamesTest {

    @Test fun asSubtypeWildcardTypeName() {
        assertEquals(
            WildcardTypeName.subtypeOf(asClassName<String>()),
            asClassName<String>().asSubtypeWildcardTypeName()
        )
        assertEquals(
            WildcardTypeName.subtypeOf(String::class.java),
            String::class.java.asSubtypeWildcardTypeName()
        )
        assertEquals(
            WildcardTypeName.subtypeOf(String::class.java),
            String::class.asSubtypeWildcardTypeName()
        )
        assertEquals(
            WildcardTypeName.subtypeOf(String::class.java),
            asSubtypeWildcardTypeName<String>()
        )
    }

    @Test fun asSupertypeWildcardTypeName() {
        assertEquals(
            WildcardTypeName.supertypeOf(asClassName<String>()),
            asClassName<String>().asSupertypeWildcardTypeName()
        )
        assertEquals(
            WildcardTypeName.supertypeOf(String::class.java),
            String::class.java.asSupertypeWildcardTypeName()
        )
        assertEquals(
            WildcardTypeName.supertypeOf(String::class.java),
            String::class.asSupertypeWildcardTypeName()
        )
        assertEquals(
            WildcardTypeName.supertypeOf(String::class.java),
            asSupertypeWildcardTypeName<String>()
        )
    }
}