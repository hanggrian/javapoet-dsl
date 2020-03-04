package com.hendraanggrian.javapoet.dsl

import com.google.common.truth.Truth.assertThat
import com.hendraanggrian.javapoet.classOf
import com.hendraanggrian.javapoet.buildParameter
import com.squareup.javapoet.ParameterSpec
import kotlin.test.Test

class ParameterSpecContainerTest {
    private val specs = mutableListOf<ParameterSpec>()
    private val container = object : ParameterSpecContainer() {
        override fun add(spec: ParameterSpec) {
            specs += spec
        }
    }

    private inline fun container(configuration: ParameterSpecContainerScope.() -> Unit) =
        ParameterSpecContainerScope(container).configuration()

    @Test fun nativeSpec() {
        container.add(buildParameter<Parameter1>("parameter1"))
        container += buildParameter<Parameter2>("parameter2")
        assertThat(specs).containsExactly(
            buildParameter<Parameter1>("parameter1"),
            buildParameter<Parameter2>("parameter2")
        )
    }

    @Test fun className() {
        val packageName = "com.hendraanggrian.javapoet.dsl.ParameterSpecContainerTest"
        container.add(packageName.classOf("Parameter1"), "parameter1")
        container["parameter2"] = packageName.classOf("Parameter2")
        container { "parameter3"(packageName.classOf("Parameter3")) { } }
        assertThat(specs).containsExactly(
            buildParameter<Parameter1>("parameter1"),
            buildParameter<Parameter2>("parameter2"),
            buildParameter<Parameter3>("parameter3")
        )
    }

    @Test fun javaClass() {
        container.add(Parameter1::class.java, "parameter1")
        container["parameter2"] = Parameter2::class.java
        container { "parameter3"(Parameter3::class.java) { } }
        assertThat(specs).containsExactly(
            buildParameter<Parameter1>("parameter1"),
            buildParameter<Parameter2>("parameter2"),
            buildParameter<Parameter3>("parameter3")
        )
    }

    @Test fun kotlinClass() {
        container.add(Parameter1::class, "parameter1")
        container["parameter2"] = Parameter2::class
        container { "parameter3"(Parameter3::class) { } }
        assertThat(specs).containsExactly(
            buildParameter<Parameter1>("parameter1"),
            buildParameter<Parameter2>("parameter2"),
            buildParameter<Parameter3>("parameter3")
        )
    }

    @Test fun reifiedType() {
        container.add<Parameter1>("parameter1")
        container { "parameter2"<Parameter2> { } }
        assertThat(specs).containsExactly(
            buildParameter<Parameter1>("parameter1"),
            buildParameter<Parameter2>("parameter2")
        )
    }

    class Parameter1
    class Parameter2
    class Parameter3
}