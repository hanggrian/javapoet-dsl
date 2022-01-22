package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeName
import com.squareup.javapoet.WildcardTypeName
import java.lang.reflect.Type
import javax.lang.model.type.WildcardType
import kotlin.reflect.KClass

/** Returns a [WildcardTypeName] that represents an unknown type that extends [TypeName]. */
inline fun TypeName.wildcardSubtypeOf(): WildcardTypeName = WildcardTypeName.subtypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that extends [Type]. */
inline fun Type.wildcardSubtypeOf(): WildcardTypeName = WildcardTypeName.subtypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that extends [KClass]. */
inline fun KClass<*>.wildcardSubtypeOf(): WildcardTypeName = WildcardTypeName.subtypeOf(java)

/** Returns a [WildcardTypeName] that represents an unknown supertype of [TypeName]. */
inline fun TypeName.wildcardSupertypeOf(): WildcardTypeName = WildcardTypeName.supertypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown supertype of [Type]. */
inline fun Type.wildcardSupertypeOf(): WildcardTypeName = WildcardTypeName.supertypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown supertype of [KClass]. */
inline fun KClass<*>.wildcardSupertypeOf(): WildcardTypeName = WildcardTypeName.supertypeOf(java)

/**
 * Returns a [WildcardTypeName] equivalent to [WildcardType].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/javax.lang.model.type.-wildcard-type/as-wildcard-type-name/)
 */
inline fun WildcardType.asWildcardTypeName(): TypeName = WildcardTypeName.get(this)

/**
 * Returns a [WildcardTypeName] equivalent to [java.lang.reflect.WildcardType].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/java.lang.reflect.-wildcard-type/as-wildcard-type-name/)
 */
inline fun java.lang.reflect.WildcardType.asWildcardTypeName(): TypeName = WildcardTypeName.get(this)
