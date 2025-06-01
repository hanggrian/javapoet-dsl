package com.hanggrian.javapoet

import com.example.Annotation1
import com.example.Annotation2
import com.example.Field1
import com.example.Field2
import com.example.Field3
import com.example.Field4
import com.example.Field5
import com.example.Field6
import com.google.common.truth.Truth.assertThat
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import javax.lang.model.element.Modifier
import kotlin.test.Test

class MethodSpecCreatorTest {
    @Test
    fun of() {
        assertThat(methodSpecOf("myMethod"))
            .isEqualTo(MethodSpec.methodBuilder("myMethod").build())
        assertThat(emptyConstructorMethodSpec())
            .isEqualTo(MethodSpec.constructorBuilder().build())
    }

    @Test
    fun build() {
        assertThat(
            buildMethodSpec("myMethod") {
                addJavadoc("text1")
            },
        ).isEqualTo(
            MethodSpec
                .methodBuilder("myMethod")
                .addJavadoc("text1")
                .build(),
        )
        assertThat(
            buildConstructorMethodSpec {
                addJavadoc("text1")
            },
        ).isEqualTo(
            MethodSpec
                .constructorBuilder()
                .addJavadoc("text1")
                .build(),
        )
    }
}

@ExtendWith(MockitoExtension::class)
class MethodSpecHandlerTest {
    private val methodSpecs = mutableListOf<MethodSpec>()

    @Spy private val methods: MethodSpecHandler =
        object : MethodSpecHandler {
            override fun add(method: MethodSpec) {
                methodSpecs += method
            }
        }

    private fun methods(configuration: MethodSpecHandlerScope.() -> Unit) =
        MethodSpecHandlerScope
            .of(methods)
            .configuration()

    @Test
    fun add() {
        methods.add("method1")
        methods.add("method2") { addJavadoc("text2") }
        methods.addConstructor()
        methods.addConstructor { addJavadoc("text4") }
        assertThat(methodSpecs).containsExactly(
            MethodSpec.methodBuilder("method1").build(),
            MethodSpec.methodBuilder("method2").addJavadoc("text2").build(),
            MethodSpec.constructorBuilder().build(),
            MethodSpec.constructorBuilder().addJavadoc("text4").build(),
        )
        verify(methods, times(4)).add(any<MethodSpec>())
    }

    @Test
    fun adding() {
        val method1 by methods.adding()
        val method2 by methods.adding { addJavadoc("text2") }
        assertThat(methodSpecs).containsExactly(
            MethodSpec.methodBuilder("method1").build(),
            MethodSpec.methodBuilder("method2").addJavadoc("text2").build(),
        )
        verify(methods, times(2)).add(any<MethodSpec>())
    }

    @Test
    fun invoke() {
        methods {
            "method1" { addJavadoc("text1") }
        }
        assertThat(methodSpecs).containsExactly(
            MethodSpec.methodBuilder("method1").addJavadoc("text1").build(),
        )
        verify(methods, times(1)).add(any<MethodSpec>())
    }
}

class MethodSpecBuilderTest {
    @Test
    fun annotations() =
        assertThat(
            buildMethodSpec("myMethod") {
                annotations.add(Field1::class)
                annotations {
                    add(Field2::class)
                }
            },
        ).isEqualTo(
            MethodSpec
                .methodBuilder("myMethod")
                .addAnnotation(Field1::class.java)
                .addAnnotation(Field2::class.java)
                .build(),
        )

    @Test
    fun parameters() =
        assertThat(
            buildMethodSpec("myMethod") {
                parameters.add(INT, "param1", PUBLIC)
                parameters {
                    add(CHAR, "param2", PRIVATE)
                }
            },
        ).isEqualTo(
            MethodSpec
                .methodBuilder("myMethod")
                .addParameter(TypeName.INT, "param1", Modifier.PUBLIC)
                .addParameter(TypeName.CHAR, "param2", Modifier.PRIVATE)
                .build(),
        )

    @Test
    fun name() =
        assertThat(
            buildMethodSpec("method1") { name = "method2" },
        ).isEqualTo(
            MethodSpec.methodBuilder("method1").setName("method2").build(),
        )

    @Test
    fun addJavadoc() =
        assertThat(
            buildMethodSpec("method1") {
                addJavadoc("javadoc1")
                addJavadoc(codeBlockOf("javadoc2"))
            },
        ).isEqualTo(
            MethodSpec
                .methodBuilder("method1")
                .addJavadoc("javadoc1")
                .addJavadoc(CodeBlock.of("javadoc2"))
                .build(),
        )

    @Test
    fun addModifiers() =
        assertThat(
            buildMethodSpec("method1") {
                addModifiers(PUBLIC)
                modifiers += listOf(STATIC, FINAL)
                assertThat(modifiers.isEmpty()).isFalse()
            },
        ).isEqualTo(
            MethodSpec
                .methodBuilder("method1")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(listOf(Modifier.STATIC, Modifier.FINAL))
                .build(),
        )

    @Test
    fun addTypeVariable() =
        assertThat(
            buildMethodSpec("method1") {
                addTypeVariables(
                    "typeVar1".genericsBy(Annotation1::class),
                    "typeVar2".genericsBy(Annotation2::class),
                )
                assertThat(typeVariables.isEmpty()).isFalse()
            },
        ).isEqualTo(
            MethodSpec
                .methodBuilder("method1")
                .addTypeVariables(
                    listOf(
                        TypeVariableName.get("typeVar1", Annotation1::class.java),
                        TypeVariableName.get("typeVar2", Annotation2::class.java),
                    ),
                ).build(),
        )

    @Test
    fun returns() {
        assertThat(
            buildMethodSpec("method1") { returns = Field1::class.name },
        ).isEqualTo(
            MethodSpec.methodBuilder("method1").returns(Field1::class.name).build(),
        )
        assertThat(
            buildMethodSpec("method2") { setReturns(Field2::class) },
        ).isEqualTo(
            MethodSpec.methodBuilder("method2").returns(Field2::class.java).build(),
        )
        assertThat(
            buildMethodSpec("method3") { setReturns(Field3::class.java) },
        ).isEqualTo(
            MethodSpec.methodBuilder("method3").returns(Field3::class.java).build(),
        )
        assertThat(
            buildMethodSpec("method4") { setReturns<Field4>() },
        ).isEqualTo(
            MethodSpec.methodBuilder("method4").returns(Field4::class.java).build(),
        )
    }

    @Test
    fun isVarargs() =
        assertThat(
            buildMethodSpec("method1") {
                parameters.add(OBJECT.array, "args")
                isVarargs = true
            },
        ).isEqualTo(
            MethodSpec
                .methodBuilder("method1")
                .addParameter(Array::class.java, "args")
                .varargs()
                .build(),
        )

    @Test
    fun addExceptions() =
        assertThat(
            buildMethodSpec("method1") {
                addExceptions(
                    listOf(
                        Field1::class.name,
                        Field2::class.name,
                    ),
                )
                addException(Field3::class.name)
                addException(Field4::class.java)
                addException(Field5::class)
                addException<Field6>()
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

    @Test
    fun addComment() =
        assertThat(buildMethodSpec("method1") { addComment("some comment") })
            .isEqualTo(MethodSpec.methodBuilder("method1").addComment("some comment").build())

    @Test
    fun defaultValue() {
        assertThat(buildMethodSpec("method1") { setDefaultValue("value1") })
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
    fun append() =
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

    @Test
    fun appendLine() =
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

    @Test
    fun appendNamed() =
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
