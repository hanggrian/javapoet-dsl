package com.hendraanggrian.javapoet.dsl

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.classOf
import com.hendraanggrian.javapoet.buildAnnotationType
import com.hendraanggrian.javapoet.buildAnonymousType
import com.hendraanggrian.javapoet.buildClassType
import com.hendraanggrian.javapoet.buildEnumType
import com.hendraanggrian.javapoet.buildInterfaceType
import com.squareup.javapoet.TypeSpec
import kotlin.test.Test

class TypeSpecContainerTest {
    private val specs = mutableListOf<TypeSpec>()
    private val container = object : TypeSpecContainer() {
        override fun add(spec: TypeSpec) {
            specs += spec
        }
    }

    private inline fun container(configuration: TypeSpecContainerScope.() -> Unit) =
        TypeSpecContainerScope(container).configuration()

    @Test fun nativeSpec() {
        container.add(buildClassType("Class1"))
        container += buildClassType("Class2")
        assertThat(specs).containsExactly(
            buildClassType("Class1"),
            buildClassType("Class2")
        )
    }

    @Test fun invocation() {
        val packageName = "com.hendraanggrian.javapoet.dsl.TypeSpecContainerTest"
        container {
            "Class1" { }
            (packageName.classOf("MyType")) { }
        }
        assertThat(specs).containsExactly(
            buildClassType("Class1"),
            buildClassType(packageName.classOf("MyType"))
        )
    }

    @Test fun others() {
        container.addClass("Class1")
        container.addInterface("Interface1")
        container.addEnum("Enum1") { addEnumConstant("A") }
        container.addAnonymous("Anonymous1")
        container.addAnnotation("Annotation1")
        assertThat(specs).containsExactly(
            buildClassType("Class1"),
            buildInterfaceType("Interface1"),
            buildEnumType("Enum1") { addEnumConstant("A") },
            buildAnonymousType("Anonymous1"),
            buildAnnotationType("Annotation1")
        )
    }
}