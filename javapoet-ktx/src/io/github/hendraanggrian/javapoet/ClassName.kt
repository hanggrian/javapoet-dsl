@file:Suppress("NOTHING_TO_INLINE")

package io.github.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import javax.lang.model.element.TypeElement
import kotlin.reflect.KClass

/** Returns a [ClassName] created from the given parts. */
inline fun String.classOf(simpleName: String, vararg simpleNames: String): ClassName =
    ClassName.get(this, simpleName, *simpleNames)

/**
 * Returns a [ClassName] equivalent to [Class].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/java.lang.-class/as-class-name/)
 */
inline fun Class<*>.asClassName(): ClassName = ClassName.get(this)

/**
 * Returns a [ClassName] equivalent to [KClass].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/kotlin.reflect.-k-class/as-class-name/)
 */
inline fun KClass<*>.asClassName(): ClassName = ClassName.get(java)

/**
 * Returns a [ClassName] equivalent to [TypeElement].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/javax.lang.model.element.-type-element/as-class-name/)
 */
inline fun TypeElement.asClassName(): ClassName = ClassName.get(this)
