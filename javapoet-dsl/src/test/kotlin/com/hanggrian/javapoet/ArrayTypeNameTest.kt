package com.hanggrian.javapoet

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class ArrayTypeNameTest {
    @Test
    fun array() = assertThat("${STRING.array}").isEqualTo("java.lang.String[]")
}
