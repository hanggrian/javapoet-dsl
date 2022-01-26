package com.hendraanggrian.javapoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.codeBlockOf
import com.squareup.javapoet.TypeSpec
import kotlin.test.Test

class EnumConstantMapTest {

    private val map = EnumConstantMap(mutableMapOf())
    private fun map(configuration: EnumConstantMapScope.() -> Unit) = EnumConstantMapScope(map).configuration()

    @Test
    fun add() {
        map.put("FIELD1")
        map.put("FIELD2", "value2")
        map.put("FIELD3", codeBlockOf("value3"))
        assertThat(map).containsExactly(
            "FIELD1", TypeSpec.anonymousClassBuilder("").build(),
            "FIELD2", TypeSpec.anonymousClassBuilder("value2").build(),
            "FIELD3", TypeSpec.anonymousClassBuilder("value3").build()
        )
    }

    @Test
    fun set() {
        map["FIELD1"] = "value1"
        map["FIELD2"] = codeBlockOf("value2")
        assertThat(map).containsExactly(
            "FIELD1", TypeSpec.anonymousClassBuilder("value1").build(),
            "FIELD2", TypeSpec.anonymousClassBuilder("value2").build()
        )
    }

    @Test
    fun invoke() {
        map {
            "FIELD1"("value1") { }
            "FIELD2"(codeBlockOf("value2")) { }
        }
        assertThat(map).containsExactly(
            "FIELD1", TypeSpec.anonymousClassBuilder("value1").build(),
            "FIELD2", TypeSpec.anonymousClassBuilder("value2").build()
        )
    }
}