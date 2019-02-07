package com.hendraanggrian.javapoet

import org.junit.Test
import kotlin.test.assertEquals

class CodeBlockBuilderTest {

    @Test
    fun of() {
        assertEquals("Hello world", buildCodeBlock { "Hello world"() }.toString())
        assertEquals("Hello \"world\"", buildCodeBlock { "Hello \$S"("world") }.toString())
    }
}