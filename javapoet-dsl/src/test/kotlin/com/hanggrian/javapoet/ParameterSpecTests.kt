package com.hanggrian.javapoet

import com.example.Field1
import com.example.Field2
import com.example.Parameter1
import com.example.Parameter2
import com.example.Parameter3
import com.example.Parameter4
import com.example.Parameter5
import com.example.Parameter6
import com.example.Parameter7
import com.google.common.truth.Truth.assertThat
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import javax.lang.model.element.Modifier
import kotlin.test.Test

class ParameterSpecCreatorTest {
    @Test
    fun of() {
        assertThat(parameterSpecOf(INT, "myParameter", FINAL))
            .isEqualTo(ParameterSpec.builder(TypeName.INT, "myParameter", Modifier.FINAL).build())
    }

    @Test
    fun build() {
        assertThat(
            buildParameterSpec(INT, "myParameter", FINAL) {
                addJavadoc("text1")
            },
        ).isEqualTo(
            ParameterSpec
                .builder(TypeName.INT, "myParameter", Modifier.FINAL)
                .addJavadoc("text1")
                .build(),
        )
    }
}

@ExtendWith(MockitoExtension::class)
class ParameterSpecHandlerTest {
    private val parameterSpecs = mutableListOf<ParameterSpec>()

    @Spy private val parameters: ParameterSpecHandler =
        object : ParameterSpecHandler {
            override fun add(parameter: ParameterSpec) {
                parameterSpecs += parameter
            }
        }

    private fun parameters(configuration: ParameterSpecHandlerScope.() -> Unit) =
        ParameterSpecHandlerScope
            .of(parameters)
            .configuration()

    @Test
    fun add() {
        parameters.add(Parameter1::class.name, "parameter1")
        parameters.add(Parameter2::class.java, "parameter2")
        parameters.add(Parameter3::class, "parameter3")
        parameters.add<Parameter4>("parameter4")
        parameters.add(Parameter5::class.name, "parameter5") { addJavadoc("text5") }
        parameters.add(Parameter6::class.java, "parameter6") { addJavadoc("text6") }
        parameters.add(Parameter7::class, "parameter7") { addJavadoc("text7") }
        assertThat(parameterSpecs).containsExactly(
            ParameterSpec.builder(Parameter1::class.java, "parameter1").build(),
            ParameterSpec.builder(Parameter2::class.java, "parameter2").build(),
            ParameterSpec.builder(Parameter3::class.java, "parameter3").build(),
            ParameterSpec.builder(Parameter4::class.java, "parameter4").build(),
            ParameterSpec.builder(Parameter5::class.java, "parameter5").addJavadoc("text5").build(),
            ParameterSpec.builder(Parameter6::class.java, "parameter6").addJavadoc("text6").build(),
            ParameterSpec.builder(Parameter7::class.java, "parameter7").addJavadoc("text7").build(),
        )
        verify(parameters, times(7)).add(any<ParameterSpec>())
    }

    @Test
    fun adding() {
        val parameter1 by parameters.adding(Parameter1::class.name)
        val parameter2 by parameters.adding(Parameter2::class.java)
        val parameter3 by parameters.adding(Parameter3::class)
        val parameter4 by parameters.adding(Parameter4::class.name) { addJavadoc("text4") }
        val parameter5 by parameters.adding(Parameter5::class.java) { addJavadoc("text5") }
        val parameter6 by parameters.adding(Parameter6::class) { addJavadoc("text6") }
        assertThat(parameterSpecs).containsExactly(
            ParameterSpec.builder(Parameter1::class.java, "parameter1").build(),
            ParameterSpec.builder(Parameter2::class.java, "parameter2").build(),
            ParameterSpec.builder(Parameter3::class.java, "parameter3").build(),
            ParameterSpec.builder(Parameter4::class.java, "parameter4").addJavadoc("text4").build(),
            ParameterSpec.builder(Parameter5::class.java, "parameter5").addJavadoc("text5").build(),
            ParameterSpec.builder(Parameter6::class.java, "parameter6").addJavadoc("text6").build(),
        )
        verify(parameters, times(6)).add(any<ParameterSpec>())
    }

    @Test
    fun invoke() {
        parameters {
            "parameter1"(Parameter1::class.name) { addJavadoc("text1") }
            "parameter2"(Parameter2::class.java) { addJavadoc("text2") }
            "parameter3"(Parameter3::class) { addJavadoc("text3") }
        }
        assertThat(parameterSpecs).containsExactly(
            ParameterSpec.builder(Parameter1::class.java, "parameter1").addJavadoc("text1").build(),
            ParameterSpec.builder(Parameter2::class.java, "parameter2").addJavadoc("text2").build(),
            ParameterSpec.builder(Parameter3::class.java, "parameter3").addJavadoc("text3").build(),
        )
        verify(parameters, times(3)).add(any<ParameterSpec>())
    }
}

class ParameterSpecBuilderTest {
    @Test
    fun annotations() =
        assertThat(
            buildParameterSpec(INT, "myParameter", FINAL) {
                annotations.add(Field1::class)
                annotations {
                    add(Field2::class)
                }
            },
        ).isEqualTo(
            ParameterSpec
                .builder(TypeName.INT, "myParameter", Modifier.FINAL)
                .addAnnotation(Field1::class.java)
                .addAnnotation(Field2::class.java)
                .build(),
        )

    @Test
    fun addJavadoc() =
        assertThat(
            buildParameterSpec(Field1::class.name, "parameter1") {
                addJavadoc("javadoc1")
                addJavadoc(codeBlockOf("javadoc2"))
            },
        ).isEqualTo(
            ParameterSpec
                .builder(Field1::class.java, "parameter1")
                .addJavadoc("javadoc1")
                .addJavadoc(CodeBlock.of("javadoc2"))
                .build(),
        )

    @Test
    fun addModifiers() =
        assertThat(
            buildParameterSpec(Field1::class.name, "parameter1") {
                addModifiers(PUBLIC)
                modifiers += listOf(FINAL)
                assertThat(modifiers.isEmpty()).isFalse()
            },
        ).isEqualTo(
            ParameterSpec
                .builder(Field1::class.java, "parameter1")
                .addModifiers(PUBLIC)
                .addModifiers(listOf(FINAL))
                .build(),
        )
}
