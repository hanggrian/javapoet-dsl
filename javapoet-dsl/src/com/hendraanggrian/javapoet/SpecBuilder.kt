package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeName

abstract class SpecBuilder {

    val void: TypeName get() = TypeName.VOID
    val boolean: TypeName get() = TypeName.BOOLEAN
}