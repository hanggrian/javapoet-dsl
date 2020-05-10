package com.hendraanggrian.javapoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.annotationTypeSpecOf
import com.hendraanggrian.javapoet.anonymousTypeSpecOf
import com.hendraanggrian.javapoet.buildEnumTypeSpec
import com.hendraanggrian.javapoet.classOf
import com.hendraanggrian.javapoet.classTypeSpecOf
import com.hendraanggrian.javapoet.interfaceTypeSpecOf
import kotlin.test.Test

class TypeSpecListTest {
    private val list = TypeSpecList(mutableListOf())

    private inline fun container(configuration: TypeSpecListScope.() -> Unit) =
        TypeSpecListScope(list).configuration()

    @Test fun nativeSpec() {
        list += classTypeSpecOf("Class1")
        list += listOf(classTypeSpecOf("Class2"))
        assertThat(list).containsExactly(
            classTypeSpecOf("Class1"),
            classTypeSpecOf("Class2")
        )
    }

    @Test fun invocation() {
        val packageName = "com.hendraanggrian.javapoet.collections.TypeSpecListTest"
        container {
            "Class1" { }
            (packageName.classOf("MyType")) { }
        }
        assertThat(list).containsExactly(
            classTypeSpecOf("Class1"),
            classTypeSpecOf(packageName.classOf("MyType"))
        )
    }

    @Test fun others() {
        list.addClass("Class1")
        list.addInterface("Interface1")
        list.addEnum("Enum1") { addEnumConstant("A") }
        list.addAnonymous("Anonymous1")
        list.addAnnotation("Annotation1")
        assertThat(list).containsExactly(
            classTypeSpecOf("Class1"),
            interfaceTypeSpecOf("Interface1"),
            buildEnumTypeSpec("Enum1") { addEnumConstant("A") },
            anonymousTypeSpecOf("Anonymous1"),
            annotationTypeSpecOf("Annotation1")
        )
    }
}