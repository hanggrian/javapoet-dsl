package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeSpec
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeSpecBuilderTest {

    @Test
    fun simple() {
        assertEquals(
            TypeSpec.classBuilder("myClass").build(),
            TypeSpecBuilder.ofClass("myClass")
        )
        assertEquals(
            TypeSpec.interfaceBuilder("myInterface").build(),
            TypeSpecBuilder.ofInterface("myInterface")
        )
        assertEquals(
            TypeSpec.enumBuilder("myEnum").addEnumConstant("A").build(),
            TypeSpecBuilder.ofEnum("myEnum") {
                addEnumConstant("A")
            }
        )
        assertEquals(
            TypeSpec.anonymousClassBuilder("myAnonymous").build(),
            TypeSpecBuilder.ofAnonymous("myAnonymous")
        )
        assertEquals(
            TypeSpec.annotationBuilder("myAnnotation").build(),
            TypeSpecBuilder.ofAnnotation("myAnnotation")
        )
    }
}