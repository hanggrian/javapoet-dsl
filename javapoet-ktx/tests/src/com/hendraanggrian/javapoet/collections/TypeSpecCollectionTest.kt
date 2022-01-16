package com.hendraanggrian.javapoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.asClassName
import com.hendraanggrian.javapoet.codeBlockOf
import com.squareup.javapoet.TypeSpec
import kotlin.test.Test

class TypeSpecCollectionTest {

    private val list = TypeSpecCollection(mutableListOf())

    private class Class2
    private interface Interface2
    private enum class Enum2
    private annotation class Annotation2

    @Test
    fun add() {
        list.addClass("Class1")
        list.addClass(Class2::class.asClassName())
        list.addInterface("Interface1")
        list.addInterface(Interface2::class.asClassName())
        list.addEnum("Enum1") { addEnumConstant("A") }
        list.addEnum(Enum2::class.asClassName()) { addEnumConstant("B") }
        list.addAnonymous("Anonymous1")
        list.addAnonymous(codeBlockOf("Anonymous2"))
        list.addAnnotation("Annotation1")
        list.addAnnotation(Annotation2::class.asClassName())
        assertThat(list).containsExactly(
            TypeSpec.classBuilder("Class1").build(),
            TypeSpec.classBuilder(Class2::class.asClassName()).build(),
            TypeSpec.interfaceBuilder("Interface1").build(),
            TypeSpec.interfaceBuilder(Interface2::class.asClassName()).build(),
            TypeSpec.enumBuilder("Enum1").addEnumConstant("A").build(),
            TypeSpec.enumBuilder(Enum2::class.asClassName()).addEnumConstant("B").build(),
            TypeSpec.anonymousClassBuilder("Anonymous1").build(),
            TypeSpec.anonymousClassBuilder(codeBlockOf("Anonymous2")).build(),
            TypeSpec.annotationBuilder("Annotation1").build(),
            TypeSpec.annotationBuilder(Annotation2::class.asClassName()).build()
        )
    }
}