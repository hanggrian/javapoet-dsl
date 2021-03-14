package io.github.hendraanggrian.javapoet.dsl

import com.squareup.javapoet.MethodSpec
import io.github.hendraanggrian.javapoet.MethodSpecBuilder
import io.github.hendraanggrian.javapoet.SpecDslMarker
import io.github.hendraanggrian.javapoet.buildConstructorMethodSpec
import io.github.hendraanggrian.javapoet.buildMethodSpec
import io.github.hendraanggrian.javapoet.emptyConstructorMethodSpec
import io.github.hendraanggrian.javapoet.methodSpecOf

/** A [MethodSpecHandler] is responsible for managing a set of method instances. */
open class MethodSpecHandler internal constructor(actualList: MutableList<MethodSpec>) :
    MutableList<MethodSpec> by actualList {

    /** Add method from name. */
    fun add(name: String): Boolean = add(methodSpecOf(name))

    /** Add constructor method. */
    fun addConstructor(): Boolean = add(emptyConstructorMethodSpec())

    /** Add method from name with custom initialization [configuration]. */
    inline fun add(
        name: String,
        configuration: MethodSpecBuilder.() -> Unit
    ): Boolean = add(buildMethodSpec(name, configuration))

    /** Add constructor method with custom initialization [configuration]. */
    inline fun addConstructor(
        configuration: MethodSpecBuilder.() -> Unit
    ): Boolean = add(buildConstructorMethodSpec(configuration))
}

/** Receiver for the `methods` function type providing an extended set of operators for the configuration. */
@SpecDslMarker
class MethodSpecHandlerScope(actualList: MutableList<MethodSpec>) : MethodSpecHandler(actualList) {

    /** Convenient method to add method with receiver type. */
    inline operator fun String.invoke(
        configuration: MethodSpecBuilder.() -> Unit
    ): Boolean = add(this, configuration)
}
