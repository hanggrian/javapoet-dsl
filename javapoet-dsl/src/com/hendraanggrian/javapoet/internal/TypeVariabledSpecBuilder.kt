package com.hendraanggrian.javapoet.internal

import com.squareup.javapoet.TypeVariableName

internal interface TypeVariabledSpecBuilder {

    /** Add single [TypeVariableName] to this spec builder. */
    var typeVariable: TypeVariableName

    /**
     * Add multiple [TypeVariableName] to this spec builder.
     * A preferable way to achieve this is to use [plus] operator (e.g.: `typeVariables = name1 + name2 + name3`).
     */
    var typeVariables: Iterable<TypeVariableName>

    /** Combines two initial values. */
    operator fun TypeVariableName.plus(other: TypeVariableName): MutableList<TypeVariableName> =
        arrayListOf(this, other)

    /** Instead of recreating a list every [plus], add the item to this list. */
    operator fun MutableList<TypeVariableName>.plus(other: TypeVariableName): MutableList<TypeVariableName> =
        also { it += other }
}