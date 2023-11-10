package com.hendraanggrian.javapoet

import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import com.squareup.javapoet.WildcardTypeName
import kotlin.reflect.KClass

val VOID: TypeName = TypeName.VOID
val BOOLEAN: TypeName = TypeName.BOOLEAN
val BYTE: TypeName = TypeName.BYTE
val SHORT: TypeName = TypeName.SHORT
val INT: TypeName = TypeName.INT
val LONG: TypeName = TypeName.LONG
val CHAR: TypeName = TypeName.CHAR
val FLOAT: TypeName = TypeName.FLOAT
val DOUBLE: TypeName = TypeName.DOUBLE
val OBJECT: TypeName = TypeName.OBJECT

inline val KClass<*>.name: ClassName get() = ClassName.get(java)

/** Returns a new class name instance for the given fully-qualified class name string. */
inline fun classNamed(fullName: String): ClassName = ClassName.bestGuess(fullName)

/** Returns a class name created from the given parts. */
inline fun classNamed(
    packageName: String,
    simpleName: String,
    vararg simpleNames: String,
): ClassName = ClassName.get(packageName, simpleName, *simpleNames)

/** Returns an array type whose elements are all instances of componentType. */
inline val TypeName.array: ArrayTypeName get() = ArrayTypeName.of(this)

/** Returns a parameterized type, applying typeArguments to rawType. */
inline fun ClassName.parameterizedBy(vararg typeArguments: TypeName): ParameterizedTypeName =
    ParameterizedTypeName.get(this, *typeArguments)

/** Returns a parameterized type, applying typeArguments to rawType. */
fun ClassName.parameterizedBy(vararg typeArguments: KClass<*>): ParameterizedTypeName =
    ParameterizedTypeName.get(this, *typeArguments.map { it.name }.toTypedArray())

/** Returns a parameterized type, applying typeArguments to rawType. */
inline fun <reified T> ClassName.parameterizedBy(): ParameterizedTypeName =
    parameterizedBy(T::class.name)

/** Returns type variable named name without bounds. */
inline val String.generics: TypeVariableName get() = TypeVariableName.get(this)

/** Returns type variable named name with bounds. */
inline fun String.genericsBy(vararg bounds: TypeName): TypeVariableName =
    TypeVariableName.get(this, *bounds)

/** Returns type variable named name with bounds. */
fun String.genericsBy(vararg bounds: KClass<*>): TypeVariableName =
    TypeVariableName.get(this, *bounds.map { it.java }.toTypedArray())

/** Returns a type that represents an unknown type that extends bound. */
inline val TypeName.subtype: WildcardTypeName get() = WildcardTypeName.subtypeOf(this)

/** Returns a type that represents an unknown supertype of bound. */
inline val TypeName.supertype: WildcardTypeName get() = WildcardTypeName.supertypeOf(this)
