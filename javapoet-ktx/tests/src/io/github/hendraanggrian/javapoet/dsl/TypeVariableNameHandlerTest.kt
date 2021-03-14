package io.github.hendraanggrian.javapoet.dsl

import com.google.common.truth.Truth
import io.github.hendraanggrian.javapoet.asTypeName
import io.github.hendraanggrian.javapoet.typeVarBy
import io.github.hendraanggrian.javapoet.typeVarOf
import kotlin.test.Test

class TypeVariableNameHandlerTest {
    private val list = TypeVariableNameHandler(mutableListOf())

    @Test fun test() {
        list += "Q"
        list.add("R", String::class.asTypeName())
        list.add("S", String::class.java)
        list.add("T", String::class)
        Truth.assertThat(list).containsExactly(
            "Q".typeVarOf(),
            "R".typeVarBy(String::class.asTypeName()),
            "S".typeVarBy(String::class.java),
            "T".typeVarBy(String::class)
        )
    }
}