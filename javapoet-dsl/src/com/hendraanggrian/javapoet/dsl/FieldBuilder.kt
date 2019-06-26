package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.FieldSpecBuilder
import com.hendraanggrian.javapoet.buildFieldSpec
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

abstract class FieldBuilder {

    abstract fun add(spec: FieldSpec)

    operator fun String.invoke(type: TypeName, builder: (FieldSpecBuilder.() -> Unit)? = null) =
        add(buildFieldSpec(type, this, builder))

    operator fun String.invoke(type: KClass<*>, builder: (FieldSpecBuilder.() -> Unit)? = null) =
        add(buildFieldSpec(type.java, this, builder))

    inline operator fun <reified T> String.invoke(noinline builder: (FieldSpecBuilder.() -> Unit)? = null) =
        invoke(T::class, builder)
}