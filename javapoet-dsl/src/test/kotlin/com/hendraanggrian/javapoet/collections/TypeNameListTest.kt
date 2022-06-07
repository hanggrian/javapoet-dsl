package com.hendraanggrian.javapoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.asTypeName
import com.squareup.javapoet.TypeName
import kotlin.test.Test

class TypeNameListTest {
    private val list = TypeNameList(mutableListOf())

    @Test
    fun test() {
        list += TypeName.CHAR
        list += Double::class.java
        list += Boolean::class
        list.add<String>()
        assertThat(list).containsExactly(
            TypeName.CHAR,
            Double::class.java.asTypeName(),
            Boolean::class.asTypeName(),
            String::class.asTypeName()
        )
    }
}
