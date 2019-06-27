package com.hendraanggrian.javapoet.container

import com.hendraanggrian.javapoet.FieldSpecBuilder
import com.hendraanggrian.javapoet.buildFieldSpec
import com.hendraanggrian.javapoet.scope.FieldContainerScope
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

abstract class FieldContainer {

    abstract operator fun plusAssign(spec: FieldSpec)

    fun add(type: TypeName, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null) =
        plusAssign(buildFieldSpec(type, name, builder))

    fun add(type: KClass<*>, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null) =
        plusAssign(buildFieldSpec(type.java, name, builder))

    inline fun <reified T> add(name: String, noinline builder: (FieldSpecBuilder.() -> Unit)? = null) =
        add(T::class, name, builder)

    operator fun invoke(configuration: FieldContainerScope.() -> Unit) =
        configuration(FieldContainerScope(this))
}