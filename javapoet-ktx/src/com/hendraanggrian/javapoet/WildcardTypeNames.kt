package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeName
import com.squareup.javapoet.WildcardTypeName
import javax.lang.model.type.WildcardType
import kotlin.reflect.KClass

/** Returns a [WildcardTypeName] that represents an unknown type that extends [upperBound]. */
fun TypeName.asSubtypeWildcardTypeName(): WildcardTypeName =
    WildcardTypeName.subtypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that extends [upperBound]. */
fun Class<*>.asSubtypeWildcardTypeName(): WildcardTypeName =
    WildcardTypeName.subtypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that extends [upperBound]. */
fun KClass<*>.asSubtypeWildcardTypeName(): WildcardTypeName =
    java.asSubtypeWildcardTypeName()

/** Returns a [WildcardTypeName] that represents an unknown type that extends [T]. */
inline fun <reified T> asSubtypeWildcardTypeName(): WildcardTypeName =
    T::class.asSubtypeWildcardTypeName()

/** Returns a [WildcardTypeName] that represents an unknown supertype of [lowerBound]. */
fun TypeName.asSupertypeWildcardTypeName(): WildcardTypeName =
    WildcardTypeName.supertypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown supertype of [lowerBound]. */
fun Class<*>.asSupertypeWildcardTypeName(): WildcardTypeName =
    WildcardTypeName.supertypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown supertype of [lowerBound]. */
fun KClass<*>.asSupertypeWildcardTypeName(): WildcardTypeName =
    java.asSupertypeWildcardTypeName()

/** Returns a [WildcardTypeName] that represents an unknown supertype of [T]. */
inline fun <reified T> asSupertypeWildcardTypeName(): WildcardTypeName =
    T::class.asSupertypeWildcardTypeName()

/** Returns a [WildcardTypeName] equivalent to this [WildcardType].  */
fun WildcardType.asWildcardTypeName(): TypeName =
    WildcardTypeName.get(this)

/** Returns a [WildcardTypeName] equivalent to this [java.lang.reflect.WildcardType].  */
fun java.lang.reflect.WildcardType.asWildcardTypeName(): TypeName =
    WildcardTypeName.get(this)
