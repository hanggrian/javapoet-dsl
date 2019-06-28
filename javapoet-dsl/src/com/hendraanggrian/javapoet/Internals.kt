@file:Suppress("SpellCheckingInspection")

package com.hendraanggrian.javapoet

import javax.lang.model.element.Modifier

internal const val NO_GETTER: String = "Property does not have a getter"

/** Some mutable backing fields are only used to set value. */
@PublishedApi
internal fun noGetter(): Nothing = throw UnsupportedOperationException(NO_GETTER)

internal interface ModifieredSpecBuilder {

    /**
     * Add single/multiple [Modifier] to this spec builder.
     * A preferable way to achieve this is to use [plus] operator (e.g.: `modifiers = public + static`).
     */
    var modifiers: Collection<Modifier>

    /** Instead of recreating a collection every [Collection.plus], add the item to this collection. */
    operator fun MutableCollection<Modifier>.plus(others: MutableCollection<Modifier>): MutableCollection<Modifier> =
        also { it += others }
}