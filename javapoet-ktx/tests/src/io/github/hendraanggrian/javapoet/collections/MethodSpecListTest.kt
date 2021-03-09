package io.github.hendraanggrian.javapoet.collections

import com.google.common.truth.Truth.assertThat
import io.github.hendraanggrian.javapoet.emptyConstructorMethodSpec
import io.github.hendraanggrian.javapoet.methodSpecOf
import kotlin.test.Test

class MethodSpecListTest {
    private val list = MethodSpecList(mutableListOf())

    private inline fun container(configuration: MethodSpecListScope.() -> Unit) =
        MethodSpecListScope(list).configuration()

    @Test fun nativeSpec() {
        list += methodSpecOf("method")
        list += listOf(emptyConstructorMethodSpec())
        assertThat(list).containsExactly(
            methodSpecOf("method"),
            emptyConstructorMethodSpec()
        )
    }

    @Test fun string() {
        list.add("method1")
        container { "method2" { } }
        assertThat(list).containsExactly(
            methodSpecOf("method1"),
            methodSpecOf("method2")
        )
    }

    @Test fun others() {
        list.addConstructor()
        assertThat(list).containsExactly(
            emptyConstructorMethodSpec()
        )
    }
}