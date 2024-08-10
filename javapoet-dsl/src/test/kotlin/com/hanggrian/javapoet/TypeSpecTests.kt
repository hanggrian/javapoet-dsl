package com.hanggrian.javapoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Field1
import com.example.Field2
import com.example.Field3
import com.example.Field4
import com.example.Field5
import com.example.Field6
import com.example.Field7
import com.google.common.truth.Truth.assertThat
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeVariableName
import javax.lang.model.element.Modifier
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertFalse

@Suppress("ktlint:standard:property-naming")
class TypeSpecHandlerTest {
    @Test
    fun add() {
        assertThat(
            buildJavaFile("com.example") {
                types.addClass("HelloWorld") {
                    types.addClass("Class1")
                    types.addClass(Annotation1::class.name)
                    types.addClass("Class2") { addJavadoc("text2") }
                    types.addClass(Annotation2::class.name) { addJavadoc("text2") }
                    types.addInterface("Interface1")
                    types.addInterface(Annotation1::class.name)
                    types.addInterface("Interface2") { addJavadoc("text2") }
                    types.addInterface(Annotation2::class.name) { addJavadoc("text2") }
                    types.addEnum("Enum2") { addEnumConstant("B") }
                    types.addEnum(Annotation1::class.name) { addEnumConstant("B") }
                    types {
                        addAnonymous("Anonymous1")
                        addAnonymous(codeBlockOf(""))
                        addAnonymous("Anonymous2") { addJavadoc("text2") }
                        addAnonymous(codeBlockOf("")) { addJavadoc("text2") }
                        addAnnotation("Annotation1")
                        addAnnotation(Annotation1::class.name)
                        addAnnotation("Annotation2") { addJavadoc("text2") }
                        addAnnotation(Annotation2::class.name) { addJavadoc("text2") }
                    }
                }
            },
        ).isEqualTo(
            JavaFile
                .builder(
                    "com.example",
                    TypeSpec
                        .classBuilder("HelloWorld")
                        .addType(TypeSpec.classBuilder("Class1").build())
                        .addType(TypeSpec.classBuilder(Annotation1::class.name).build())
                        .addType(TypeSpec.classBuilder("Class2").addJavadoc("text2").build())
                        .addType(
                            TypeSpec
                                .classBuilder(Annotation2::class.name)
                                .addJavadoc("text2")
                                .build(),
                        ).addType(TypeSpec.interfaceBuilder("Interface1").build())
                        .addType(TypeSpec.interfaceBuilder(Annotation1::class.name).build())
                        .addType(
                            TypeSpec
                                .interfaceBuilder("Interface2")
                                .addJavadoc("text2")
                                .build(),
                        ).addType(
                            TypeSpec
                                .interfaceBuilder(Annotation2::class.name)
                                .addJavadoc("text2")
                                .build(),
                        ).addType(TypeSpec.enumBuilder("Enum2").addEnumConstant("B").build())
                        .addType(
                            TypeSpec
                                .enumBuilder(Annotation1::class.name)
                                .addEnumConstant("B")
                                .build(),
                        ).addType(TypeSpec.anonymousClassBuilder("Anonymous1").build())
                        .addType(TypeSpec.anonymousClassBuilder(codeBlockOf("")).build())
                        .addType(
                            TypeSpec
                                .anonymousClassBuilder("Anonymous2")
                                .addJavadoc("text2")
                                .build(),
                        ).addType(
                            TypeSpec
                                .anonymousClassBuilder(codeBlockOf(""))
                                .addJavadoc("text2")
                                .build(),
                        ).addType(TypeSpec.annotationBuilder("Annotation1").build())
                        .addType(TypeSpec.annotationBuilder(Annotation1::class.name).build())
                        .addType(
                            TypeSpec
                                .annotationBuilder("Annotation2")
                                .addJavadoc("text2")
                                .build(),
                        ).addType(
                            TypeSpec
                                .annotationBuilder(Annotation2::class.name)
                                .addJavadoc("text2")
                                .build(),
                        ).build(),
                ).build(),
        )
    }

    @Test
    fun adding() {
        assertThat(
            buildJavaFile("com.example") {
                types.addClass("HelloWorld") {
                    val Class1 by types.addingClass()
                    val Class2 by types.addingClass { addJavadoc("text2") }
                    val Interface1 by types.addingInterface()
                    val Interface2 by types.addingInterface { addJavadoc("text2") }
                    val Enum1 by types.addingEnum { addEnumConstant("A") }
                    val Enum2 by types.addingEnum { addEnumConstant("B") }
                    val Anonymous1 by types.addingAnonymous()
                    val Anonymous2 by types.addingAnonymous { addJavadoc("text2") }
                    val Annotation1 by types.addingAnnotation()
                    val Annotation2 by types.addingAnnotation { addJavadoc("text2") }
                }
            },
        ).isEqualTo(
            JavaFile
                .builder(
                    "com.example",
                    TypeSpec
                        .classBuilder("HelloWorld")
                        .addType(TypeSpec.classBuilder("Class1").build())
                        .addType(TypeSpec.classBuilder("Class2").addJavadoc("text2").build())
                        .addType(TypeSpec.interfaceBuilder("Interface1").build())
                        .addType(
                            TypeSpec
                                .interfaceBuilder("Interface2")
                                .addJavadoc("text2")
                                .build(),
                        ).addType(TypeSpec.enumBuilder("Enum1").addEnumConstant("A").build())
                        .addType(TypeSpec.enumBuilder("Enum2").addEnumConstant("B").build())
                        .addType(TypeSpec.anonymousClassBuilder("Anonymous1").build())
                        .addType(
                            TypeSpec
                                .anonymousClassBuilder("Anonymous2")
                                .addJavadoc("text2")
                                .build(),
                        ).addType(TypeSpec.annotationBuilder("Annotation1").build())
                        .addType(
                            TypeSpec
                                .annotationBuilder("Annotation2")
                                .addJavadoc("text2")
                                .build(),
                        ).build(),
                ).build(),
        )
    }

    @Test
    fun invoke() {
        assertThat(
            buildJavaFile("com.example") {
                types.addClass("OuterClass") {
                    types {
                        "Class1" { addJavadoc("text1") }
                        "Class2" { addJavadoc("text2") }
                    }
                }
            }.typeSpec,
        ).isEqualTo(
            TypeSpec
                .classBuilder("OuterClass")
                .addType(TypeSpec.classBuilder("Class1").addJavadoc("text1").build())
                .addType(TypeSpec.classBuilder("Class2").addJavadoc("text2").build())
                .build(),
        )
    }
}

class TypeSpecBuilderTest {
    @Test
    fun `Fails when inserted more than 1 types`() {
        assertFails {
            buildJavaFile("MyClass") {
                types {
                    addClass("Class1")
                    addClass("Class2")
                }
            }
        }
    }

    @Test
    fun addJavadoc() {
        assertThat(
            buildClassTypeSpec("class1") {
                addJavadoc("javadoc1")
                addJavadoc(codeBlockOf("javadoc2"))
            },
        ).isEqualTo(
            TypeSpec
                .classBuilder("class1")
                .addJavadoc("javadoc1")
                .addJavadoc(CodeBlock.of("javadoc2"))
                .build(),
        )
    }

    @Test
    fun addModifiers() {
        assertThat(
            buildClassTypeSpec("class1") {
                addModifiers(PUBLIC, FINAL, STATIC)
                assertFalse(modifiers.isEmpty())
            },
        ).isEqualTo(
            TypeSpec
                .classBuilder("class1")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .build(),
        )
    }

    @Test
    fun addTypeVariable() {
        assertThat(
            buildClassTypeSpec("class1") {
                addTypeVariables(
                    "typeVar1".genericsBy(Annotation1::class),
                    "typeVar2".genericsBy(Annotation2::class),
                )
                assertFalse(typeVariables.isEmpty())
            },
        ).isEqualTo(
            TypeSpec
                .classBuilder("class1")
                .addTypeVariables(
                    listOf(
                        TypeVariableName.get("typeVar1", Annotation1::class.java),
                        TypeVariableName.get("typeVar2", Annotation2::class.java),
                    ),
                ).build(),
        )
    }

    @Test
    fun superclass() {
        assertThat(
            buildClassTypeSpec("class1") { superclass = Field1::class.name },
        ).isEqualTo(
            TypeSpec.classBuilder("class1").superclass(Field1::class.name).build(),
        )
        assertThat(
            buildClassTypeSpec("class2") { setSuperclass(Field2::class) },
        ).isEqualTo(
            TypeSpec.classBuilder("class2").superclass(Field2::class.java).build(),
        )
        assertThat(
            buildClassTypeSpec("class3") { setSuperclass(Field2::class.java) },
        ).isEqualTo(
            TypeSpec.classBuilder("class3").superclass(Field2::class.java).build(),
        )
        assertThat(
            buildClassTypeSpec("class4") { setSuperclass<Field3>() },
        ).isEqualTo(
            TypeSpec.classBuilder("class4").superclass(Field3::class.java).build(),
        )
    }

    @Test
    fun addSuperinterfaces() {
        assertThat(
            buildClassTypeSpec("class1") {
                addSuperinterfaces(Field1::class.name, Field2::class.name)
                addSuperinterfaces(Field3::class.java, Field4::class.java)
                addSuperinterfaces(Field5::class, Field6::class)
                addSuperinterface<Field7>()
                assertFalse(superinterfaces.isEmpty())
            },
        ).isEqualTo(
            TypeSpec
                .classBuilder("class1")
                .addSuperinterface(Field1::class.java)
                .addSuperinterface(Field2::class.java)
                .addSuperinterface(Field3::class.java)
                .addSuperinterface(Field4::class.java)
                .addSuperinterface(Field5::class.java)
                .addSuperinterface(Field6::class.java)
                .addSuperinterface(Field7::class.java)
                .build(),
        )
    }

    @Test
    fun addEnumConstant() {
        assertThat(
            buildEnumTypeSpec("class1") { addEnumConstant("VALUE") },
        ).isEqualTo(
            TypeSpec.enumBuilder("class1").addEnumConstant("VALUE").build(),
        )
    }

    @Test
    fun addStaticBlock() {
        assertThat(
            buildClassTypeSpec("class1") { addStaticBlock("some code") },
        ).isEqualTo(
            TypeSpec.classBuilder("class1").addStaticBlock(codeBlockOf("some code")).build(),
        )
        assertThat(
            buildClassTypeSpec("class1") { addStaticBlock(codeBlockOf("some code")) },
        ).isEqualTo(
            TypeSpec.classBuilder("class1").addStaticBlock(codeBlockOf("some code")).build(),
        )
    }

    @Test
    fun addInitializerBlock() {
        assertThat(
            buildClassTypeSpec("class1") { addInitializerBlock("some code") },
        ).isEqualTo(
            TypeSpec.classBuilder("class1").addInitializerBlock(codeBlockOf("some code")).build(),
        )
        assertThat(
            buildClassTypeSpec("class1") { addInitializerBlock(codeBlockOf("some code")) },
        ).isEqualTo(
            TypeSpec.classBuilder("class1").addInitializerBlock(codeBlockOf("some code")).build(),
        )
    }

    @Test
    fun alwaysQualify() {
        assertThat(
            buildClassTypeSpec("class1") {
                alwaysQualify("name1", "name2")
                assertFalse(alwaysQualifiedNames.isEmpty())
            },
        ).isEqualTo(
            TypeSpec.classBuilder("class1").alwaysQualify("name1", "name2").build(),
        )
    }

    @Test
    fun avoidClashesWithNestedClasses() {
        assertThat(
            buildClassTypeSpec("class1") {
                avoidClashesWithNestedClasses(Field1::class)
                avoidClashesWithNestedClasses<Field2>()
            },
        ).isEqualTo(
            TypeSpec
                .classBuilder("class1")
                .avoidClashesWithNestedClasses(Field1::class.java)
                .avoidClashesWithNestedClasses(Field2::class.java)
                .build(),
        )
    }
}
