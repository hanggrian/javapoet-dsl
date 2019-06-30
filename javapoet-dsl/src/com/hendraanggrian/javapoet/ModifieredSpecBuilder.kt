package com.hendraanggrian.javapoet

import javax.lang.model.element.Modifier

@Suppress("SpellCheckingInspection")
abstract class ModifieredSpecBuilder<T> internal constructor() : SpecBuilder<T>() {

    inline val public: MutableCollection<Modifier> get() = mutableListOf(Modifier.PUBLIC)
    inline val protected: MutableCollection<Modifier> get() = mutableListOf(Modifier.PROTECTED)
    inline val private: MutableCollection<Modifier> get() = mutableListOf(Modifier.PRIVATE)
    inline val abstract: MutableCollection<Modifier> get() = mutableListOf(Modifier.ABSTRACT)
    inline val default: MutableCollection<Modifier> get() = mutableListOf(Modifier.DEFAULT)
    inline val static: MutableCollection<Modifier> get() = mutableListOf(Modifier.STATIC)
    inline val final: MutableCollection<Modifier> get() = mutableListOf(Modifier.FINAL)
    inline val transient: MutableCollection<Modifier> get() = mutableListOf(Modifier.TRANSIENT)
    inline val volatile: MutableCollection<Modifier> get() = mutableListOf(Modifier.VOLATILE)
    inline val synchronized: MutableCollection<Modifier> get() = mutableListOf(Modifier.SYNCHRONIZED)
    inline val native: MutableCollection<Modifier> get() = mutableListOf(Modifier.NATIVE)
    inline val strictfp: MutableCollection<Modifier> get() = mutableListOf(Modifier.STRICTFP)

    /**
     * Add single/multiple [Modifier] to this spec builder.
     * A preferable way to achieve this is to use [plus] operator (e.g.: `modifiers = public + static`).
     */
    abstract var modifiers: Collection<Modifier>

    /** Instead of recreating a collection every [Collection.plus], add the item to this collection. */
    operator fun MutableCollection<Modifier>.plus(others: MutableCollection<Modifier>): MutableCollection<Modifier> =
        also { it += others }
}