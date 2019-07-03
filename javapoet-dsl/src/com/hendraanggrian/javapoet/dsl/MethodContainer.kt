package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.hendraanggrian.javapoet.invoke
import com.squareup.javapoet.MethodSpec

/** A [MethodContainer] is responsible for managing a set of method instances. */
abstract class MethodContainer internal constructor() {

    /** Add method to this container, returning the method added. */
    abstract fun add(spec: MethodSpec): MethodSpec

    /** Add method from [name], returning the method added. */
    fun add(name: String): MethodSpec =
        add(MethodSpec.methodBuilder(name).build())

    /** Add method from [name] with custom initialization [builder], returning the method added. */
    inline fun add(name: String, builder: MethodSpecBuilder.() -> Unit): MethodSpec =
        add(MethodSpec.methodBuilder(name)(builder))

    /** Add constructor method, returning the method added. */
    fun addConstructor(): MethodSpec =
        add(MethodSpec.constructorBuilder().build())

    /** Add constructor method with custom initialization [builder], returning the method added. */
    inline fun addConstructor(builder: MethodSpecBuilder.() -> Unit): MethodSpec =
        add(MethodSpec.constructorBuilder()(builder))

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
class MethodContainerScope @PublishedApi internal constructor(private val container: MethodContainer) :
    MethodContainer() {

    override fun add(spec: MethodSpec): MethodSpec = container.add(spec)

    /** Convenient method to add method with receiver type. */
    inline operator fun String.invoke(builder: MethodSpecBuilder.() -> Unit): MethodSpec =
        add(this, builder)
}