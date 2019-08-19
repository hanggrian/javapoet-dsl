package com.hendraanggrian.javapoet

import kotlin.reflect.KClass

internal const val NO_GETTER: String = "Property does not have a getter"

/** Some mutable backing fields are only used to set value. */
@PublishedApi
internal fun noGetter(): Nothing = throw UnsupportedOperationException(NO_GETTER)

/** Converts JavaPoet standard [format] and [args] to KotlinPoet. */
@PublishedApi
internal inline fun <T> format(format: String, args: Array<*>, action: (String, Array<*>) -> T): T = action(
    format.replace('%', '\$'),
    args.map { (it as? KClass<*>)?.java ?: it }.toTypedArray()
)

/** Converts JavaPoet standard [format] and [args] to KotlinPoet. */
@PublishedApi
internal inline fun format(format: String, args: Map<String, *>, action: (String, Map<String, *>) -> Unit): Unit =
    format(format, args.values.toTypedArray()) { s: String, array: Array<*> ->
        action(s, args.keys.zip(array).toMap())
    }

/** Converts array of Kotlin classes to Java classes. */
internal fun Array<out KClass<*>>.mapJavaClass(): Array<Class<*>> = map { it.java }.toTypedArray()
