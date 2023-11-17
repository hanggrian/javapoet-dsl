@file:JvmMultifileClass
@file:JvmName("TypeNamesKt")

package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import kotlin.reflect.KClass

/** Returns type variable named name without bounds. */
public inline val String.generics: TypeVariableName get() = TypeVariableName.get(this)

/** Returns type variable named name with bounds. */
public inline fun String.genericsBy(vararg bounds: TypeName): TypeVariableName =
    TypeVariableName.get(this, *bounds)

/** Returns type variable named name with bounds. */
public fun String.genericsBy(vararg bounds: KClass<*>): TypeVariableName =
    TypeVariableName.get(this, *bounds.map { it.java }.toTypedArray())
