package com.hendraanggrian.javapoet.collections

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.classOf
import com.hendraanggrian.javapoet.parameterSpecOf
import kotlin.test.Test

class ParameterSpecListTest {
    private val container = ParameterSpecList(mutableListOf())

    private inline fun container(configuration: ParameterSpecListScope.() -> Unit) =
        ParameterSpecListScope(container).configuration()

    @Test fun nativeSpec() {
        container += parameterSpecOf<Parameter1>("parameter1")
        container += listOf(parameterSpecOf<Parameter2>("parameter2"))
        assertThat(container).containsExactly(
            parameterSpecOf<Parameter1>("parameter1"),
            parameterSpecOf<Parameter2>("parameter2")
        )
    }

    @Test fun className() {
        val packageName = "com.hendraanggrian.javapoet.collections.ParameterSpecListTest"
        container.add(packageName.classOf("Parameter1"), "parameter1")
        container["parameter2"] = packageName.classOf("Parameter2")
        container { "parameter3"(packageName.classOf("Parameter3")) { } }
        assertThat(container).containsExactly(
            parameterSpecOf<Parameter1>("parameter1"),
            parameterSpecOf<Parameter2>("parameter2"),
            parameterSpecOf<Parameter3>("parameter3")
        )
    }

    @Test fun javaClass() {
        container.add(Parameter1::class.java, "parameter1")
        container["parameter2"] = Parameter2::class.java
        container { "parameter3"(Parameter3::class.java) { } }
        assertThat(container).containsExactly(
            parameterSpecOf<Parameter1>("parameter1"),
            parameterSpecOf<Parameter2>("parameter2"),
            parameterSpecOf<Parameter3>("parameter3")
        )
    }

    @Test fun kotlinClass() {
        container.add(Parameter1::class, "parameter1")
        container["parameter2"] = Parameter2::class
        container { "parameter3"(Parameter3::class) { } }
        assertThat(container).containsExactly(
            parameterSpecOf<Parameter1>("parameter1"),
            parameterSpecOf<Parameter2>("parameter2"),
            parameterSpecOf<Parameter3>("parameter3")
        )
    }

    @Test fun reifiedType() {
        container.add<Parameter1>("parameter1")
        container { "parameter2"<Parameter2> { } }
        assertThat(container).containsExactly(
            parameterSpecOf<Parameter1>("parameter1"),
            parameterSpecOf<Parameter2>("parameter2")
        )
    }

    class Parameter1
    class Parameter2
    class Parameter3
}