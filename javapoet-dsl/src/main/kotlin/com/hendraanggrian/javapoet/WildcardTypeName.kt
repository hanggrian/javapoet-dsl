package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeName
import com.squareup.javapoet.WildcardTypeName
import java.lang.reflect.Type
import javax.lang.model.type.WildcardType
import kotlin.reflect.KClass

/** Returns a [WildcardTypeName] that represents an unknown type that extends [TypeName]. */
inline fun TypeName.toUpperWildcardTypeName(): WildcardTypeName = WildcardTypeName.subtypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that extends [Type]. */
inline fun Type.toUpperWildcardTypeName(): WildcardTypeName = WildcardTypeName.subtypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that extends [KClass]. */
inline fun KClass<*>.toUpperWildcardTypeName(): WildcardTypeName = WildcardTypeName.subtypeOf(java)

/** Returns a [WildcardTypeName] that represents an unknown type that extends [T]. */
inline fun <reified T> wildcardTypeNameUpperOf(): WildcardTypeName = WildcardTypeName.subtypeOf(T::class.java)

/** Returns a [WildcardTypeName] that represents an unknown supertype of [TypeName]. */
inline fun TypeName.toLowerWildcardTypeName(): WildcardTypeName = WildcardTypeName.supertypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown supertype of [Type]. */
inline fun Type.toLowerWildcardTypeName(): WildcardTypeName = WildcardTypeName.supertypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown supertype of [KClass]. */
inline fun KClass<*>.toLowerWildcardTypeName(): WildcardTypeName = WildcardTypeName.supertypeOf(java)

/** Returns a [WildcardTypeName] that represents an unknown supertype of [T]. */
inline fun <reified T> wildcardTypeNameLowerOf(): WildcardTypeName = WildcardTypeName.supertypeOf(T::class.java)

/** Returns a [WildcardTypeName] equivalent to [WildcardType]. */
inline fun WildcardType.asWildcardTypeName(): TypeName = WildcardTypeName.get(this)

/** Returns a [WildcardTypeName] equivalent to [java.lang.reflect.WildcardType]. */
inline fun java.lang.reflect.WildcardType.asWildcardTypeName(): TypeName = WildcardTypeName.get(this)
