package com.hendraanggrian.javapoet.internal

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier

/**
 * Base type of all JavaPoet DSL spec builders.
 * Subclass of this type have access to many convenient inline functions to JavaPoet constant values.
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

    inline val public: Modifier get() = Modifier.PUBLIC
    inline val protected: Modifier get() = Modifier.PROTECTED
    inline val private: Modifier get() = Modifier.PRIVATE
    inline val abstract: Modifier get() = Modifier.ABSTRACT
    inline val default: Modifier get() = Modifier.DEFAULT
    inline val static: Modifier get() = Modifier.STATIC
    inline val final: Modifier get() = Modifier.FINAL
    inline val transient: Modifier get() = Modifier.TRANSIENT
    inline val volatile: Modifier get() = Modifier.VOLATILE
    inline val synchronized: Modifier get() = Modifier.SYNCHRONIZED
    inline val native: Modifier get() = Modifier.NATIVE
    inline val strictfp: Modifier get() = Modifier.STRICTFP

    abstract fun build(): T

    /**
     * Instead of recreating a list every [plus], add the item to this list.
     *
     * @see com.hendraanggrian.javapoet.TypeVariableSpecBuilder.plus
     * @see com.hendraanggrian.javapoet.ModifierableSpecBuilder.plus
     */
    operator fun <E> ArrayList<E>.plus(other: E): ArrayList<E> = also { it += other }
}