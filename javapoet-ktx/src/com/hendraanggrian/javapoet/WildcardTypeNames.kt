package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeName
import com.squareup.javapoet.WildcardTypeName
import java.lang.reflect.Type
import javax.lang.model.type.WildcardType
import kotlin.reflect.KClass

/** Returns a [WildcardTypeName] that represents an unknown type that extends [TypeName]. */
fun TypeName.asSubtypeWildcardTypeName(): WildcardTypeName = WildcardTypeName.subtypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that extends [Type]. */
fun Type.asSubtypeWildcardTypeName(): WildcardTypeName = WildcardTypeName.subtypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that extends [KClass]. */
fun KClass<*>.asSubtypeWildcardTypeName(): WildcardTypeName = java.asSubtypeWildcardTypeName()

/** Returns a [WildcardTypeName] that represents an unknown type that extends [T]. */
inline fun <reified T> asSubtypeWildcardTypeName(): WildcardTypeName = T::class.asSubtypeWildcardTypeName()

/** Returns a [WildcardTypeName] that represents an unknown supertype of [TypeName]. */
fun TypeName.asSupertypeWildcardTypeName(): WildcardTypeName = WildcardTypeName.supertypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown supertype of [Type]. */
fun Type.asSupertypeWildcardTypeName(): WildcardTypeName = WildcardTypeName.supertypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown supertype of [KClass]. */
fun KClass<*>.asSupertypeWildcardTypeName(): WildcardTypeName = java.asSupertypeWildcardTypeName()

/** Returns a [WildcardTypeName] that represents an unknown supertype of [T]. */
inline fun <reified T> asSupertypeWildcardTypeName(): WildcardTypeName = T::class.asSupertypeWildcardTypeName()

/** Returns a [WildcardTypeName] equivalent to this [WildcardType].  */
fun WildcardType.asWildcardTypeName(): TypeName = WildcardTypeName.get(this)

/** Returns a [WildcardTypeName] equivalent to this [java.lang.reflect.WildcardType].  */
fun java.lang.reflect.WildcardType.asWildcardTypeName(): TypeName = WildcardTypeName.get(this)
