package com.hendraanggrian.javapoet.dsl

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.annotationTypeSpecOf
import com.hendraanggrian.javapoet.anonymousTypeSpecOf
import com.hendraanggrian.javapoet.buildEnumTypeSpec
import com.hendraanggrian.javapoet.classOf
import com.hendraanggrian.javapoet.classTypeSpecOf
import com.hendraanggrian.javapoet.interfaceTypeSpecOf
import com.squareup.javapoet.TypeSpec
import kotlin.test.Test

class TypeSpecContainerTest {
    private val types = mutableListOf<TypeSpec>()
    private val container = object : TypeSpecContainer() {
        override fun addAll(specs: Iterable<TypeSpec>): Boolean = types.addAll(specs)
        override fun add(spec: TypeSpec) {
            types += spec
        }
    }

    private inline fun container(configuration: TypeSpecContainerScope.() -> Unit) =
        TypeSpecContainerScope(container).configuration()

    @Test fun nativeSpec() {
        container += classTypeSpecOf("Class1")
        container += listOf(classTypeSpecOf("Class2"))
        assertThat(types).containsExactly(
            classTypeSpecOf("Class1"),
            classTypeSpecOf("Class2")
        )
    }

    @Test fun invocation() {
        val packageName = "com.hendraanggrian.javapoet.dsl.TypeSpecContainerTest"
        container {
            "Class1" { }
            (packageName.classOf("MyType")) { }
        }
        assertThat(types).containsExactly(
            classTypeSpecOf("Class1"),
            classTypeSpecOf(packageName.classOf("MyType"))
        )
    }

    @Test fun others() {
        container.addClass("Class1")
        container.addInterface("Interface1")
        container.addEnum("Enum1") { addEnumConstant("A") }
        container.addAnonymous("Anonymous1")
        container.addAnnotation("Annotation1")
        assertThat(types).containsExactly(
            classTypeSpecOf("Class1"),
            interfaceTypeSpecOf("Interface1"),
            buildEnumTypeSpec("Enum1") { addEnumConstant("A") },
            anonymousTypeSpecOf("Anonymous1"),
            annotationTypeSpecOf("Annotation1")
        )
    }
}