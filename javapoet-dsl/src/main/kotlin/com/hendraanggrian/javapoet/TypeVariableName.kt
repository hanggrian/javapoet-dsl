package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import java.lang.reflect.Type
import javax.lang.model.element.TypeParameterElement
import javax.lang.model.type.TypeVariable
import kotlin.reflect.KClass

/** Returns a [TypeVariableName] without bounds. */
inline fun String.genericsBy(): TypeVariableName = TypeVariableName.get(this)

/** Returns a [TypeVariableName] with [TypeName] bounds. */
inline fun String.genericsBy(vararg bounds: TypeName): TypeVariableName =
    TypeVariableName.get(this, *bounds)

/** Returns a [TypeVariableName] with [Type] bounds. */
inline fun String.genericsBy(vararg bounds: Type): TypeVariableName =
    TypeVariableName.get(this, *bounds)

/** Returns a [TypeVariableName] with [KClass] bounds. */
fun String.genericsBy(vararg bounds: KClass<*>): TypeVariableName =
    TypeVariableName.get(this, *bounds.toJavaClasses())

/** Returns a [TypeVariableName] with collection of [TypeName] bounds. */
fun String.genericsBy(bounds: List<TypeName>): TypeVariableName =
    TypeVariableName.get(this, *bounds.toTypedArray())

/** Returns a [TypeVariableName] with collection of [Type] bounds. */
@JvmName("genericsWithTypes")
fun String.genericsBy(bounds: Iterable<Type>): TypeVariableName =
    genericsBy(bounds.map { it.asTypeName() })

/** Returns a [TypeVariableName] with collection of [KClass] bounds. */
@JvmName("genericsWithClasses")
fun String.genericsBy(bounds: Iterable<KClass<*>>): TypeVariableName =
    genericsBy(bounds.map { it.asTypeName() })

/** Returns a [TypeVariableName] equivalent to [TypeParameterElement]. */
inline fun TypeParameterElement.asTypeVariableName(): TypeVariableName = TypeVariableName.get(this)

/** Returns a [TypeVariableName] equivalent to [TypeVariable]. */
inline fun TypeVariable.asTypeVariableName(): TypeVariableName = TypeVariableName.get(this)

/** Returns a [TypeVariableName] equivalent to [java.lang.reflect.TypeVariable]. */
inline fun java.lang.reflect.TypeVariable<*>.asTypeVariableName(): TypeVariableName =
    TypeVariableName.get(this)
