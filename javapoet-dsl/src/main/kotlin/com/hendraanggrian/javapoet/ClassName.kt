@file:JvmMultifileClass
@file:JvmName("TypeNamesKt")

package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import kotlin.reflect.KClass

public val STRING: ClassName = classNamed("java.lang", "String")
public val CHAR_SEQUENCE: ClassName = classNamed("java.lang", "CharSequence")
public val COMPARABLE: ClassName = classNamed("java.lang", "Comparable")
public val THROWABLE: ClassName = classNamed("java.lang", "Throwable")
public val ANNOTATION: ClassName = classNamed("java.lang", "Annotation")
public val ITERABLE: ClassName = classNamed("java.lang", "Iterable")
public val COLLECTION: ClassName = classNamed("java.util", "Collection")
public val LIST: ClassName = classNamed("java.util", "List")
public val SET: ClassName = classNamed("java.util", "Set")
public val MAP: ClassName = classNamed("java.util", "Map")

public inline val Class<*>.name2: ClassName get() = ClassName.get(this)

public inline val KClass<*>.name: ClassName get() = ClassName.get(java)

/** Returns a new class name instance for the given fully-qualified class name string. */
public inline fun classNamed(fullName: String): ClassName = ClassName.bestGuess(fullName)

/** Returns a class name created from the given parts. */
public inline fun classNamed(
    packageName: String,
    simpleName: String,
    vararg simpleNames: String,
): ClassName = ClassName.get(packageName, simpleName, *simpleNames)
