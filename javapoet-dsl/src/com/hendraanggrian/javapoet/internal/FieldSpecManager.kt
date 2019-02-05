package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.FieldSpecBuilder
import com.hendraanggrian.javapoet.FieldSpecBuilderImpl
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type

interface FieldSpecManager {

    fun field(type: TypeName, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null)

    fun field(type: Type, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null)

    fun createField(type: TypeName, name: String, builder: (FieldSpecBuilder.() -> Unit)?): FieldSpec =
        FieldSpecBuilderImpl(FieldSpec.builder(type, name))
            .also { builder?.invoke(it) }
            .nativeBuilder
            .build()

    fun createField(type: Type, name: String, builder: (FieldSpecBuilder.() -> Unit)?): FieldSpec =
        FieldSpecBuilderImpl(FieldSpec.builder(type, name))
            .also { builder?.invoke(it) }
            .nativeBuilder
            .build()
}