package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeName
import javax.lang.model.type.TypeMirror
import kotlin.reflect.KClass

/** Primitive void name. */
inline val void: TypeName get() = TypeName.VOID

/** Primitive boolean name. */
inline val boolean: TypeName get() = TypeName.BOOLEAN

/** Primitive byte name. */
inline val byte: TypeName get() = TypeName.BYTE

/** Primitive short name. */
inline val short: TypeName get() = TypeName.SHORT

/** Primitive int name. */
inline val int: TypeName get() = TypeName.INT

/** Primitive long name. */
inline val long: TypeName get() = TypeName.LONG

/** Primitive char name. */
inline val char: TypeName get() = TypeName.CHAR

/** Primitive float name. */
inline val float: TypeName get() = TypeName.FLOAT

/** Primitive double name. */
inline val double: TypeName get() = TypeName.DOUBLE

/** Returns a type name equivalent to [mirror]. */
fun typeNameOf(mirror: TypeMirror): TypeName =
    TypeName.get(mirror)

/** Returns a type name equivalent to [type]. */
fun typeNameOf(type: Class<*>): TypeName =
    TypeName.get(type)

/** Returns a type name equivalent to [type]. */
fun typeNameOf(type: KClass<*>): TypeName =
    typeNameOf(type.java)

/** Returns a type name equivalent to [T]. */
inline fun <reified T> typeNameOf(): TypeName =
    typeNameOf(T::class)
