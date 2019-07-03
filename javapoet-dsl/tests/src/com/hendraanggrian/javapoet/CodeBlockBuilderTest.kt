package com.hendraanggrian.javapoet

import com.squareup.javapoet.CodeBlock
import kotlin.test.Test
import kotlin.test.assertEquals

class CodeBlockBuilderTest {
    private val getBuilder = { CodeBlock.builder() }
    private val expected = getBuilder()
        .addStatement("int total = 0")
        .beginControlFlow("for (int i = 0; i < 10; i++)")
        .addStatement("total += i")
        .endControlFlow()
        .build()

    @Test
    fun simple() {
        assertEquals(expected, (getBuilder()) {
            statements.add("int total = 0")
            beginControlFlow("for (int i = 0; i < 10; i++)")
            statements.add("total += i")
            endControlFlow()
        })
    }

    @Test
    fun invocation() {
        assertEquals(expected, (getBuilder()) {
            statements {
                add("int total = 0")
                beginControlFlow("for (int i = 0; i < 10; i++)")
                add("total += i")
                endControlFlow()
            }
        })
    }
}