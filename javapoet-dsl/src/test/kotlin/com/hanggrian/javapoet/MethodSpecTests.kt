package com.hanggrian.javapoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Annotation3
import com.example.Field1
import com.example.Field2
import com.example.Field3
import com.example.Field4
import com.example.Field5
import com.example.Field6
import com.google.common.truth.Truth.assertThat
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeVariableName
import javax.lang.model.element.Modifier
import kotlin.test.Test
import kotlin.test.assertFalse

class MethodSpecHandlerTest {
    @Test
    fun method() {
        assertThat(
            buildClassTypeSpec("test") {
                method("method1")
                method("method2") { javadoc("text2") }
                constructorMethod()
                constructorMethod { javadoc("text4") }
            }.methodSpecs,
        ).containsExactly(
            MethodSpec.methodBuilder("method1").build(),
            MethodSpec.methodBuilder("method2").addJavadoc("text2").build(),
            MethodSpec.constructorBuilder().build(),
            MethodSpec.constructorBuilder().addJavadoc("text4").build(),
        )
    }

    @Test
    fun methoding() {
        assertThat(
            buildClassTypeSpec("test") {
                val method1 by methoding()
                val method2 by methoding { javadoc("text2") }
            }.methodSpecs,
        ).containsExactly(
            MethodSpec.methodBuilder("method1").build(),
            MethodSpec.methodBuilder("method2").addJavadoc("text2").build(),
        )
    }

    @Test
    fun invoke() {
        assertThat(
            buildClassTypeSpec("test") {
                methods {
                    "method1" { javadoc("text1") }
                }
            }.methodSpecs,
        ).containsExactly(
            MethodSpec.methodBuilder("method1").addJavadoc("text1").build(),
        )
    }
}

class MethodSpecBuilderTest {
    @Test
    fun name() {
        assertThat(
            buildMethodSpec("method1") { name = "method2" },
        ).isEqualTo(
            MethodSpec.methodBuilder("method1").setName("method2").build(),
        )
    }

    @Test
    fun javadoc() {
        assertThat(
            buildMethodSpec("method1") {
                javadoc("javadoc1")
                javadoc(codeBlockOf("javadoc2"))
            },
        ).isEqualTo(
            MethodSpec
                .methodBuilder("method1")
                .addJavadoc("javadoc1")
                .addJavadoc(CodeBlock.of("javadoc2"))
                .build(),
        )
    }

    @Test
    fun annotation() {
        assertThat(
            buildMethodSpec("method1") {
                annotation(annotationSpecOf(Annotation1::class.name))
                assertFalse(annotations.isEmpty())
            },
        ).isEqualTo(
            MethodSpec
                .methodBuilder("method1")
                .addAnnotation(Annotation1::class.java)
                .build(),
        )
    }

    @Test
    fun modifiers() {
        assertThat(
            buildMethodSpec("method1") {
                modifiers(PUBLIC)
                modifiers += listOf(STATIC, FINAL)
                assertFalse(modifiers.isEmpty())
            },
        ).isEqualTo(
            MethodSpec
                .methodBuilder("method1")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(listOf(Modifier.STATIC, Modifier.FINAL))
                .build(),
        )
    }

    @Test
    fun typeVariable() {
        assertThat(
            buildMethodSpec("method1") {
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
            MethodSpec
                .methodBuilder("method1")
                .addTypeVariables(
                    listOf(
                        TypeVariableName.get("typeVar1", Annotation1::class.java),
                        TypeVariableName.get("typeVar2", Annotation2::class.java),
                    ),
                ).addTypeVariable(TypeVariableName.get("typeVar3", Annotation3::class.java))
                .build(),
        )
    }

    @Test
    fun returns() {
        assertThat(
            buildMethodSpec("method1") { returns = Field1::class.name },
        ).isEqualTo(
            MethodSpec.methodBuilder("method1").returns(Field1::class.name).build(),
        )
        assertThat(
            buildMethodSpec("method2") { returns(Field2::class) },
        ).isEqualTo(
            MethodSpec.methodBuilder("method2").returns(Field2::class.java).build(),
        )
        assertThat(
            buildMethodSpec("method3") { returns(Field3::class.java) },
        ).isEqualTo(
            MethodSpec.methodBuilder("method3").returns(Field3::class.java).build(),
        )
        assertThat(
            buildMethodSpec("method4") { returns<Field4>() },
        ).isEqualTo(
            MethodSpec.methodBuilder("method4").returns(Field4::class.java).build(),
        )
    }

    @Test
    fun parameter() {
        assertThat(
            buildMethodSpec("method1") {
                parameter(Field1::class.name, "parameter1")
                parameter(Field2::class, "parameter2")
                parameter<Field3>("parameter3")
                assertFalse(parameters.isEmpty())
            },
        ).isEqualTo(
            MethodSpec
                .methodBuilder("method1")
                .addParameter(Field1::class.java, "parameter1")
                .addParameter(Field2::class.java, "parameter2")
                .addParameter(Field3::class.java, "parameter3")
                .build(),
        )
    }

    @Test
    fun isVarargs() {
        assertThat(
            buildMethodSpec("method1") {
                parameter(OBJECT.array, "args")
                isVarargs = true
            },
        ).isEqualTo(
            MethodSpec
                .methodBuilder("method1")
                .addParameter(Array::class.java, "args")
                .varargs()
                .build(),
        )
    }

    @Test
    fun exceptions() {
        assertThat(
            buildMethodSpec("method1") {
                exceptions(
                    listOf(
                        Field1::class.name,
                        Field2::class.name,
                    ),
                )
                exception(Field3::class.name)
                exception(Field4::class.java)
                exception(Field5::class)
                exception<Field6>()
            },
        ).isEqualTo(
            MethodSpec
                .methodBuilder("method1")
                .addExceptions(
                    listOf(
                        Field1::class.name,
                        Field2::class.name,
                    ),
                ).addException(Field3::class.java)
                .addException(Field4::class.java)
                .addException(Field5::class.java)
                .addException(Field6::class.java)
                .build(),
        )
    }

    @Test
    fun comment() {
        assertThat(buildMethodSpec("method1") { comment("some comment") })
            .isEqualTo(MethodSpec.methodBuilder("method1").addComment("some comment").build())
    }

    @Test
    fun defaultValue() {
        assertThat(buildMethodSpec("method1") { defaultValue("value1") })
            .isEqualTo(
                MethodSpec
                    .methodBuilder("method1")
                    .defaultValue("value1")
                    .build(),
            )
        assertThat(buildMethodSpec("method2") { defaultValue = codeBlockOf("value2") })
            .isEqualTo(
                MethodSpec
                    .methodBuilder("method2")
                    .defaultValue(CodeBlock.of("value2"))
                    .build(),
            )
    }

    @Test
    fun append() {
        assertThat(
            buildMethodSpec("method1") {
                append("some code")
                append(codeBlockOf("some other code"))
            },
        ).isEqualTo(
            MethodSpec
                .methodBuilder("method1")
                .addCode("some code")
                .addCode(CodeBlock.of("some other code"))
                .build(),
        )
    }

    @Test
    fun appendLine() {
        assertThat(
            buildMethodSpec("method1") {
                appendLine("some code")
                appendLine(codeBlockOf("another code"))
            },
        ).isEqualTo(
            MethodSpec
                .methodBuilder("method1")
                .addStatement("some code")
                .addStatement(CodeBlock.of("another code"))
                .build(),
        )
    }

    @Test
    fun appendNamed() {
        assertThat(
            buildMethodSpec("method1") {
                appendNamed("format", mapOf("key1" to "value1", "key2" to "value2"))
            },
        ).isEqualTo(
            MethodSpec
                .methodBuilder("method1")
                .addNamedCode("format", mapOf("key1" to "value1", "key2" to "value2"))
                .build(),
        )
    }

    @Test
    fun controlFlow() {
        assertThat(
            buildMethodSpec("method1") {
                beginControlFlow("some code")
                nextControlFlow("another code")
                endControlFlow()
            },
        ).isEqualTo(
            MethodSpec
                .methodBuilder("method1")
                .beginControlFlow("some code")
                .nextControlFlow("another code")
                .endControlFlow()
                .build(),
        )
        assertThat(
            buildMethodSpec("method2") {
                beginControlFlow(codeBlockOf("some code"))
                nextControlFlow(codeBlockOf("another code"))
                endControlFlow(codeBlockOf("yet another code"))
            },
        ).isEqualTo(
            MethodSpec
                .methodBuilder("method2")
                .beginControlFlow(CodeBlock.of("some code"))
                .nextControlFlow(CodeBlock.of("another code"))
                .endControlFlow(CodeBlock.of("yet another code"))
                .build(),
        )
        assertThat(
            buildMethodSpec("method1") {
                beginControlFlow("some code")
                endControlFlow("format", "arg")
            },
        ).isEqualTo(
            MethodSpec
                .methodBuilder("method1")
                .beginControlFlow("some code")
                .endControlFlow("format", "arg")
                .build(),
        )
    }
}
