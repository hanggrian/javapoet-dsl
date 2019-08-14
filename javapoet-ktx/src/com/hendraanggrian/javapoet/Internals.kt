package com.hendraanggrian.javapoet

import kotlin.reflect.KClass

internal const val NO_GETTER: String = "Property does not have a getter"

/** Some mutable backing fields are only used to set value. */
@PublishedApi
internal fun noGetter(): Nothing = throw UnsupportedOperationException(NO_GETTER)

@PublishedApi
internal inline fun <T> format(format: String, args: Array<*>, action: (String, Array<*>) -> T): T = action(
    format.replace('%', '\$'),
    args.map {
        when (it) {
            is KClass<*> -> it.java
            else -> it
        }
    }.toTypedArray()
)

@PublishedApi
internal inline fun <T> format(format: String, args: Map<String, *>, action: (String, Map<String, *>) -> T): T =
    format(format, args.values.toTypedArray()) { s: String, array: Array<*> ->
        action(s, args.keys.zip(array).toMap())
    }
