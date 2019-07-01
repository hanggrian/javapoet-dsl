@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.FieldSpecBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

@JavapoetDslMarker
class FieldContainerScope @PublishedApi internal constructor(container: FieldContainer) :
    FieldContainer by container {

    /** Convenient method to add field with receiver. */
    inline operator fun String.invoke(name: TypeName, noinline builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(name, this, builder)

    /** Convenient method to add field with receiver. */
    inline operator fun String.invoke(type: KClass<*>, noinline builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(type, this, builder)

    /** Convenient method to add field with receiver and reified type. */
    inline operator fun <reified T> String.invoke(noinline builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        invoke(T::class, builder)
}

interface FieldContainer {

    /** Add spec to this container, returning the spec added. */
    fun add(spec: FieldSpec): FieldSpec

    fun add(type: TypeName, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null): FieldSpec =
        add(FieldSpecBuilder.of(type, name, builder))

    fun add(type: KClass<*>, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null): FieldSpec =
        add(FieldSpecBuilder.of(type, name, builder))
}

/** Configures the fields of this container. */
inline operator fun FieldContainer.invoke(configuration: FieldContainerScope.() -> Unit) =
    FieldContainerScope(this).configuration()

inline fun <reified T> FieldContainer.add(
    name: String,
    noinline builder: (FieldSpecBuilder.() -> Unit)? = null
): FieldSpec = add(T::class, name, builder)

inline operator fun FieldContainer.plusAssign(spec: FieldSpec) {
    add(spec)
}

inline operator fun FieldContainer.set(name: String, type: TypeName) {
    add(type, name)
}

inline operator fun FieldContainer.set(name: String, type: KClass<*>) {
    add(type, name)
}