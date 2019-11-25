package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import javax.lang.model.element.TypeElement
import kotlin.reflect.KClass

/** Returns a [ClassName] created from the given parts */
fun classNameOf(packageName: String, simpleName: String, vararg simpleNames: String): ClassName =
    ClassName.get(packageName, simpleName, *simpleNames)

/** Returns a [ClassName] equivalent to this [Class].  */
fun Class<*>.asClassName(): ClassName = ClassName.get(this)

/** Returns a [ClassName] equivalent to this [KClass].  */
fun KClass<*>.asClassName(): ClassName = java.asClassName()

/** Returns a [ClassName] equivalent to this [T].  */
inline fun <reified T> asClassName(): ClassName = T::class.asClassName()

/** Returns a [ClassName] equivalent to this [TypeElement].  */
fun TypeElement.asClassName(): ClassName = ClassName.get(this)
