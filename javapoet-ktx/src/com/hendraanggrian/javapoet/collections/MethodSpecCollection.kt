@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.hendraanggrian.javapoet.SpecMarker
import com.hendraanggrian.javapoet.buildConstructorMethodSpec
import com.hendraanggrian.javapoet.buildMethodSpec
import com.squareup.javapoet.MethodSpec

/** A [MethodSpecCollection] is responsible for managing a set of method instances. */
open class MethodSpecCollection(actualList: MutableList<MethodSpec>) : MutableList<MethodSpec> by actualList {

    /** Add method from name. */
    fun add(name: String): Boolean = add(MethodSpec.methodBuilder(name).build())

    /** Add method from name with custom initialization [configuration]. */
    fun add(name: String, configuration: MethodSpecBuilder.() -> Unit): Boolean =
        add(buildMethodSpec(name, configuration))

    /** Add constructor method. */
    fun addConstructor(): Boolean = add(MethodSpec.constructorBuilder().build())

    /** Add constructor method with custom initialization [configuration]. */
    fun addConstructor(configuration: MethodSpecBuilder.() -> Unit): Boolean =
        add(buildConstructorMethodSpec(configuration))

    /** Convenient method to add method with operator function. */
    inline operator fun plusAssign(name: String) {
        add(name)
    }
}

/** Receiver for the `methods` block providing an extended set of operators for the configuration. */
@SpecMarker
class MethodSpecCollectionScope(actualList: MutableList<MethodSpec>) : MethodSpecCollection(actualList) {

    /** @see MethodSpecCollection.add */
    operator fun String.invoke(configuration: MethodSpecBuilder.() -> Unit): Boolean = add(this, configuration)
}
