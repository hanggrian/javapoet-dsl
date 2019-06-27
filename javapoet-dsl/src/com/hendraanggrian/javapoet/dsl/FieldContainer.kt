package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.FieldSpecBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.buildFieldSpec
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

abstract class FieldContainer {

    abstract operator fun plusAssign(spec: FieldSpec)

    fun add(type: TypeName, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null) =
        plusAssign(buildFieldSpec(type, name, builder))

    fun add(type: KClass<*>, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null) =
        plusAssign(buildFieldSpec(type, name, builder))

    inline fun <reified T> add(name: String, noinline builder: (FieldSpecBuilder.() -> Unit)? = null) =
        add(T::class, name, builder)

    operator fun invoke(configuration: FieldContainerScope.() -> Unit) =
        configuration(FieldContainerScope(this))
}

@JavapoetDslMarker
class FieldContainerScope internal constructor(private val container: FieldContainer) {

    operator fun String.invoke(type: TypeName, builder: (FieldSpecBuilder.() -> Unit)? = null) {
        container += buildFieldSpec(type, this, builder)
    }

    operator fun String.invoke(type: KClass<*>, builder: (FieldSpecBuilder.() -> Unit)? = null) {
        container += buildFieldSpec(type, this, builder)
    }

    inline operator fun <reified T> String.invoke(noinline builder: (FieldSpecBuilder.() -> Unit)? = null) =
        invoke(T::class, builder)
}