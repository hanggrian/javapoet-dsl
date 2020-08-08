package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.hendraanggrian.javapoet.buildConstructorMethodSpec
import com.hendraanggrian.javapoet.buildMethodSpec
import com.hendraanggrian.javapoet.emptyConstructorMethodSpec
import com.hendraanggrian.javapoet.methodSpecOf
import com.squareup.javapoet.MethodSpec

/** A [MethodSpecList] is responsible for managing a set of method instances. */
open class MethodSpecList internal constructor(actualList: MutableList<MethodSpec>) :
    MutableList<MethodSpec> by actualList {

    /** Add method from [name], returning the method added. */
    fun add(name: String): MethodSpec = methodSpecOf(name).also(::plusAssign)

    /** Add constructor method, returning the method added. */
    fun addConstructor(): MethodSpec = emptyConstructorMethodSpec().also(::plusAssign)

    /** Add method from [name] with custom initialization [builderAction], returning the method added. */
    inline fun add(
        name: String,
        builderAction: MethodSpecBuilder.() -> Unit
    ): MethodSpec = buildMethodSpec(name, builderAction).also(::plusAssign)

    /** Add constructor method with custom initialization [builderAction], returning the method added. */
    inline fun addConstructor(
        builderAction: MethodSpecBuilder.() -> Unit
    ): MethodSpec = buildConstructorMethodSpec(builderAction).also(::plusAssign)
}

/** Receiver for the `methods` function type providing an extended set of operators for the configuration. */
@JavapoetDslMarker
class MethodSpecListScope(actualList: MutableList<MethodSpec>) : MethodSpecList(actualList) {

    /** Convenient method to add method with receiver type. */
    inline operator fun String.invoke(
        builderAction: MethodSpecBuilder.() -> Unit
    ): MethodSpec = add(this, builderAction)
}
