package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import javax.lang.model.element.TypeElement
import kotlin.reflect.KClass

/**
 * Returns a [ClassName] equivalent to [T].
 * @see typeNameOf
 */
inline fun <reified T> classNameOf(): ClassName = T::class.asClassName()

/** Returns a [ClassName] created from the given parts. */
fun String.classOf(simpleName: String, vararg simpleNames: String): ClassName =
    ClassName.get(this, simpleName, *simpleNames)

/**
 * Returns a [ClassName] equivalent to [TypeElement].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/javax.lang.model.element.-type-element/as-class-name/)
 */
fun TypeElement.asClassName(): ClassName = ClassName.get(this)

/**
 * Returns a [ClassName] equivalent to [Class].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/java.lang.-class/as-class-name/)
 */
fun Class<*>.asClassName(): ClassName = ClassName.get(this)

/**
 * Returns a [ClassName] equivalent to [KClass].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/kotlin.reflect.-k-class/as-class-name/)
 */
fun KClass<*>.asClassName(): ClassName = ClassName.get(java)
