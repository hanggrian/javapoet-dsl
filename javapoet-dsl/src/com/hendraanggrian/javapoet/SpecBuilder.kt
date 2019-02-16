package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier

/**
 * Base class of all JavaPoet DSL spec builders.
 * Subclass of this class have access to many convenient inline functions to JavaPoet constant values.
 */
abstract class SpecBuilder<T> {

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

    inline val public: MutableList<Modifier> get() = arrayListOf(Modifier.PUBLIC)
    inline val protected: MutableList<Modifier> get() = arrayListOf(Modifier.PROTECTED)
    inline val private: MutableList<Modifier> get() = arrayListOf(Modifier.PRIVATE)
    inline val abstract: MutableList<Modifier> get() = arrayListOf(Modifier.ABSTRACT)
    inline val default: MutableList<Modifier> get() = arrayListOf(Modifier.DEFAULT)
    inline val static: MutableList<Modifier> get() = arrayListOf(Modifier.STATIC)
    inline val final: MutableList<Modifier> get() = arrayListOf(Modifier.FINAL)
    inline val transient: MutableList<Modifier> get() = arrayListOf(Modifier.TRANSIENT)
    inline val volatile: MutableList<Modifier> get() = arrayListOf(Modifier.VOLATILE)
    inline val synchronized: MutableList<Modifier> get() = arrayListOf(Modifier.SYNCHRONIZED)
    inline val native: MutableList<Modifier> get() = arrayListOf(Modifier.NATIVE)
    inline val strictfp: MutableList<Modifier> get() = arrayListOf(Modifier.STRICTFP)

    abstract fun build(): T
}