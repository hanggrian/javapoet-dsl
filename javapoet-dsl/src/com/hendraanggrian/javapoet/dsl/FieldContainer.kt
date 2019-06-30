package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.FieldSpecBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

abstract class FieldContainer internal constructor() : FieldContainerDelegate {

    inline operator fun invoke(configuration: FieldContainerScope.() -> Unit) =
        configuration(FieldContainerScope(this))
}

@JavapoetDslMarker
@Suppress("NOTHING_TO_INLINE")
class FieldContainerScope @PublishedApi internal constructor(private val container: FieldContainer) :
    FieldContainerDelegate by container {

    inline operator fun String.invoke(name: TypeName, noinline builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(name, this, builder)

    inline operator fun String.invoke(type: KClass<*>, noinline builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(type, this, builder)

    inline operator fun <reified T> String.invoke(noinline builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        invoke(T::class, builder)
}

interface FieldContainerDelegate {

    fun add(spec: FieldSpec): FieldSpec

    fun add(type: TypeName, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null): FieldSpec =
        add(FieldSpecBuilder.of(type, name, builder))

    fun add(type: KClass<*>, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null): FieldSpec =
        add(FieldSpecBuilder.of(type, name, builder))

    operator fun plusAssign(spec: FieldSpec) {
        add(spec)
    }

    operator fun set(name: String, type: TypeName) {
        add(type, name)
    }

    operator fun set(name: String, type: KClass<*>) {
        add(type, name)
    }
}

inline fun <reified T> FieldContainerDelegate.add(
    name: String,
    noinline builder: (FieldSpecBuilder.() -> Unit)? = null
): FieldSpec = add(T::class, name, builder)