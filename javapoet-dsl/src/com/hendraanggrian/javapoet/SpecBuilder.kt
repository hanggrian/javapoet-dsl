package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeName

/**
 * Base type of all JavaPoet DSL spec builders.
 * Subclass of this type have access to many convenient inline functions to JavaPoet constant values.
 *
 * @see com.hendraanggrian.javapoet.ModifieredSpecBuilder.plus
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

    /** Converts this DSL spec builder to native JavaPoet specs. */
    abstract fun build(): T
}