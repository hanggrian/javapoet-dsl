package com.hanggrian.javapoet.internals

import kotlin.reflect.KClass

public object Internals {
    public fun <T> format(
        format: String,
        args: Array<*>,
        action: (format2: String, args2: Array<*>) -> T,
    ): T {
        var s = ""
        var isTemplate = false
        format.forEachIndexed { index, c ->
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
            if (index == format.lastIndex && isTemplate) {
                s += '$'
            }
        }
        return action(s, args.map { (it as? KClass<*>)?.java ?: it }.toTypedArray())
    }

    public fun <T> format(
        format: String,
        args: Map<String, *>,
        action: (format2: String, args2: Map<String, *>) -> T,
    ): T =
        format(format, args.values.toTypedArray()) { s, array ->
            action(s, args.keys.zip(array).toMap())
        }
}
