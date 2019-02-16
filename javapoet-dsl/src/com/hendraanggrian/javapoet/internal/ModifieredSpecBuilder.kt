package com.hendraanggrian.javapoet.internal

import javax.lang.model.element.Modifier

internal interface ModifieredSpecBuilder {

    val public: MutableList<Modifier> get() = arrayListOf(Modifier.PUBLIC)
    val protected: MutableList<Modifier> get() = arrayListOf(Modifier.PROTECTED)
    val private: MutableList<Modifier> get() = arrayListOf(Modifier.PRIVATE)
    val abstract: MutableList<Modifier> get() = arrayListOf(Modifier.ABSTRACT)
    val default: MutableList<Modifier> get() = arrayListOf(Modifier.DEFAULT)
    val static: MutableList<Modifier> get() = arrayListOf(Modifier.STATIC)
    val final: MutableList<Modifier> get() = arrayListOf(Modifier.FINAL)
    val transient: MutableList<Modifier> get() = arrayListOf(Modifier.TRANSIENT)
    val volatile: MutableList<Modifier> get() = arrayListOf(Modifier.VOLATILE)
    val synchronized: MutableList<Modifier> get() = arrayListOf(Modifier.SYNCHRONIZED)
    val native: MutableList<Modifier> get() = arrayListOf(Modifier.NATIVE)
    val strictfp: MutableList<Modifier> get() = arrayListOf(Modifier.STRICTFP)

    /**
     * Add single/multiple [Modifier] to this spec builder.
     * A preferable way to achieve this is to use [plus] operator (e.g.: `typeVariables = name1 + name2 + name3`).
     */
    var modifiers: Collection<Modifier>

    /** Instead of recreating a list every [plus], add the item to this list. */
    operator fun MutableList<Modifier>.plus(other: Modifier): MutableList<Modifier> = also { it += other }
}