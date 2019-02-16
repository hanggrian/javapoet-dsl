package com.hendraanggrian.javapoet.internal

import javax.lang.model.element.Modifier

internal interface Modifierable {

    val public: Modifier get() = Modifier.PUBLIC
    val protected: Modifier get() = Modifier.PROTECTED
    val private: Modifier get() = Modifier.PRIVATE
    val abstract: Modifier get() = Modifier.ABSTRACT
    val default: Modifier get() = Modifier.DEFAULT
    val static: Modifier get() = Modifier.STATIC
    val final: Modifier get() = Modifier.FINAL
    val transient: Modifier get() = Modifier.TRANSIENT
    val volatile: Modifier get() = Modifier.VOLATILE
    val synchronized: Modifier get() = Modifier.SYNCHRONIZED
    val native: Modifier get() = Modifier.NATIVE
    val strictfp: Modifier get() = Modifier.STRICTFP

    var modifiers: List<Modifier>

    operator fun Modifier.plus(other: Modifier): List<Modifier> = listOf(this, other)
}