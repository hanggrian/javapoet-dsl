package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.JavapoetSpecMarker
import com.hendraanggrian.javapoet.MethodSpecBuilder
import com.hendraanggrian.javapoet.SpecLoader
import com.hendraanggrian.javapoet.buildConstructorMethodSpec
import com.hendraanggrian.javapoet.buildMethodSpec
import com.hendraanggrian.javapoet.createSpecLoader
import com.squareup.javapoet.MethodSpec
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/** A [MethodSpecList] is responsible for managing a set of method instances. */
@OptIn(ExperimentalContracts::class)
open class MethodSpecList internal constructor(actualList: MutableList<MethodSpec>) :
    MutableList<MethodSpec> by actualList {

    /** Add method from name. */
    fun add(name: String): MethodSpec = MethodSpec.methodBuilder(name).build().also(::add)

    /** Add method from name with custom initialization [configuration]. */
    fun add(name: String, configuration: MethodSpecBuilder.() -> Unit): MethodSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildMethodSpec(name, configuration).also(::add)
    }

    /** Add constructor method. */
    fun addConstructor(): MethodSpec = MethodSpec.constructorBuilder().build().also(::add)

    /** Add constructor method with custom initialization [configuration]. */
    fun addConstructor(configuration: MethodSpecBuilder.() -> Unit): MethodSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildConstructorMethodSpec(configuration).also(::add)
    }

    /** Convenient method to add method with operator function. */
    inline operator fun plusAssign(name: String) {
        add(name)
    }

    /** Property delegate for adding method from name. */
    val adding: SpecLoader<MethodSpec> get() = createSpecLoader(::add)

    /** Property delegate for adding method from name with initialization [configuration]. */
    fun adding(configuration: MethodSpecBuilder.() -> Unit): SpecLoader<MethodSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { add(it, configuration) }
    }
}

/** Receiver for the `methods` block providing an extended set of operators for the configuration. */
@JavapoetSpecMarker
class MethodSpecListScope internal constructor(actualList: MutableList<MethodSpec>) : MethodSpecList(actualList) {

    /** @see MethodSpecList.add */
    inline operator fun String.invoke(noinline configuration: MethodSpecBuilder.() -> Unit): MethodSpec =
        add(this, configuration)
}
