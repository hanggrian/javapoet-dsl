@file:JvmMultifileClass
@file:JvmName("TypeNamesKt")

package com.hanggrian.javapoet

import com.squareup.javapoet.TypeName
import com.squareup.javapoet.WildcardTypeName

/** Returns a type that represents an unknown type that extends bound. */
public inline val TypeName.subtype: WildcardTypeName get() = WildcardTypeName.subtypeOf(this)

/** Returns a type that represents an unknown supertype of bound. */
public inline val TypeName.supertype: WildcardTypeName get() = WildcardTypeName.supertypeOf(this)
