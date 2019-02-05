package com.hendraanggrian.javapoet

internal const val NO_GETTER: String = "Property does not have a getter."

/** Some mutable backing fields are only used to set value. */
internal fun noGetter(): Nothing = fail { NO_GETTER }

/** Equivalent to [error] but throws [UnsupportedOperationException] instead. */
private inline fun fail(lazyMessage: () -> Any = { "Unsupported operation." }): Nothing =
    throw UnsupportedOperationException(lazyMessage().toString())