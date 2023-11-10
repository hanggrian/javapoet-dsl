package com.hendraanggrian.javapoet

import kotlin.test.Test
import kotlin.test.assertEquals

class TypeNamesTest {
    @Test
    fun staticFields() {
        assertEquals("void", VOID.toString())
        assertEquals("boolean", BOOLEAN.toString())
        assertEquals("byte", BYTE.toString())
        assertEquals("short", SHORT.toString())
        assertEquals("int", INT.toString())
        assertEquals("long", LONG.toString())
        assertEquals("char", CHAR.toString())
        assertEquals("float", FLOAT.toString())
        assertEquals("double", DOUBLE.toString())
        assertEquals("java.lang.Object", OBJECT.toString())
    }

    @Test
    fun name() {
        assertEquals("java.lang.String", "${String::class.name}")
    }

    @Test
    fun classNamed() {
        assertEquals("java.lang.String", "${classNamed("java.lang.String")}")
        assertEquals("java.lang.String", "${classNamed("java.lang", "String")}")
    }

    @Test
    fun array() {
        assertEquals("java.lang.String[]", "${String::class.name.array}")
    }

    @Test
    fun parameterizedBy() {
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${Pair::class.name.parameterizedBy(INT.box(), String::class.name)}",
        )
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${Pair::class.name.parameterizedBy(java.lang.Integer::class, String::class)}",
        )
        assertEquals(
            "java.util.List<java.lang.String>",
            "${List::class.name.parameterizedBy<String>()}",
        )
    }

    @Test
    fun genericsBy() {
        assertEquals("T", "T".generics.toString())
        assertEquals(
            """
            <T extends java.lang.CharSequence> void go() {
            }

            """.trimIndent(),
            "${
                buildMethodSpec("go") {
                    typeVariables.add("T".genericsBy(CharSequence::class.name))
                }
            }",
        )
        assertEquals(
            """
            <T extends java.lang.CharSequence> void go() {
            }

            """.trimIndent(),
            "${
                buildMethodSpec("go") {
                    typeVariables.add("T".genericsBy(CharSequence::class))
                }
            }",
        )
    }

    @Test
    fun subtype() {
        assertEquals("? extends java.lang.CharSequence", "${CharSequence::class.name.subtype}")
    }

    @Test
    fun supertype() {
        assertEquals("? super java.lang.CharSequence", "${CharSequence::class.name.supertype}")
    }
}
