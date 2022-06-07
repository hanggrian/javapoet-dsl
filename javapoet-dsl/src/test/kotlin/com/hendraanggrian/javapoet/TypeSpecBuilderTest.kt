package com.hendraanggrian.javapoet

import com.example.Annotation1
import com.example.Field1
import com.example.Field2
import com.example.Field3
import com.example.Field4
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeVariableName
import javax.lang.model.element.Modifier
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeSpecBuilderTest {

    @Test
    fun javadoc() {
        assertEquals(
            buildClassTypeSpec("class1") {
                javadoc {
                    append("javadoc1")
                    append(codeBlockOf("javadoc2"))
                }
            },
            TypeSpec.classBuilder("class1")
                .addJavadoc("javadoc1")
                .addJavadoc(CodeBlock.of("javadoc2"))
                .build()
        )
    }

    @Test
    fun annotations() {
        assertEquals(
            buildClassTypeSpec("class1") { annotations.add<Annotation1>() },
            TypeSpec.classBuilder("class1").addAnnotation(Annotation1::class.java).build()
        )
    }

    @Test
    fun addModifiers() {
        assertEquals(
            buildClassTypeSpec("class1") { addModifiers(PUBLIC, FINAL, STATIC) },
            TypeSpec.classBuilder("class1")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .build()
        )
    }

    @Test
    fun typeVariables() {
        assertEquals(
            buildClassTypeSpec("class1") { typeVariables.add("typeVar1", Annotation1::class) },
            TypeSpec.classBuilder("class1")
                .addTypeVariable(TypeVariableName.get("typeVar1", Annotation1::class.java))
                .build()
        )
    }

    @Test
    fun superclass() {
        assertEquals(
            buildClassTypeSpec("class1") { superclass = Field1::class.asClassName() },
            TypeSpec.classBuilder("class1").superclass(Field1::class.asClassName()).build()
        )
        assertEquals(
            buildClassTypeSpec("class2") { superclass(Field2::class.java) },
            TypeSpec.classBuilder("class2").superclass(Field2::class.java).build()
        )
        assertEquals(
            buildClassTypeSpec("class3") { superclass(Field3::class) },
            TypeSpec.classBuilder("class3").superclass(Field3::class.java).build()
        )
        assertEquals(
            buildClassTypeSpec("class4") { superclass<Field4>() },
            TypeSpec.classBuilder("class4").superclass(Field4::class.java).build()
        )
    }

    @Test
    fun superinterfaces() {
        assertEquals(
            buildClassTypeSpec("class1") { superinterfaces.add<Field1>() },
            TypeSpec.classBuilder("class1")
                .addSuperinterface(Field1::class.java)
                .build()
        )
    }

    @Test
    fun enumConstants() {
        assertEquals(
            buildEnumTypeSpec("class1") { enumConstants.put("VALUE") },
            TypeSpec.enumBuilder("class1").addEnumConstant("VALUE").build()
        )
    }

    @Test
    fun fields() {
        assertEquals(
            buildClassTypeSpec("class1") { fields.add<Field1>("field1") },
            TypeSpec.classBuilder("class1").addField(Field1::class.java, "field1").build()
        )
    }

    @Test
    fun addStaticBlock() {
        assertEquals(
            buildClassTypeSpec("class1") { addStaticBlock(codeBlockOf("some code")) },
            TypeSpec.classBuilder("class1").addStaticBlock(codeBlockOf("some code")).build()
        )
    }

    @Test
    fun addInitializerBlock() {
        assertEquals(
            buildClassTypeSpec("class1") { addInitializerBlock(codeBlockOf("some code")) },
            TypeSpec.classBuilder("class1").addInitializerBlock(codeBlockOf("some code")).build()
        )
    }

    @Test
    fun methods() {
        assertEquals(
            buildClassTypeSpec("class1") { methods.add("method1") },
            TypeSpec.classBuilder("class1")
                .addMethod(MethodSpec.methodBuilder("method1").build())
                .build()
        )
    }

    @Test
    fun types() {
        assertEquals(
            buildClassTypeSpec("class1") { types.addClass("class2") },
            TypeSpec.classBuilder("class1")
                .addType(TypeSpec.classBuilder("class2").build())
                .build()
        )
    }

    @Test
    fun alwaysQualify() {
        assertEquals(
            buildClassTypeSpec("class1") { alwaysQualify("name1", "name2") },
            TypeSpec.classBuilder("class1").alwaysQualify("name1", "name2").build()
        )
    }

    @Test
    fun avoidClashesWithNestedClasses() {
        assertEquals(
            buildClassTypeSpec("class1") { avoidClashesWithNestedClasses<Field1>() },
            TypeSpec.classBuilder("class1").avoidClashesWithNestedClasses(Field1::class.java).build()
        )
    }
}
