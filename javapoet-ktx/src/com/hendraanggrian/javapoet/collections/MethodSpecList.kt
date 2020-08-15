package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.hendraanggrian.javapoet.SpecDslMarker
import com.hendraanggrian.javapoet.buildConstructorMethodSpec
import com.hendraanggrian.javapoet.buildMethodSpec
import com.hendraanggrian.javapoet.emptyConstructorMethodSpec
import com.hendraanggrian.javapoet.methodSpecOf
import com.squareup.javapoet.MethodSpec

/** A [MethodSpecList] is responsible for managing a set of method instances. */
open class MethodSpecList internal constructor(actualList: MutableList<MethodSpec>) :
    MutableList<MethodSpec> by actualList {

    /** Add method from name. */
    fun add(name: String): Boolean = add(methodSpecOf(name))

    /** Add constructor method. */
    fun addConstructor(): Boolean = add(emptyConstructorMethodSpec())

    /** Add method from name with custom initialization [builderAction]. */
    inline fun add(
        name: String,
        builderAction: MethodSpecBuilder.() -> Unit
    ): Boolean = add(buildMethodSpec(name, builderAction))

    /** Add constructor method with custom initialization [builderAction]. */
    inline fun addConstructor(
        builderAction: MethodSpecBuilder.() -> Unit
    ): Boolean = add(buildConstructorMethodSpec(builderAction))
}

/** Receiver for the `methods` function type providing an extended set of operators for the configuration. */
@SpecDslMarker
class MethodSpecListScope(actualList: MutableList<MethodSpec>) : MethodSpecList(actualList) {

    /** Convenient method to add method with receiver type. */
    inline operator fun String.invoke(
        builderAction: MethodSpecBuilder.() -> Unit
    ): Boolean = add(this, builderAction)
}
