package com.hanggrian.javapoet

import com.example.Field1
import com.example.Field2
import com.example.Field3
import com.example.Field4
import com.example.Field5
import com.example.Field6
import com.example.Field7
import com.google.common.truth.Truth.assertThat
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import javax.lang.model.element.Modifier
import kotlin.test.Test

class FieldSpecCreatorTest {
    @Test
    fun of() =
        assertThat(fieldSpecOf(INT, "myField", PUBLIC))
            .isEqualTo(FieldSpec.builder(TypeName.INT, "myField", Modifier.PUBLIC).build())

    @Test
    fun build() =
        assertThat(
            buildFieldSpec(INT, "myField", PUBLIC) {
                setInitializer("value1")
            },
        ).isEqualTo(
            FieldSpec
                .builder(TypeName.INT, "myField", Modifier.PUBLIC)
                .initializer("value1")
                .build(),
        )
}

@ExtendWith(MockitoExtension::class)
class FieldSpecHandlerTest {
    private val fieldSpecs = mutableListOf<FieldSpec>()

    @Spy private val fields: FieldSpecHandler =
        object : FieldSpecHandler {
            override fun add(field: FieldSpec) {
                fieldSpecs += field
            }
        }

    private fun fields(configuration: FieldSpecHandlerScope.() -> Unit) =
        FieldSpecHandlerScope
            .of(fields)
            .configuration()

    @Test
    fun add() {
        fields.add(Field1::class.name, "field1")
        fields.add(Field2::class.java, "field2")
        fields.add(Field3::class, "field3")
        fields.add<Field4>("field4")
        fields.add(Field5::class.name, "field5") { setInitializer("value5") }
        fields.add(Field6::class.java, "field6") { setInitializer("value6") }
        fields.add(Field7::class, "field7") { setInitializer("value7") }
        assertThat(fieldSpecs).containsExactly(
            FieldSpec.builder(Field1::class.java, "field1").build(),
            FieldSpec.builder(Field2::class.java, "field2").build(),
            FieldSpec.builder(Field3::class.java, "field3").build(),
            FieldSpec.builder(Field4::class.java, "field4").build(),
            FieldSpec.builder(Field5::class.java, "field5").initializer("value5").build(),
            FieldSpec.builder(Field6::class.java, "field6").initializer("value6").build(),
            FieldSpec.builder(Field7::class.java, "field7").initializer("value7").build(),
        )
        verify(fields, times(7)).add(any<FieldSpec>())
    }

    @Test
    fun adding() {
        val field1 by fields.adding(Field1::class.name)
        val field2 by fields.adding(Field2::class.java)
        val field3 by fields.adding(Field3::class)
        val field4 by fields.adding(Field4::class.name) { setInitializer("value4") }
        val field5 by fields.adding(Field5::class.java) { setInitializer("value5") }
        val field6 by fields.adding(Field6::class) { setInitializer("value6") }
        assertThat(fieldSpecs).containsExactly(
            FieldSpec.builder(Field1::class.java, "field1").build(),
            FieldSpec.builder(Field2::class.java, "field2").build(),
            FieldSpec.builder(Field3::class.java, "field3").build(),
            FieldSpec.builder(Field4::class.java, "field4").initializer("value4").build(),
            FieldSpec.builder(Field5::class.java, "field5").initializer("value5").build(),
            FieldSpec.builder(Field6::class.java, "field6").initializer("value6").build(),
        )
        verify(fields, times(6)).add(any<FieldSpec>())
    }

    @Test
    fun invoke() {
        fields {
            "field1"(Field1::class.name) { setInitializer("value1") }
            "field2"(Field2::class.java) { setInitializer("value2") }
            "field3"(Field3::class) { setInitializer("value3") }
        }
        assertThat(fieldSpecs).containsExactly(
            FieldSpec.builder(Field1::class.java, "field1").initializer("value1").build(),
            FieldSpec.builder(Field2::class.java, "field2").initializer("value2").build(),
            FieldSpec.builder(Field3::class.java, "field3").initializer("value3").build(),
        )
        verify(fields, times(3)).add(any<FieldSpec>())
    }
}

class FieldSpecBuilderTest {
    @Test
    fun annotations() =
        assertThat(
            buildFieldSpec(INT, "myField", PUBLIC) {
                annotations.add(Field1::class)
                annotations {
                    add(Field2::class)
                }
            },
        ).isEqualTo(
            FieldSpec
                .builder(TypeName.INT, "myField", Modifier.PUBLIC)
                .addAnnotation(Field1::class.java)
                .addAnnotation(Field2::class.java)
                .build(),
        )

    @Test
    fun addJavadoc() =
        assertThat(
            buildFieldSpec(Field1::class.name, "field1") {
                addJavadoc("javadoc1")
                addJavadoc(codeBlockOf("javadoc2"))
            },
        ).isEqualTo(
            FieldSpec
                .builder(Field1::class.java, "field1")
                .addJavadoc("javadoc1")
                .addJavadoc(CodeBlock.of("javadoc2"))
                .build(),
        )

    @Test
    fun addModifiers() =
        assertThat(
            buildFieldSpec(Field1::class.name, "field1") {
                addModifiers(PUBLIC)
                modifiers += listOf(STATIC, FINAL)
                assertThat(modifiers.isEmpty()).isFalse()
            },
        ).isEqualTo(
            FieldSpec
                .builder(Field1::class.java, "field1")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC, Modifier.FINAL)
                .build(),
        )

    @Test
    fun initializer() {
        assertThat(
            buildFieldSpec(Field1::class.name, "field1") { setInitializer("value1") },
        ).isEqualTo(
            FieldSpec
                .builder(Field1::class.java, "field1")
                .initializer("value1")
                .build(),
        )
        assertThat(
            buildFieldSpec(Field2::class.name, "field2") { initializer = codeBlockOf("value2") },
        ).isEqualTo(
            FieldSpec
                .builder(Field2::class.java, "field2")
                .initializer(CodeBlock.of("value2"))
                .build(),
        )
    }
}
