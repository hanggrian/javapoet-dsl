package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import javax.lang.model.element.TypeElement
import kotlin.reflect.KClass

/** Returns a [ClassName] for the given fully-qualified class name string. */
inline fun classNameOf(fullName: String): ClassName = ClassName.bestGuess(fullName)

/** Returns a [ClassName] created from the given parts. */
inline fun classNameOf(
    packageName: String,
    simpleName: String,
    vararg simpleNames: String
): ClassName = ClassName.get(packageName, simpleName, *simpleNames)

/** Returns a [ClassName] equivalent to [T]. */
inline fun <reified T> classNameOf(): ClassName = T::class.asClassName()

/** Returns a [ClassName] equivalent to [Class]. */
inline fun Class<*>.asClassName(): ClassName = ClassName.get(this)

/** Returns a [ClassName] equivalent to [KClass]. */
inline fun KClass<*>.asClassName(): ClassName = ClassName.get(java)

/** Returns a [ClassName] equivalent to [TypeElement]. */
inline fun TypeElement.asClassName(): ClassName = ClassName.get(this)
