package com.hendraanggrian.javapoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.asClassName
import com.hendraanggrian.javapoet.codeBlockOf
import com.squareup.javapoet.TypeSpec
import kotlin.test.Test

class TypeSpecListTest {
    private val list = TypeSpecList(mutableListOf())

    private class Class2
    private interface Interface2
    private enum class Enum2
    private annotation class Annotation2

    @Test
    fun add() {
        list.addClass("Class1")
        list.addClass("Class1") { javadoc.append("text2") }
        list.addClass(Class2::class.asClassName())
        list.addClass(Class2::class.asClassName()) { javadoc.append("text4") }
        list.addInterface("Interface1")
        list.addInterface("Interface1") { javadoc.append("text6") }
        list.addInterface(Interface2::class.asClassName())
        list.addInterface(Interface2::class.asClassName()) { javadoc.append("text8") }
        list.addEnum("Enum1") { enumConstants.put("A") }
        list.addEnum(Enum2::class.asClassName()) { enumConstants.put("B") }
        list.addAnonymous("Anonymous1")
        list.addAnonymous("Anonymous1") { javadoc.append("text12") }
        list.addAnonymous(codeBlockOf("Anonymous2"))
        list.addAnonymous(codeBlockOf("Anonymous2")) { javadoc.append("text14") }
        list.addAnnotation("Annotation1")
        list.addAnnotation("Annotation1") { javadoc.append("text16") }
        list.addAnnotation(Annotation2::class.asClassName())
        list.addAnnotation(Annotation2::class.asClassName()) { javadoc.append("text18") }
        assertThat(list).containsExactly(
            TypeSpec.classBuilder("Class1").build(),
            TypeSpec.classBuilder("Class1").addJavadoc("text2").build(),
            TypeSpec.classBuilder(Class2::class.asClassName()).build(),
            TypeSpec.classBuilder(Class2::class.asClassName()).addJavadoc("text4").build(),
            TypeSpec.interfaceBuilder("Interface1").build(),
            TypeSpec.interfaceBuilder("Interface1").addJavadoc("text6").build(),
            TypeSpec.interfaceBuilder(Interface2::class.asClassName()).build(),
            TypeSpec.interfaceBuilder(Interface2::class.asClassName()).addJavadoc("text8").build(),
            TypeSpec.enumBuilder("Enum1").addEnumConstant("A").build(),
            TypeSpec.enumBuilder(Enum2::class.asClassName()).addEnumConstant("B").build(),
            TypeSpec.anonymousClassBuilder("Anonymous1").build(),
            TypeSpec.anonymousClassBuilder("Anonymous1").addJavadoc("text12").build(),
            TypeSpec.anonymousClassBuilder(codeBlockOf("Anonymous2")).build(),
            TypeSpec.anonymousClassBuilder(codeBlockOf("Anonymous2")).addJavadoc("text14").build(),
            TypeSpec.annotationBuilder("Annotation1").build(),
            TypeSpec.annotationBuilder("Annotation1").addJavadoc("text16").build(),
            TypeSpec.annotationBuilder(Annotation2::class.asClassName()).build(),
            TypeSpec.annotationBuilder(Annotation2::class.asClassName()).addJavadoc("text18").build()
        )
    }

    @Test
    @Suppress("UNUSED_VARIABLE", "LocalVariableName")
    fun adding() {
        val Class1 by list.addingClass
        val Class2 by list.addingClass { javadoc.append("text2") }
        val Interface1 by list.addingInterface
        val Interface2 by list.addingInterface { javadoc.append("text4") }
        val Enum1 by list.addingEnum { enumConstants.put("A") }
        val Anonymous1 by list.addingAnonymous
        val Anonymous2 by list.addingAnonymous { javadoc.append("text7") }
        val Annotation1 by list.addingAnnotation
        val Annotation2 by list.addingAnnotation { javadoc.append("text9") }
        assertThat(list).containsExactly(
            TypeSpec.classBuilder("Class1").build(),
            TypeSpec.classBuilder("Class2").addJavadoc("text2").build(),
            TypeSpec.interfaceBuilder("Interface1").build(),
            TypeSpec.interfaceBuilder("Interface2").addJavadoc("text4").build(),
            TypeSpec.enumBuilder("Enum1").addEnumConstant("A").build(),
            TypeSpec.anonymousClassBuilder("Anonymous1").build(),
            TypeSpec.anonymousClassBuilder("Anonymous2").addJavadoc("text7").build(),
            TypeSpec.annotationBuilder("Annotation1").build(),
            TypeSpec.annotationBuilder("Annotation2").addJavadoc("text9").build()
        )
    }
}
