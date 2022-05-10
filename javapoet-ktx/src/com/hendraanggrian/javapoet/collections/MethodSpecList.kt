package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.hendraanggrian.javapoet.SpecMarker
import com.hendraanggrian.javapoet.buildConstructorMethodSpec
import com.hendraanggrian.javapoet.buildMethodSpec
import com.squareup.javapoet.MethodSpec

/** A [MethodSpecList] is responsible for managing a set of method instances. */
open class MethodSpecList internal constructor(actualList: MutableList<MethodSpec>) :
    MutableList<MethodSpec> by actualList {

    /** Add method from name. */
    fun add(name: String): MethodSpec = MethodSpec.methodBuilder(name).build().also(::add)

    /** Add method from name with custom initialization [configuration]. */
    fun add(name: String, configuration: MethodSpecBuilder.() -> Unit): MethodSpec =
        buildMethodSpec(name, configuration).also(::add)

    /** Add constructor method. */
    fun addConstructor(): MethodSpec = MethodSpec.constructorBuilder().build().also(::add)

    /** Add constructor method with custom initialization [configuration]. */
    fun addConstructor(configuration: MethodSpecBuilder.() -> Unit): MethodSpec =
        buildConstructorMethodSpec(configuration).also(::add)

    /** Convenient method to add method with operator function. */
    inline operator fun plusAssign(name: String) {
        add(name)
    }
}

/** Receiver for the `methods` block providing an extended set of operators for the configuration. */
@SpecMarker
class MethodSpecListScope internal constructor(actualList: MutableList<MethodSpec>) : MethodSpecList(actualList) {

    /** @see MethodSpecList.add */
    operator fun String.invoke(configuration: MethodSpecBuilder.() -> Unit): MethodSpec = add(this, configuration)
}
