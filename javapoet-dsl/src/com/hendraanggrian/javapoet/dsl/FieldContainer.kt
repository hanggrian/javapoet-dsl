package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.FieldSpecBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

abstract class FieldContainer internal constructor() : FieldContainerDelegate() {

    operator fun invoke(configuration: FieldContainerScope.() -> Unit) =
        FieldContainerScope(this).configuration()
}

@JavapoetDslMarker
@Suppress("NOTHING_TO_INLINE")
class FieldContainerScope @PublishedApi internal constructor(private val container: FieldContainer) :
    FieldContainerDelegate() {

    override fun add(spec: FieldSpec): FieldSpec = container.add(spec)

    operator fun String.invoke(name: TypeName, builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(name, this, builder)

    operator fun String.invoke(type: KClass<*>, builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        add(type, this, builder)

    inline operator fun <reified T> String.invoke(noinline builder: FieldSpecBuilder.() -> Unit): FieldSpec =
        invoke(T::class, builder)
}

sealed class FieldContainerDelegate {

    abstract fun add(spec: FieldSpec): FieldSpec

    fun add(type: TypeName, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null): FieldSpec =
        add(FieldSpecBuilder.of(type, name, builder))

    fun add(type: KClass<*>, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null): FieldSpec =
        add(FieldSpecBuilder.of(type, name, builder))

    inline fun <reified T> add(name: String, noinline builder: (FieldSpecBuilder.() -> Unit)? = null): FieldSpec =
        add(T::class, name, builder)

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