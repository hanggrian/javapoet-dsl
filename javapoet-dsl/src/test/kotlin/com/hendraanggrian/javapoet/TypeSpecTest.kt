package com.hendraanggrian.javapoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Annotation3
import com.example.Field1
import com.example.Field2
import com.example.Field3
import com.example.Field4
import com.example.Field5
import com.google.common.truth.Truth.assertThat
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeVariableName
import javax.lang.model.element.Modifier
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertFalse

@Suppress("ktlint:standard:property-naming")
class TypeSpecHandlerTest {
    @Test
    fun classType() {
        assertThat(
            buildJavaFile("com.example") {
                classType("Class1")
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.classBuilder("Class1").build(),
        )
        assertThat(
            buildJavaFile("com.example") {
                classType("Class2") { javadoc.append("text2") }
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.classBuilder("Class2").addJavadoc("text2").build(),
        )
    }

    @Test
    fun interfaceType() {
        assertThat(
            buildJavaFile("com.example") {
                interfaceType("Interface1")
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.interfaceBuilder("Interface1").build(),
        )
        assertThat(
            buildJavaFile("com.example") {
                interfaceType("Interface2") { javadoc.append("text2") }
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.interfaceBuilder("Interface2").addJavadoc("text2").build(),
        )
    }

    @Test
    fun enumType() {
        assertThat(
            buildJavaFile("com.example") {
                enumType("Enum1") { enumConstant("A") }
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.enumBuilder("Enum1").addEnumConstant("A").build(),
        )
        assertThat(
            buildJavaFile("com.example") {
                enumType("Enum2") { enumConstant("B") }
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.enumBuilder("Enum2").addEnumConstant("B").build(),
        )
    }

    @Test
    fun anonymousType() {
        assertThat(
            buildJavaFile("com.example") {
                anonymousType("Anonymous1")
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.anonymousClassBuilder("Anonymous1").build(),
        )
        assertThat(
            buildJavaFile("com.example") {
                anonymousType("Anonymous2") { javadoc.append("text2") }
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.anonymousClassBuilder("Anonymous2").addJavadoc("text2").build(),
        )
    }

    @Test
    fun annotationType() {
        assertThat(
            buildJavaFile("com.example") {
                annotationType("Annotation1")
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.annotationBuilder("Annotation1").build(),
        )
        assertThat(
            buildJavaFile("com.example") {
                annotationType("Annotation2") { javadoc.append("text2") }
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.annotationBuilder("Annotation2").addJavadoc("text2").build(),
        )
    }

    @Test
    fun classTyping() {
        assertThat(
            buildJavaFile("com.example") {
                val Class1 by classTyping()
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.classBuilder("Class1").build(),
        )
        assertThat(
            buildJavaFile("com.example") {
                val Class2 by classTyping { javadoc.append("text2") }
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.classBuilder("Class2").addJavadoc("text2").build(),
        )
    }

    @Test
    fun interfaceTyping() {
        assertThat(
            buildJavaFile("com.example") {
                val Interface1 by interfaceTyping()
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.interfaceBuilder("Interface1").build(),
        )
        assertThat(
            buildJavaFile("com.example") {
                val Interface2 by interfaceTyping { javadoc.append("text2") }
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.interfaceBuilder("Interface2").addJavadoc("text2").build(),
        )
    }

    @Test
    fun enumTyping() {
        assertThat(
            buildJavaFile("com.example") {
                val Enum1 by enumTyping { enumConstant("A") }
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.enumBuilder("Enum1").addEnumConstant("A").build(),
        )
        assertThat(
            buildJavaFile("com.example") {
                val Enum2 by enumTyping { enumConstant("B") }
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.enumBuilder("Enum2").addEnumConstant("B").build(),
        )
    }

    @Test
    fun anonymousTyping() {
        assertThat(
            buildJavaFile("com.example") {
                val Anonymous1 by anonymousTyping()
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.anonymousClassBuilder("Anonymous1").build(),
        )
        assertThat(
            buildJavaFile("com.example") {
                val Anonymous2 by anonymousTyping { javadoc.append("text2") }
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.anonymousClassBuilder("Anonymous2").addJavadoc("text2").build(),
        )
    }

    @Test
    fun annotationTyping() {
        assertThat(
            buildJavaFile("com.example") {
                val Annotation1 by annotationTyping()
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.annotationBuilder("Annotation1").build(),
        )
        assertThat(
            buildJavaFile("com.example") {
                val Annotation2 by annotationTyping { javadoc.append("text2") }
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.annotationBuilder("Annotation2").addJavadoc("text2").build(),
        )
    }

    @Test
    fun invoke() {
        assertThat(
            buildJavaFile("com.example") {
                classType("OuterClass") {
                    types {
                        "Class1" { javadoc.append("text1") }
                        "Class2" { javadoc.append("text2") }
                    }
                }
            }.typeSpec,
        ).isEqualTo(
            TypeSpec.classBuilder("OuterClass")
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
                classType("Class1")
                classType("Class2")
            }
        }
    }

    @Test
    fun javadoc() {
        assertThat(
            buildClassTypeSpec("class1") {
                javadoc.append("javadoc1")
                javadoc.append(codeBlockOf("javadoc2"))
            },
        ).isEqualTo(
            TypeSpec.classBuilder("class1")
                .addJavadoc("javadoc1")
                .addJavadoc(CodeBlock.of("javadoc2"))
                .build(),
        )
    }

    @Test
    fun annotations() {
        assertThat(
            buildClassTypeSpec("class1") {
                annotation(Annotation1::class.name)
                annotation(Annotation2::class)
                annotation<Annotation3>()
            },
        ).isEqualTo(
            TypeSpec.classBuilder("class1")
                .addAnnotation(Annotation1::class.java)
                .addAnnotation(Annotation2::class.java)
                .addAnnotation(Annotation3::class.java)
                .build(),
        )
    }

    @Test
    fun modifiers() {
        assertThat(
            buildClassTypeSpec("class1") {
                modifiers(PUBLIC, FINAL, STATIC)
                assertFalse(modifiers.isEmpty())
            },
        ).isEqualTo(
            TypeSpec.classBuilder("class1")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .build(),
        )
    }

    @Test
    fun typeVariables() {
        assertThat(
            buildClassTypeSpec("class1") {
                typeVariables(
                    listOf(
                        "typeVar1".genericsBy(Annotation1::class),
                        "typeVar2".genericsBy(Annotation2::class),
                    ),
                )
                typeVariable("typeVar3".genericsBy(Annotation3::class))
                assertFalse(typeVariables.isEmpty())
            },
        ).isEqualTo(
            TypeSpec.classBuilder("class1")
                .addTypeVariables(
                    listOf(
                        TypeVariableName.get("typeVar1", Annotation1::class.java),
                        TypeVariableName.get("typeVar2", Annotation2::class.java),
                    ),
                )
                .addTypeVariable(TypeVariableName.get("typeVar3", Annotation3::class.java))
                .build(),
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
            buildClassTypeSpec("class2") { superclass(Field2::class) },
        ).isEqualTo(
            TypeSpec.classBuilder("class2").superclass(Field2::class.java).build(),
        )
        assertThat(
            buildClassTypeSpec("class3") { superclass<Field3>() },
        ).isEqualTo(
            TypeSpec.classBuilder("class3").superclass(Field3::class.java).build(),
        )
    }

    @Test
    fun superinterfaces() {
        assertThat(
            buildClassTypeSpec("class1") {
                superinterfaces(listOf(Field1::class.name, Field2::class.name))
                superinterface(Field3::class.name)
                superinterface(Field4::class)
                superinterface<Field5>()
                assertFalse(superinterfaces.isEmpty())
            },
        ).isEqualTo(
            TypeSpec.classBuilder("class1")
                .addSuperinterface(Field1::class.java)
                .addSuperinterface(Field2::class.java)
                .addSuperinterface(Field3::class.java)
                .addSuperinterface(Field4::class.java)
                .addSuperinterface(Field5::class.java)
                .build(),
        )
    }

    @Test
    fun enumConstants() {
        assertThat(
            buildEnumTypeSpec("class1") { enumConstant("VALUE") },
        ).isEqualTo(
            TypeSpec.enumBuilder("class1").addEnumConstant("VALUE").build(),
        )
    }

    @Test
    fun fields() {
        assertThat(
            buildClassTypeSpec("class1") { field<Field1>("field1") },
        ).isEqualTo(
            TypeSpec.classBuilder("class1").addField(Field1::class.java, "field1").build(),
        )
    }

    @Test
    fun staticBlock() {
        assertThat(
            buildClassTypeSpec("class1") { staticBlock("some code") },
        ).isEqualTo(
            TypeSpec.classBuilder("class1").addStaticBlock(codeBlockOf("some code")).build(),
        )
        assertThat(
            buildClassTypeSpec("class1") { staticBlock(codeBlockOf("some code")) },
        ).isEqualTo(
            TypeSpec.classBuilder("class1").addStaticBlock(codeBlockOf("some code")).build(),
        )
    }

    @Test
    fun initializerBlock() {
        assertThat(
            buildClassTypeSpec("class1") { initializerBlock("some code") },
        ).isEqualTo(
            TypeSpec.classBuilder("class1").addInitializerBlock(codeBlockOf("some code")).build(),
        )
        assertThat(
            buildClassTypeSpec("class1") { initializerBlock(codeBlockOf("some code")) },
        ).isEqualTo(
            TypeSpec.classBuilder("class1").addInitializerBlock(codeBlockOf("some code")).build(),
        )
    }

    @Test
    fun methods() {
        assertThat(
            buildClassTypeSpec("class1") { method("method1") },
        ).isEqualTo(
            TypeSpec.classBuilder("class1")
                .addMethod(MethodSpec.methodBuilder("method1").build())
                .build(),
        )
    }

    @Test
    fun types() {
        assertThat(
            buildClassTypeSpec("class1") { classType("class2") },
        ).isEqualTo(
            TypeSpec.classBuilder("class1")
                .addType(TypeSpec.classBuilder("class2").build())
                .build(),
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
            TypeSpec.classBuilder("class1")
                .avoidClashesWithNestedClasses(Field1::class.java)
                .avoidClashesWithNestedClasses(Field2::class.java)
                .build(),
        )
    }
}
