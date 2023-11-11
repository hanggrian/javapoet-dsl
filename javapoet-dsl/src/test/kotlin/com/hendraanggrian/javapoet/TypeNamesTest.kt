package com.hendraanggrian.javapoet

import kotlin.test.Test
import kotlin.test.assertEquals

class TypeNamesTest {
    @Test
    fun staticFields() {
        assertEquals("void", "$VOID")
        assertEquals("boolean", "$BOOLEAN")
        assertEquals("byte", "$BYTE")
        assertEquals("short", "$SHORT")
        assertEquals("int", "$INT")
        assertEquals("long", "$LONG")
        assertEquals("char", "$CHAR")
        assertEquals("float", "$FLOAT")
        assertEquals("double", "$DOUBLE")
        assertEquals("java.lang.Object", "$OBJECT")

        assertEquals("java.lang.String", "$STRING")
        assertEquals("java.lang.CharSequence", "$CHAR_SEQUENCE")
        assertEquals("java.lang.Comparable", "$COMPARABLE")
        assertEquals("java.lang.Throwable", "$THROWABLE")
        assertEquals("java.lang.Annotation", "$ANNOTATION")
        assertEquals("java.lang.Iterable", "$ITERABLE")
        assertEquals("java.util.Collection", "$COLLECTION")
        assertEquals("java.util.List", "$LIST")
        assertEquals("java.util.Set", "$SET")
        assertEquals("java.util.Map", "$MAP")
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
        assertEquals("java.lang.String[]", "${STRING.array}")
    }

    @Test
    fun parameterizedBy() {
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${Pair::class.name.parameterizedBy(INT.box(), STRING)}",
        )
        assertEquals(
            "kotlin.Pair<java.lang.Integer, java.lang.String>",
            "${Pair::class.name.parameterizedBy(java.lang.Integer::class, String::class)}",
        )
        assertEquals(
            "java.util.List<java.lang.String>",
            "${LIST.parameterizedBy<String>()}",
        )
    }

    @Test
    fun generics() {
        assertEquals("T", "T".generics.toString())
    }

    @Test
    fun genericsBy() {
        assertEquals(
            """
            <T extends java.lang.CharSequence> void go() {
            }

            """.trimIndent(),
            "${
                buildMethodSpec("go") {
                    typeVariables.add("T".genericsBy(CHAR_SEQUENCE))
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
        assertEquals("? extends java.lang.CharSequence", "${CHAR_SEQUENCE.subtype}")
    }

    @Test
    fun supertype() {
        assertEquals("? super java.lang.CharSequence", "${CHAR_SEQUENCE.supertype}")
    }
}
