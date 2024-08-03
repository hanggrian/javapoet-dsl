@file:JvmMultifileClass
@file:JvmName("TypeNamesKt")

package com.hanggrian.javapoet

import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.TypeName

/** Returns an array type whose elements are all instances of componentType. */
public inline val TypeName.array: ArrayTypeName get() = ArrayTypeName.of(this)
