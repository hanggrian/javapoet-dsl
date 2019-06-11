package com.hendraanggrian.javapoet.internal

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier

/**
 * Base type of all JavaPoet DSL spec builders.
 * Subclass of this type have access to many convenient inline functions to JavaPoet constant values.
 *
 * @see com.hendraanggrian.javapoet.ModifierableSpecBuilder.plus
 */
abstract class SpecBuilder<T> internal constructor() {

    inline val void: TypeName get() = TypeName.VOID
    inline val boolean: TypeName get() = TypeName.BOOLEAN
    inline val byte: TypeName get() = TypeName.BYTE
    inline val short: TypeName get() = TypeName.SHORT
    inline val int: TypeName get() = TypeName.INT
    inline val long: TypeName get() = TypeName.LONG
    inline val char: TypeName get() = TypeName.CHAR
    inline val float: TypeName get() = TypeName.FLOAT
    inline val double: TypeName get() = TypeName.DOUBLE
    inline val `object`: ClassName get() = TypeName.OBJECT

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

    /** Converts this DSL spec builder to native JavaPoet specs. */
    abstract fun build(): T
}