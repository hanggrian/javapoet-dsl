package com.hendraanggrian.javapoet

import kotlin.reflect.KClass

/** Field deprecation message. */
internal const val NO_GETTER: String = "Property does not have a getter"

/** Some mutable backing fields are only used to set value. */
@PublishedApi
internal fun noGetter(): Nothing = throw UnsupportedOperationException(NO_GETTER)

/** Converts JavaPoet standard [format] and [args] to KotlinPoet. */
internal fun <T> String.formatWith(args: Array<*>, action: (String, Array<*>) -> T): T {
    var s = ""
    var isTemplate = false
    forEachIndexed { index, c ->
        when {
            isTemplate -> {
                when (c) {
                    '%' -> s += '%'
                    else -> s += "$$c"
                }
                isTemplate = false
            }
            c == '%' -> isTemplate = true
            else -> s += c
        }
        if (index == lastIndex && isTemplate) {
            s += '$'
        }
    }
    return action(s, args.map { (it as? KClass<*>)?.java ?: it }.toTypedArray())
}

/** Converts JavaPoet standard [format] and [args] to KotlinPoet. */
internal fun <T> String.formatWith(args: Map<String, *>, action: (String, Map<String, *>) -> T): T =
    formatWith(args.values.toTypedArray()) { s, array -> action(s, args.keys.zip(array).toMap()) }

/** Converts array of Kotlin classes to Java classes. */
internal fun Array<out KClass<*>>.toJavaClasses(): Array<Class<*>> = map { it.java }.toTypedArray()
