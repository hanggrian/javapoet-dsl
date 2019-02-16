package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.buildFieldSpec
import org.junit.Test
import kotlin.test.assertEquals

class ModifieredSpecBuilderTest {

    @Test
    fun single() {
        assertEquals(1, buildFieldSpec<String>("yo") {
            modifiers = public
        }.modifiers.size)
    }

    @Test
    fun multiple() {
        assertEquals(2, buildFieldSpec<String>("yo") {
            modifiers = public + static
        }.modifiers.size)
    }
}