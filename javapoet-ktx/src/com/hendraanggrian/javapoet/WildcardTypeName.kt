package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeName
import com.squareup.javapoet.WildcardTypeName
import java.lang.reflect.Type
import javax.lang.model.type.WildcardType
import kotlin.reflect.KClass

/** Returns a [WildcardTypeName] that represents an unknown type that extends [TypeName]. */
fun TypeName.subtypeOf(): WildcardTypeName = WildcardTypeName.subtypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that extends [Type]. */
fun Type.subtypeOf(): WildcardTypeName = WildcardTypeName.subtypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown type that extends [KClass]. */
fun KClass<*>.subtypeOf(): WildcardTypeName = WildcardTypeName.subtypeOf(java)

/** Returns a [WildcardTypeName] that represents an unknown supertype of [TypeName]. */
fun TypeName.supertypeOf(): WildcardTypeName = WildcardTypeName.supertypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown supertype of [Type]. */
fun Type.supertypeOf(): WildcardTypeName = WildcardTypeName.supertypeOf(this)

/** Returns a [WildcardTypeName] that represents an unknown supertype of [KClass]. */
fun KClass<*>.supertypeOf(): WildcardTypeName = WildcardTypeName.supertypeOf(java)

/**
 * Returns a [WildcardTypeName] equivalent to [WildcardType].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/javax.lang.model.type.-wildcard-type/as-wildcard-type-name/)
 */
fun WildcardType.asWildcardTypeName(): TypeName = WildcardTypeName.get(this)

/**
 * Returns a [WildcardTypeName] equivalent to [java.lang.reflect.WildcardType].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/java.lang.reflect.-wildcard-type/as-wildcard-type-name/)
 */
fun java.lang.reflect.WildcardType.asWildcardTypeName(): TypeName = WildcardTypeName.get(this)
