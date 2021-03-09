package io.github.hendraanggrian.javapoet.collections

import com.squareup.javapoet.MethodSpec
import io.github.hendraanggrian.javapoet.MethodSpecBuilder
import io.github.hendraanggrian.javapoet.SpecDslMarker
import io.github.hendraanggrian.javapoet.buildConstructorMethodSpec
import io.github.hendraanggrian.javapoet.buildMethodSpec
import io.github.hendraanggrian.javapoet.emptyConstructorMethodSpec
import io.github.hendraanggrian.javapoet.methodSpecOf

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
