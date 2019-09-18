package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeName
import com.squareup.javapoet.WildcardTypeName
import javax.lang.model.type.WildcardType
import kotlin.reflect.KClass

/** Returns a type that represents an unknown type that extends [upperBound]. */
fun subtypeWildcardTypeNameOf(upperBound: TypeName): WildcardTypeName =
    WildcardTypeName.subtypeOf(upperBound)

/** Returns a type that represents an unknown type that extends [upperBound]. */
fun subtypeWildcardTypeNameOf(upperBound: Class<*>): WildcardTypeName =
    WildcardTypeName.subtypeOf(upperBound)

/** Returns a type that represents an unknown type that extends [upperBound]. */
fun subtypeWildcardTypeNameOf(upperBound: KClass<*>): WildcardTypeName =
    subtypeWildcardTypeNameOf(upperBound.java)

/** Returns a type that represents an unknown type that extends [T]. */
inline fun <reified T> subtypeWildcardTypeNameOf(): WildcardTypeName =
    subtypeWildcardTypeNameOf(T::class)

/** Returns a type that represents an unknown supertype of [lowerBound]. */
fun supertypeWildcardTypeNameOf(lowerBound: TypeName): WildcardTypeName =
    WildcardTypeName.supertypeOf(lowerBound)

/** Returns a type that represents an unknown supertype of [lowerBound]. */
fun supertypeWildcardTypeNameOf(lowerBound: Class<*>): WildcardTypeName =
    WildcardTypeName.supertypeOf(lowerBound)

/** Returns a type that represents an unknown supertype of [lowerBound]. */
fun supertypeWildcardTypeNameOf(lowerBound: KClass<*>): WildcardTypeName =
    supertypeWildcardTypeNameOf(lowerBound.java)

/** Returns a type that represents an unknown supertype of [T]. */
inline fun <reified T> supertypeWildcardTypeNameOf(): WildcardTypeName =
    supertypeWildcardTypeNameOf(T::class)

/** Converts [WildcardType] to [TypeName]. */
fun wildcardTypeNameOf(mirror: WildcardType): TypeName =
    WildcardTypeName.get(mirror)

/** Converts [java.lang.reflect.WildcardType] to [TypeName]. */
fun wildcardTypeNameOf(wildcardName: java.lang.reflect.WildcardType): TypeName =
    WildcardTypeName.get(wildcardName)
