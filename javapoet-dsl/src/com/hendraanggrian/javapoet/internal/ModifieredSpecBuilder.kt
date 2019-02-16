package com.hendraanggrian.javapoet.internal

import javax.lang.model.element.Modifier

internal interface ModifieredSpecBuilder {

    /**
     * Add single/multiple [Modifier] to this spec builder.
     * A preferable way to achieve this is to use [plus] operator (e.g.: `typeVariables = name1 + name2 + name3`).
     */
    var modifiers: Collection<Modifier>

    /** Instead of recreating a list every [plus], add the item to this list. */
    operator fun MutableList<Modifier>.plus(other: Modifier): MutableList<Modifier> = also { it += other }
}