package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeSpec
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeSpecBuilderTest {

    @Test
    fun simple() {
        assertEquals(
            TypeSpec.classBuilder("myClass").build(),
            buildClassTypeSpec("myClass")
        )
        assertEquals(
            TypeSpec.interfaceBuilder("myInterface").build(),
            buildInterfaceTypeSpec("myInterface")
        )
        assertEquals(
            TypeSpec.enumBuilder("myEnum").addEnumConstant("A").build(),
            buildEnumTypeSpec("myEnum") {
                addEnumConstant("A")
            }
        )
        assertEquals(
            TypeSpec.anonymousClassBuilder("myAnonymous").build(),
            buildAnonymousTypeSpec("myAnonymous")
        )
        assertEquals(
            TypeSpec.annotationBuilder("myAnnotation").build(),
            buildAnnotationTypeSpec("myAnnotation")
        )
    }
}