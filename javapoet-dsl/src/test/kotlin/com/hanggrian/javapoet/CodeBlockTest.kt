package com.hanggrian.javapoet

import com.google.common.truth.Truth.assertThat
import com.squareup.javapoet.CodeBlock
import kotlin.test.Test
import kotlin.test.assertFails

class CodeBlockTest {
    @Test
    fun isEmpty() {
        buildCodeBlock {
            assertThat(isEmpty()).isTrue()
            append("text")
            assertThat(isEmpty()).isFalse()
        }
    }

    @Test
    fun isNotEmpty() {
        buildCodeBlock {
            assertThat(isNotEmpty()).isFalse()
            append("text")
            assertThat(isNotEmpty()).isTrue()
        }
    }

    @Test
    fun controlFlow() =
        assertThat(
            buildCodeBlock {
                beginControlFlow("format", "arg")
                nextControlFlow("format", "arg")
                endControlFlow("format", "arg")
            },
        ).isEqualTo(
            CodeBlock
                .builder()
                .beginControlFlow("format", "arg")
                .nextControlFlow("format", "arg")
                .endControlFlow("format", "arg")
                .build(),
        )

    @Test
    fun append() =
        assertThat(
            buildCodeBlock {
                append("text")
                append(codeBlockOf("some code"))
            },
        ).isEqualTo(
            CodeBlock
                .builder()
                .add("text")
                .add(CodeBlock.of("some code"))
                .build(),
        )

    @Test
    fun appendLine() =
        assertThat(
            buildCodeBlock {
                appendLine()
                appendLine("text")
                appendLine(codeBlockOf("some code"))
            },
        ).isEqualTo(
            CodeBlock
                .builder()
                .addStatement("")
                .addStatement("text")
                .addStatement(CodeBlock.of("some code"))
                .build(),
        )

    @Test
    fun clear() {
        buildCodeBlock {
            assertThat(isNotEmpty()).isFalse()
            append("text")
            clear()
            assertThat(isEmpty()).isTrue()
        }
    }

    @Test
    fun `Simple example`() =
        assertThat(
            buildCodeBlock {
                appendLine("int total = 0")
                beginControlFlow("for (int i = 0; i < 10; i++)")
                appendLine("total += i")
                endControlFlow()
            },
        ).isEqualTo(
            CodeBlock
                .builder()
                .addStatement("int total = 0")
                .beginControlFlow("for (int i = 0; i < 10; i++)")
                .addStatement("total += i")
                .endControlFlow()
                .build(),
        )

    @Test
    fun `Escape special characters`() {
        assertFails { codeBlockOf("100$") }
        assertThat("${codeBlockOf("100$$")}").isEqualTo("100$")
        assertThat("${codeBlockOf("100$\$S")}").isEqualTo("100\$S")
        assertThat("${codeBlockOf("100$\$S$$\$T", System::class)}")
            .isEqualTo("100\$S\$java.lang.System")

        assertFails { codeBlockOf("100%") }
        assertThat("${codeBlockOf("100%%")}").isEqualTo("100%")
        assertThat("${codeBlockOf("100%%S")}").isEqualTo("100%S")
        assertThat("${codeBlockOf("100%%S%%%T", System::class)}")
            .isEqualTo("100%S%java.lang.System")
    }
}
