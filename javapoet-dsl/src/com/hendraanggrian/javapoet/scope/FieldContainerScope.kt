package com.hendraanggrian.javapoet.scope

import com.hendraanggrian.javapoet.FieldSpecBuilder
import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.buildFieldSpec
import com.hendraanggrian.javapoet.container.FieldContainer
import com.squareup.javapoet.TypeName
import kotlin.reflect.KClass

@JavapoetDslMarker
class FieldContainerScope internal constructor(private val container: FieldContainer) {

    operator fun String.invoke(type: TypeName, builder: (FieldSpecBuilder.() -> Unit)? = null) {
        container += buildFieldSpec(type, this, builder)
    }

    operator fun String.invoke(type: KClass<*>, builder: (FieldSpecBuilder.() -> Unit)? = null) {
        container += buildFieldSpec(type.java, this, builder)
    }

    inline operator fun <reified T> String.invoke(noinline builder: (FieldSpecBuilder.() -> Unit)? = null) =
        invoke(T::class, builder)
}