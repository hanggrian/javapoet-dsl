package com.hendraanggrian.javapoet.internal

import com.google.common.truth.Truth
import com.hendraanggrian.javapoet.buildClassTypeSpec
import javax.lang.model.element.Modifier
import kotlin.test.Test

class ModifieredSpecBuilderTest {

    @Test
    fun test() {
        buildClassTypeSpec("Strings") {
            fields {
                val single = "single"<String> {
                    modifiers = public
                }
                Truth.assertThat(single.modifiers).containsExactly(Modifier.PUBLIC)
                val multiple = "multiple"<String> {
                    modifiers = public + static + final
                }
                Truth.assertThat(multiple.modifiers).containsExactly(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
            }
        }
    }
}