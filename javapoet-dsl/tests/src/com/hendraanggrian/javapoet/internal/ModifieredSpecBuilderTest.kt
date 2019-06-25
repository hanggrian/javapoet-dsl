package com.hendraanggrian.javapoet.internal

import com.google.common.truth.Truth
import com.hendraanggrian.javapoet.buildFieldSpec
import javax.lang.model.element.Modifier
import kotlin.test.Test

class ModifieredSpecBuilderTest {

    @Test
    fun single() {
        Truth.assertThat(buildFieldSpec<String>("yo") {
            modifiers = public
        }.modifiers).containsExactly(Modifier.PUBLIC)
    }

    @Test
    fun multiple() {
        Truth.assertThat(buildFieldSpec<String>("yo") {
            modifiers = public + static
        }.modifiers).containsExactly(Modifier.PUBLIC, Modifier.STATIC)
    }
}