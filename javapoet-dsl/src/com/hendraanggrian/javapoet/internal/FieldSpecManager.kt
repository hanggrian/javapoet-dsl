package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.FieldSpecBuilder
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type

interface FieldSpecManager {

    fun field(type: TypeName, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null)

    fun field(type: Type, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null)
}