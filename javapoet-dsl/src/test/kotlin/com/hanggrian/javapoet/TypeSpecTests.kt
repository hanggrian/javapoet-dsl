@file:Suppress("ktlint:standard:property-naming")

package com.hanggrian.javapoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Class1
import com.example.Class2
import com.example.Field1
import com.example.Field2
import com.example.Field3
import com.example.Field4
import com.example.Field5
import com.example.Field6
import com.example.Field7
import com.google.common.truth.Truth.assertThat
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeVariableName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import javax.lang.model.element.Modifier
import kotlin.test.Test

class TypeSpecCreatorTest {
    @Test
    fun of() {
        assertThat(classTypeSpecOf("MyClass"))
            .isEqualTo(TypeSpec.classBuilder("MyClass").build())
        assertThat(classTypeSpecOf(Class1::class.name))
            .isEqualTo(TypeSpec.classBuilder(ClassName.get(Class1::class.java)).build())

        assertThat(interfaceTypeSpecOf("MyClass"))
            .isEqualTo(TypeSpec.interfaceBuilder("MyClass").build())
        assertThat(interfaceTypeSpecOf(Class1::class.name))
            .isEqualTo(TypeSpec.interfaceBuilder(ClassName.get(Class1::class.java)).build())

        assertThat(anonymousTypeSpecOf("Anonymous1"))
            .isEqualTo(TypeSpec.anonymousClassBuilder("Anonymous1").build())
        assertThat(anonymousTypeSpecOf(codeBlockOf("Anonymous2")))
            .isEqualTo(TypeSpec.anonymousClassBuilder(CodeBlock.of("Anonymous2")).build())

        assertThat(annotationTypeSpecOf("MyClass"))
            .isEqualTo(TypeSpec.annotationBuilder("MyClass").build())
        assertThat(annotationTypeSpecOf(Class1::class.name))
            .isEqualTo(TypeSpec.annotationBuilder(ClassName.get(Class1::class.java)).build())
    }

    @Test
    fun build() {
        assertThat(
            buildClassTypeSpec("MyClass") {
                addJavadoc("text1")
            },
        ).isEqualTo(
            TypeSpec
                .classBuilder("MyClass")
                .addJavadoc("text1")
                .build(),
        )
        assertThat(
            buildClassTypeSpec(Class1::class.name) {
                addJavadoc("text2")
            },
        ).isEqualTo(
            TypeSpec
                .classBuilder(ClassName.get(Class1::class.java))
                .addJavadoc("text2")
                .build(),
        )

        assertThat(
            buildInterfaceTypeSpec("MyClass") {
                addJavadoc("text1")
            },
        ).isEqualTo(
            TypeSpec
                .interfaceBuilder("MyClass")
                .addJavadoc("text1")
                .build(),
        )
        assertThat(
            buildInterfaceTypeSpec(Class1::class.name) {
                addJavadoc("text2")
            },
        ).isEqualTo(
            TypeSpec
                .interfaceBuilder(ClassName.get(Class1::class.java))
                .addJavadoc("text2")
                .build(),
        )

        assertThat(
            buildEnumTypeSpec("MyClass") {
                addEnumConstant("A")
            },
        ).isEqualTo(
            TypeSpec
                .enumBuilder("MyClass")
                .addEnumConstant("A")
                .build(),
        )
        assertThat(
            buildEnumTypeSpec(Class1::class.name) {
                addEnumConstant("A")
            },
        ).isEqualTo(
            TypeSpec
                .enumBuilder(ClassName.get(Class1::class.java))
                .addEnumConstant("A")
                .build(),
        )

        assertThat(
            buildAnonymousTypeSpec("Anonymous1") {
                addJavadoc("text1")
            },
        ).isEqualTo(
            TypeSpec
                .anonymousClassBuilder("Anonymous1")
                .addJavadoc("text1")
                .build(),
        )
        assertThat(
            buildAnonymousTypeSpec(codeBlockOf("Anonymous2")) {
                addJavadoc("text2")
            },
        ).isEqualTo(
            TypeSpec
                .anonymousClassBuilder(CodeBlock.of("Anonymous2"))
                .addJavadoc("text2")
                .build(),
        )

        assertThat(
            buildAnnotationTypeSpec("MyClass") {
                addJavadoc("text1")
            },
        ).isEqualTo(
            TypeSpec
                .annotationBuilder("MyClass")
                .addJavadoc("text1")
                .build(),
        )
        assertThat(
            buildAnnotationTypeSpec(Class1::class.name) {
                addJavadoc("text2")
            },
        ).isEqualTo(
            TypeSpec
                .annotationBuilder(ClassName.get(Class1::class.java))
                .addJavadoc("text2")
                .build(),
        )
    }
}

@ExtendWith(MockitoExtension::class)
class TypeSpecHandlerTest {
    private val typeSpecs = mutableListOf<TypeSpec>()

    @Spy private val types: TypeSpecHandler =
        object : TypeSpecHandler {
            override fun add(type: TypeSpec) {
                typeSpecs += type
            }
        }

    private fun types(configuration: TypeSpecHandlerScope.() -> Unit) =
        TypeSpecHandlerScope
            .of(types)
            .configuration()

    @Test
    fun add() {
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
        assertThat(typeSpecs).containsExactly(
            TypeSpec.classBuilder("Class1").build(),
            TypeSpec.classBuilder(Annotation1::class.name).build(),
            TypeSpec.classBuilder("Class2").addJavadoc("text2").build(),
            TypeSpec
                .classBuilder(Annotation2::class.name)
                .addJavadoc("text2")
                .build(),
            TypeSpec.interfaceBuilder("Interface1").build(),
            TypeSpec.interfaceBuilder(Annotation1::class.name).build(),
            TypeSpec
                .interfaceBuilder("Interface2")
                .addJavadoc("text2")
                .build(),
            TypeSpec
                .interfaceBuilder(Annotation2::class.name)
                .addJavadoc("text2")
                .build(),
            TypeSpec.enumBuilder("Enum2").addEnumConstant("B").build(),
            TypeSpec
                .enumBuilder(Annotation1::class.name)
                .addEnumConstant("B")
                .build(),
            TypeSpec.anonymousClassBuilder("Anonymous1").build(),
            TypeSpec.anonymousClassBuilder(CodeBlock.of("")).build(),
            TypeSpec
                .anonymousClassBuilder("Anonymous2")
                .addJavadoc("text2")
                .build(),
            TypeSpec
                .anonymousClassBuilder(CodeBlock.of(""))
                .addJavadoc("text2")
                .build(),
            TypeSpec.annotationBuilder("Annotation1").build(),
            TypeSpec.annotationBuilder(Annotation1::class.name).build(),
            TypeSpec
                .annotationBuilder("Annotation2")
                .addJavadoc("text2")
                .build(),
            TypeSpec
                .annotationBuilder(Annotation2::class.name)
                .addJavadoc("text2")
                .build(),
        )
        verify(types, times(18)).add(any<TypeSpec>())
    }

    @Test
    fun adding() {
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
        assertThat(typeSpecs).containsExactly(
            TypeSpec.classBuilder("Class1").build(),
            TypeSpec.classBuilder("Class2").addJavadoc("text2").build(),
            TypeSpec.interfaceBuilder("Interface1").build(),
            TypeSpec
                .interfaceBuilder("Interface2")
                .addJavadoc("text2")
                .build(),
            TypeSpec.enumBuilder("Enum1").addEnumConstant("A").build(),
            TypeSpec.enumBuilder("Enum2").addEnumConstant("B").build(),
            TypeSpec.anonymousClassBuilder("Anonymous1").build(),
            TypeSpec
                .anonymousClassBuilder("Anonymous2")
                .addJavadoc("text2")
                .build(),
            TypeSpec.annotationBuilder("Annotation1").build(),
            TypeSpec
                .annotationBuilder("Annotation2")
                .addJavadoc("text2")
                .build(),
        )
        verify(types, times(10)).add(any<TypeSpec>())
    }

    @Test
    fun invoke() {
        types {
            "Class1" { addJavadoc("text1") }
            "Class2" { addJavadoc("text2") }
        }
        assertThat(typeSpecs).containsExactly(
            TypeSpec.classBuilder("Class1").addJavadoc("text1").build(),
            TypeSpec.classBuilder("Class2").addJavadoc("text2").build(),
        )
        verify(types, times(2)).add(any<TypeSpec>())
    }
}

class TypeSpecBuilderTest {
    @Test
    fun of() {
        assertThat(classTypeSpecOf("MyClass"))
            .isEqualTo(TypeSpec.classBuilder("MyClass").build())
        assertThat(classTypeSpecOf(Class1::class.name))
            .isEqualTo(TypeSpec.classBuilder(ClassName.get(Class1::class.java)).build())

        assertThat(interfaceTypeSpecOf("MyClass"))
            .isEqualTo(TypeSpec.interfaceBuilder("MyClass").build())
        assertThat(interfaceTypeSpecOf(Class1::class.name))
            .isEqualTo(TypeSpec.interfaceBuilder(ClassName.get(Class1::class.java)).build())

        assertThat(anonymousTypeSpecOf("Anonymous1"))
            .isEqualTo(TypeSpec.anonymousClassBuilder("Anonymous1").build())
        assertThat(anonymousTypeSpecOf(codeBlockOf("Anonymous2")))
            .isEqualTo(TypeSpec.anonymousClassBuilder(CodeBlock.of("Anonymous2")).build())

        assertThat(annotationTypeSpecOf("MyClass"))
            .isEqualTo(TypeSpec.annotationBuilder("MyClass").build())
        assertThat(annotationTypeSpecOf(Class1::class.name))
            .isEqualTo(TypeSpec.annotationBuilder(ClassName.get(Class1::class.java)).build())
    }

    @Test
    fun annotations() {
        assertThat(
            buildClassTypeSpec("MyClass") {
                annotations.add(Field1::class)
                annotations {
                    add(Field2::class)
                }
            },
        ).isEqualTo(
            TypeSpec
                .classBuilder("MyClass")
                .addAnnotation(Field1::class.java)
                .addAnnotation(Field2::class.java)
                .build(),
        )
    }

    @Test
    fun fields() {
        assertThat(
            buildClassTypeSpec("MyClass") {
                fields.add(INT, "field1", PUBLIC)
                fields {
                    add(CHAR, "field2", PRIVATE)
                }
            },
        ).isEqualTo(
            TypeSpec
                .classBuilder("MyClass")
                .addField(TypeName.INT, "field1", Modifier.PUBLIC)
                .addField(TypeName.CHAR, "field2", Modifier.PRIVATE)
                .build(),
        )
    }

    @Test
    fun methods() {
        assertThat(
            buildClassTypeSpec("MyClass") {
                methods.add("method1")
                methods {
                    add("method2")
                }
            },
        ).isEqualTo(
            TypeSpec
                .classBuilder("MyClass")
                .addMethod(MethodSpec.methodBuilder("method1").build())
                .addMethod(MethodSpec.methodBuilder("method2").build())
                .build(),
        )
    }

    @Test
    fun types() {
        assertThat(
            buildClassTypeSpec("MyClass") {
                types.addClass(Class1::class.name)
                types {
                    addClass(Class2::class.name)
                }
            },
        ).isEqualTo(
            TypeSpec
                .classBuilder("MyClass")
                .addType(TypeSpec.classBuilder(ClassName.get(Class1::class.java)).build())
                .addType(TypeSpec.classBuilder(ClassName.get(Class2::class.java)).build())
                .build(),
        )
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
                assertThat(modifiers.isEmpty()).isFalse()
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
                assertThat(typeVariables.isEmpty()).isFalse()
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
                assertThat(superinterfaces.isEmpty()).isFalse()
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
            TypeSpec.classBuilder("class1").addStaticBlock(CodeBlock.of("some code")).build(),
        )
        assertThat(
            buildClassTypeSpec("class1") { addStaticBlock(codeBlockOf("some code")) },
        ).isEqualTo(
            TypeSpec.classBuilder("class1").addStaticBlock(CodeBlock.of("some code")).build(),
        )
    }

    @Test
    fun addInitializerBlock() {
        assertThat(
            buildClassTypeSpec("class1") { addInitializerBlock("some code") },
        ).isEqualTo(
            TypeSpec.classBuilder("class1").addInitializerBlock(CodeBlock.of("some code")).build(),
        )
        assertThat(
            buildClassTypeSpec("class1") { addInitializerBlock(codeBlockOf("some code")) },
        ).isEqualTo(
            TypeSpec.classBuilder("class1").addInitializerBlock(CodeBlock.of("some code")).build(),
        )
    }

    @Test
    fun alwaysQualify() {
        assertThat(
            buildClassTypeSpec("class1") {
                alwaysQualify("name1", "name2")
                assertThat(alwaysQualifiedNames.isEmpty()).isFalse()
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
