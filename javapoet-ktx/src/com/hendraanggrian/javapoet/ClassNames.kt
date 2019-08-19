package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import javax.lang.model.element.TypeElement
import kotlin.reflect.KClass

fun classNameOf(type: KClass<*>): ClassName =
    ClassName.get(type.java)

inline fun <reified T> classNameOf(): ClassName =
    classNameOf(T::class)

/** Returns a new [ClassName] instance for the given fully-qualified class name string. */
fun classNameOf(name: String): ClassName =
    ClassName.bestGuess(name)

/** Returns a class name created from the given parts */
fun classNameOf(packageName: String, simpleName: String, vararg simpleNames: String): ClassName =
    ClassName.get(packageName, simpleName, *simpleNames)

/** Returns the class name for [element]. */
fun classNameOf(element: TypeElement): ClassName =
    ClassName.get(element)
