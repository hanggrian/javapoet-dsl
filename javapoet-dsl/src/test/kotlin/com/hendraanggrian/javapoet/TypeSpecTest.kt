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

@Suppress("ktlint:standard:property-naming")
class TypeSpecHandlerTest {
    @Test
    fun classType() {
        assertEquals(
            TypeSpec.classBuilder("Class1").build(),
            buildJavaFile("com.example") {
                classType("Class1")
            }.typeSpec,
        )
        assertEquals(
            TypeSpec.classBuilder("Class2").addJavadoc("text2").build(),
            buildJavaFile("com.example") {
                classType("Class2") { javadoc.append("text2") }
            }.typeSpec,
        )
    }

    @Test
    fun interfaceType() {
        assertEquals(
            TypeSpec.interfaceBuilder("Interface1").build(),
            buildJavaFile("com.example") {
                interfaceType("Interface1")
            }.typeSpec,
        )
        assertEquals(
            TypeSpec.interfaceBuilder("Interface2").addJavadoc("text2").build(),
            buildJavaFile("com.example") {
                interfaceType("Interface2") { javadoc.append("text2") }
            }.typeSpec,
        )
    }

    @Test
    fun enumType() {
        assertEquals(
            TypeSpec.enumBuilder("Enum1").addEnumConstant("A").build(),
            buildJavaFile("com.example") {
                enumType("Enum1") { enumConstant("A") }
            }.typeSpec,
        )
        assertEquals(
            TypeSpec.enumBuilder("Enum2").addEnumConstant("B").build(),
            buildJavaFile("com.example") {
                enumType("Enum2") { enumConstant("B") }
            }.typeSpec,
        )
    }

    @Test
    fun anonymousType() {
        assertEquals(
            TypeSpec.anonymousClassBuilder("Anonymous1").build(),
            buildJavaFile("com.example") {
                anonymousType("Anonymous1")
            }.typeSpec,
        )
        assertEquals(
            TypeSpec.anonymousClassBuilder("Anonymous2").addJavadoc("text2").build(),
            buildJavaFile("com.example") {
                anonymousType("Anonymous2") { javadoc.append("text2") }
            }.typeSpec,
        )
    }

    @Test
    fun annotationType() {
        assertEquals(
            TypeSpec.annotationBuilder("Annotation1").build(),
            buildJavaFile("com.example") {
                annotationType("Annotation1")
            }.typeSpec,
        )
        assertEquals(
            TypeSpec.annotationBuilder("Annotation2").addJavadoc("text2").build(),
            buildJavaFile("com.example") {
                annotationType("Annotation2") { javadoc.append("text2") }
            }.typeSpec,
        )
    }

    @Test
    fun classTyping() {
        assertEquals(
            TypeSpec.classBuilder("Class1").build(),
            buildJavaFile("com.example") {
                val Class1 by classTyping()
            }.typeSpec,
        )
        assertEquals(
            TypeSpec.classBuilder("Class2").addJavadoc("text2").build(),
            buildJavaFile("com.example") {
                val Class2 by classTyping { javadoc.append("text2") }
            }.typeSpec,
        )
    }

    @Test
    fun interfaceTyping() {
        assertEquals(
            TypeSpec.interfaceBuilder("Interface1").build(),
            buildJavaFile("com.example") {
                val Interface1 by interfaceTyping()
            }.typeSpec,
        )
        assertEquals(
            TypeSpec.interfaceBuilder("Interface2").addJavadoc("text2").build(),
            buildJavaFile("com.example") {
                val Interface2 by interfaceTyping { javadoc.append("text2") }
            }.typeSpec,
        )
    }

    @Test
    fun enumTyping() {
        assertEquals(
            TypeSpec.enumBuilder("Enum1").addEnumConstant("A").build(),
            buildJavaFile("com.example") {
                val Enum1 by enumTyping { enumConstant("A") }
            }.typeSpec,
        )
        assertEquals(
            TypeSpec.enumBuilder("Enum2").addEnumConstant("B").build(),
            buildJavaFile("com.example") {
                val Enum2 by enumTyping { enumConstant("B") }
            }.typeSpec,
        )
    }

    @Test
    fun anonymousTyping() {
        assertEquals(
            TypeSpec.anonymousClassBuilder("Anonymous1").build(),
            buildJavaFile("com.example") {
                val Anonymous1 by anonymousTyping()
            }.typeSpec,
        )
        assertEquals(
            TypeSpec.anonymousClassBuilder("Anonymous2").addJavadoc("text2").build(),
            buildJavaFile("com.example") {
                val Anonymous2 by anonymousTyping { javadoc.append("text2") }
            }.typeSpec,
        )
    }

    @Test
    fun annotationTyping() {
        assertEquals(
            TypeSpec.annotationBuilder("Annotation1").build(),
            buildJavaFile("com.example") {
                val Annotation1 by annotationTyping()
            }.typeSpec,
        )
        assertEquals(
            TypeSpec.annotationBuilder("Annotation2").addJavadoc("text2").build(),
            buildJavaFile("com.example") {
                val Annotation2 by annotationTyping { javadoc.append("text2") }
            }.typeSpec,
        )
    }
}

class TypeSpecBuilderTest {
    @Test
    fun javadoc() {
        assertEquals(
            buildClassTypeSpec("class1") {
                javadoc.append("javadoc1")
                javadoc.append(codeBlockOf("javadoc2"))
            },
            TypeSpec.classBuilder("class1")
                .addJavadoc("javadoc1")
                .addJavadoc(CodeBlock.of("javadoc2"))
                .build(),
        )
    }

    @Test
    fun annotations() {
        assertEquals(
            buildClassTypeSpec("class1") { annotation<Annotation1>() },
            TypeSpec.classBuilder("class1").addAnnotation(Annotation1::class.java).build(),
        )
    }

    @Test
    fun addModifiers() {
        assertEquals(
            buildClassTypeSpec("class1") { modifiers(PUBLIC, FINAL, STATIC) },
            TypeSpec.classBuilder("class1")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .build(),
        )
    }

    @Test
    fun typeVariables() {
        assertEquals(
            buildClassTypeSpec("class1") {
                typeVariables.add("typeVar1".genericsBy(Annotation1::class))
            },
            TypeSpec.classBuilder("class1")
                .addTypeVariable(TypeVariableName.get("typeVar1", Annotation1::class.java))
                .build(),
        )
    }

    @Test
    fun superclass() {
        assertEquals(
            buildClassTypeSpec("class1") { superclass = Field1::class.asClassName() },
            TypeSpec.classBuilder("class1").superclass(Field1::class.asClassName()).build(),
        )
        assertEquals(
            buildClassTypeSpec("class2") { superclass(Field2::class.java) },
            TypeSpec.classBuilder("class2").superclass(Field2::class.java).build(),
        )
        assertEquals(
            buildClassTypeSpec("class3") { superclass(Field3::class) },
            TypeSpec.classBuilder("class3").superclass(Field3::class.java).build(),
        )
        assertEquals(
            buildClassTypeSpec("class4") { superclass<Field4>() },
            TypeSpec.classBuilder("class4").superclass(Field4::class.java).build(),
        )
    }

    @Test
    fun superinterfaces() {
        assertEquals(
            buildClassTypeSpec("class1") { superinterfaces.add(Field1::class.asClassName()) },
            TypeSpec.classBuilder("class1")
                .addSuperinterface(Field1::class.java)
                .build(),
        )
    }

    @Test
    fun enumConstants() {
        assertEquals(
            buildEnumTypeSpec("class1") { enumConstant("VALUE") },
            TypeSpec.enumBuilder("class1").addEnumConstant("VALUE").build(),
        )
    }

    @Test
    fun fields() {
        assertEquals(
            buildClassTypeSpec("class1") { field<Field1>("field1") },
            TypeSpec.classBuilder("class1").addField(Field1::class.java, "field1").build(),
        )
    }

    @Test
    fun addStaticBlock() {
        assertEquals(
            buildClassTypeSpec("class1") { staticBlock(codeBlockOf("some code")) },
            TypeSpec.classBuilder("class1").addStaticBlock(codeBlockOf("some code")).build(),
        )
    }

    @Test
    fun addInitializerBlock() {
        assertEquals(
            buildClassTypeSpec("class1") { initializerBlock(codeBlockOf("some code")) },
            TypeSpec.classBuilder("class1").addInitializerBlock(codeBlockOf("some code")).build(),
        )
    }

    @Test
    fun methods() {
        assertEquals(
            buildClassTypeSpec("class1") { method("method1") },
            TypeSpec.classBuilder("class1")
                .addMethod(MethodSpec.methodBuilder("method1").build())
                .build(),
        )
    }

    @Test
    fun types() {
        assertEquals(
            buildClassTypeSpec("class1") { classType("class2") },
            TypeSpec.classBuilder("class1")
                .addType(TypeSpec.classBuilder("class2").build())
                .build(),
        )
    }

    @Test
    fun alwaysQualify() {
        assertEquals(
            buildClassTypeSpec("class1") { alwaysQualify("name1", "name2") },
            TypeSpec.classBuilder("class1").alwaysQualify("name1", "name2").build(),
        )
    }

    @Test
    fun avoidClashesWithNestedClasses() {
        assertEquals(
            buildClassTypeSpec("class1") { avoidClashesWithNestedClasses<Field1>() },
            TypeSpec.classBuilder("class1").avoidClashesWithNestedClasses(Field1::class.java)
                .build(),
        )
    }
}
