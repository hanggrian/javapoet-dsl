@file:JvmMultifileClass
@file:JvmName("TypeNamesKt")

package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import kotlin.reflect.KClass

val STRING: ClassName = classNamed("java.lang", "String")
val CHAR_SEQUENCE: ClassName = classNamed("java.lang", "CharSequence")
val COMPARABLE: ClassName = classNamed("java.lang", "Comparable")
val THROWABLE: ClassName = classNamed("java.lang", "Throwable")
val ANNOTATION: ClassName = classNamed("java.lang", "Annotation")
val ITERABLE: ClassName = classNamed("java.lang", "Iterable")
val COLLECTION: ClassName = classNamed("java.util", "Collection")
val LIST: ClassName = classNamed("java.util", "List")
val SET: ClassName = classNamed("java.util", "Set")
val MAP: ClassName = classNamed("java.util", "Map")

inline val Class<*>.name2: ClassName get() = ClassName.get(this)

inline val KClass<*>.name: ClassName get() = ClassName.get(java)

/** Returns a new class name instance for the given fully-qualified class name string. */
inline fun classNamed(fullName: String): ClassName = ClassName.bestGuess(fullName)

/** Returns a class name created from the given parts. */
inline fun classNamed(
    packageName: String,
    simpleName: String,
    vararg simpleNames: String,
): ClassName = ClassName.get(packageName, simpleName, *simpleNames)
