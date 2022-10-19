package com.hendraanggrian.javapoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.codeBlockOf
import com.squareup.javapoet.TypeSpec
import kotlin.test.Test

class EnumConstantMapTest {
    private val map = EnumConstantMap(mutableMapOf())
    private fun map(configuration: EnumConstantMapScope.() -> Unit) =
        EnumConstantMapScope(map).configuration()

    @Test
    fun add() {
        map.put("FIELD1")
        map.put("FIELD2", "value2")
        map.put("FIELD3", "value3") { javadoc.append("text3") }
        map.put("FIELD4", codeBlockOf("value4"))
        map.put("FIELD5", codeBlockOf("value5")) { javadoc.append("text5") }
        assertThat(map).containsExactly(
            "FIELD1", TypeSpec.anonymousClassBuilder("").build(),
            "FIELD2", TypeSpec.anonymousClassBuilder("value2").build(),
            "FIELD3", TypeSpec.anonymousClassBuilder("value3").addJavadoc("text3").build(),
            "FIELD4", TypeSpec.anonymousClassBuilder("value4").build(),
            "FIELD5", TypeSpec.anonymousClassBuilder("value5").addJavadoc("text5").build()
        )
    }

    @Test
    fun set() {
        map["FIELD1"] = "value1"
        map["FIELD2"] = codeBlockOf("value2")
        assertThat(map).containsExactly(
            "FIELD1",
            TypeSpec.anonymousClassBuilder("value1").build(),
            "FIELD2",
            TypeSpec.anonymousClassBuilder("value2").build()
        )
    }

    @Test
    @Suppress("UNUSED_VARIABLE", "LocalVariableName")
    fun putting() {
        val FIELD1 by map.putting
        val FIELD2 by map.putting("value2")
        val FIELD3 by map.putting("value3") { javadoc.append("text3") }
        val FIELD4 by map.putting(codeBlockOf("value4"))
        val FIELD5 by map.putting(codeBlockOf("value5")) { javadoc.append("text5") }
        assertThat(map).containsExactly(
            "FIELD1", TypeSpec.anonymousClassBuilder("").build(),
            "FIELD2", TypeSpec.anonymousClassBuilder("value2").build(),
            "FIELD3", TypeSpec.anonymousClassBuilder("value3").addJavadoc("text3").build(),
            "FIELD4", TypeSpec.anonymousClassBuilder("value4").build(),
            "FIELD5", TypeSpec.anonymousClassBuilder("value5").addJavadoc("text5").build()
        )
    }

    @Test
    fun invoke() {
        map {
            "FIELD1"("value1") { javadoc.append("text1") }
            "FIELD2"(codeBlockOf("value2")) { javadoc.append("text2") }
        }
        assertThat(map).containsExactly(
            "FIELD1",
            TypeSpec.anonymousClassBuilder("value1").addJavadoc("text1").build(),
            "FIELD2",
            TypeSpec.anonymousClassBuilder("value2").addJavadoc("text2").build()
        )
    }
}
