package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.Annotation1
import com.hendraanggrian.javapoet.internal.Field1
import com.hendraanggrian.javapoet.internal.Field2
import com.hendraanggrian.javapoet.internal.Field3
import com.hendraanggrian.javapoet.internal.Field4
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeVariableName
import javax.lang.model.element.Modifier
import kotlin.test.Test
import kotlin.test.assertEquals

class MethodSpecBuilderTest {

    @Test
    fun name() {
        assertEquals(
            buildMethodSpec("method1") { name = "method2" },
            MethodSpec.methodBuilder("method1").setName("method2").build()
        )
    }

    @Test
    fun javadoc() {
        assertEquals(
            buildMethodSpec("method1") { javadoc.append("some doc") },
            MethodSpec.methodBuilder("method1").addJavadoc("some doc").build()
        )
    }

    @Test
    fun annotations() {
        assertEquals(
            buildMethodSpec("method1") { annotations.add<Annotation1>() },
            MethodSpec.methodBuilder("method1").addAnnotation(Annotation1::class.java).build()
        )
    }

    @Test
    fun addModifiers() {
        assertEquals(
            buildMethodSpec("method1") { addModifiers(PUBLIC, FINAL, STATIC) },
            MethodSpec.methodBuilder("method1").addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC).build()
        )
    }

    @Test
    fun typeVariables() {
        assertEquals(
            buildMethodSpec("method1") { typeVariables.add("typeVar1", Annotation1::class) },
            MethodSpec.methodBuilder("method1")
                .addTypeVariable(TypeVariableName.get("typeVar1", Annotation1::class.java))
                .build()
        )
    }

    @Test
    fun returns() {
        assertEquals(
            buildMethodSpec("method1") { returns = Field1::class.asClassName() },
            MethodSpec.methodBuilder("method1").returns(Field1::class.asClassName()).build()
        )
        assertEquals(
            buildMethodSpec("method2") { returns(Field2::class.java) },
            MethodSpec.methodBuilder("method2").returns(Field2::class.java).build()
        )
        assertEquals(
            buildMethodSpec("method3") { returns(Field3::class) },
            MethodSpec.methodBuilder("method3").returns(Field3::class.java).build()
        )
        assertEquals(
            buildMethodSpec("method4") { returns<Field4>() },
            MethodSpec.methodBuilder("method4").returns(Field4::class.java).build()
        )
    }

    @Test
    fun parameters() {
        assertEquals(
            buildMethodSpec("method1") { parameters { add<Field1>("parameter1") } },
            MethodSpec.methodBuilder("method1").addParameter(Field1::class.java, "parameter1").build()
        )
    }

    @Test
    fun isVarargs() {
        assertEquals(
            buildMethodSpec("method1") {
                parameters["args"] = Array::class
                isVarargs = true
            },
            MethodSpec.methodBuilder("method1")
                .addParameter(Array::class.java, "args")
                .varargs()
                .build()
        )
    }

    @Test
    fun addExceptions() {
        assertEquals(
            buildMethodSpec("method1") {
                addExceptions(
                    listOf(
                        Field1::class.asClassName(), Field2::class.asClassName(),
                        Field3::class.asClassName(), Field4::class.asClassName(),
                    )
                )
            },
            MethodSpec.methodBuilder("method1")
                .addExceptions(
                    listOf(
                        Field1::class.asClassName(), Field2::class.asClassName(),
                        Field3::class.asClassName(), Field4::class.asClassName(),
                    )
                ).build()
        )
    }

    @Test
    fun addException() {
        assertEquals(
            buildMethodSpec("method1") {
                addException(Field1::class.asClassName())
                addException(Field2::class.java)
                addException(Field3::class)
                addException<Field4>()
            },
            MethodSpec.methodBuilder("method1")
                .addException(Field1::class.java)
                .addException(Field2::class.java)
                .addException(Field3::class.java)
                .addException(Field4::class.java)
                .build()
        )
    }

    @Test
    fun addComment() {
        assertEquals(
            buildMethodSpec("method1") { addComment("some comment") },
            MethodSpec.methodBuilder("method1").addComment("some comment").build()
        )
    }

    @Test
    fun defaultValue() {
        assertEquals(
            buildMethodSpec("method1") { defaultValue("value1") },
            MethodSpec.methodBuilder("method1").defaultValue("value1").build()
        )
        assertEquals(
            buildMethodSpec("method2") { defaultValue = codeBlockOf("value2") },
            MethodSpec.methodBuilder("method2").defaultValue(CodeBlock.of("value2")).build()
        )
    }

    @Test
    fun append() {
        assertEquals(
            buildMethodSpec("method1") { append("some code") },
            MethodSpec.methodBuilder("method1").addCode("some code").build()
        )
    }

    @Test
    fun appendLine() {
        assertEquals(
            buildMethodSpec("method1") { appendLine("some code") },
            MethodSpec.methodBuilder("method1").addStatement("some code").build()
        )
    }

    @Test
    fun controlFlow() {
        assertEquals(
            buildMethodSpec("method1") {
                beginControlFlow("some code")
                nextControlFlow("another code")
                endControlFlow()
            },
            MethodSpec.methodBuilder("method1")
                .beginControlFlow("some code")
                .nextControlFlow("another code")
                .endControlFlow()
                .build()
        )
    }
}