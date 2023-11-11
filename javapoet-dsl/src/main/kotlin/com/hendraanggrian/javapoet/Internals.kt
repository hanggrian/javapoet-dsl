package com.hendraanggrian.javapoet

import kotlin.reflect.KClass

internal const val NO_GETTER: String = "Property does not have a getter"

internal fun noGetter(): Nothing = throw UnsupportedOperationException(NO_GETTER)

internal fun <T> String.internalFormat(
    args: Array<*>,
    action: (format2: String, args2: Array<*>) -> T,
): T {
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

internal fun <T> String.internalFormat(
    args: Map<String, *>,
    action: (format2: String, args2: Map<String, *>) -> T,
): T =
    internalFormat(args.values.toTypedArray()) { s, array ->
        action(s, args.keys.zip(array).toMap())
    }
