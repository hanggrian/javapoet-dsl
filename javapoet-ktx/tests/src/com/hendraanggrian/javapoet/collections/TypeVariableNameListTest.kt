package com.hendraanggrian.javapoet.collections

import com.google.common.truth.Truth
import com.hendraanggrian.javapoet.asTypeName
import com.hendraanggrian.javapoet.genericsBy
import kotlin.test.Test

class TypeVariableNameListTest {

    private val list = TypeVariableNameList(mutableListOf())

    @Test
    fun add() {
        list.add("Q")
        list.add("R", String::class.asTypeName())
        list.add("S", String::class.java)
        list.add("T", String::class)
        Truth.assertThat(list).containsExactly(
            "Q".genericsBy(),
            "R".genericsBy(String::class.asTypeName()),
            "S".genericsBy(String::class.java),
            "T".genericsBy(String::class)
        )
    }

    @Test
    fun plusAssign() {
        list += "Q"
        Truth.assertThat(list).containsExactly(
            "Q".genericsBy(),
        )
    }
}