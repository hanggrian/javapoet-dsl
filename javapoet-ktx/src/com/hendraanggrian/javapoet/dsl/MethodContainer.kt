package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.MethodSpecs
import com.squareup.javapoet.MethodSpec

internal interface MethodCollection {

    /** Add method to this container, returning the method added. */
    fun add(spec: MethodSpec): MethodSpec
}

/** A [MethodContainer] is responsible for managing a set of method instances. */
abstract class MethodContainer internal constructor() : MethodCollection {

    /** Add method from [name], returning the method added. */
    fun add(name: String): MethodSpec =
        add(MethodSpecs.of(name))

    /** Add method from [name] with custom initialization [builder], returning the method added. */
    inline fun add(name: String, builderAction: MethodSpecs.Builder.() -> Unit): MethodSpec =
        add(MethodSpecs.of(name, builderAction))

    /** Add constructor method, returning the method added. */
    fun addConstructor(): MethodSpec =
        add(MethodSpecs.constructor())

    /** Add constructor method with custom initialization [builder], returning the method added. */
    inline fun addConstructor(builderAction: MethodSpecs.Builder.() -> Unit): MethodSpec =
        add(MethodSpecs.constructor(builderAction))

    /** Convenient method to add method with operator function. */
    operator fun plusAssign(spec: MethodSpec) {
        add(spec)
    }

    /** Convenient method to add method with operator function. */
    operator fun plusAssign(name: String) {
        add(name)
    }

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: MethodContainerScope.() -> Unit) =
        MethodContainerScope(this).configuration()
}

/** Receiver for the `methods` block providing an extended set of operators for the configuration. */
class MethodContainerScope @PublishedApi internal constructor(collection: MethodCollection) :
    MethodContainer(), MethodCollection by collection {

    /** Convenient method to add method with receiver type. */
    inline operator fun String.invoke(builderAction: MethodSpecs.Builder.() -> Unit): MethodSpec =
        add(this, builderAction)
}
