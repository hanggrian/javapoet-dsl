package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier

internal const val NO_GETTER: String = "Property does not have a getter"

/** Some mutable backing fields are only used to set value. */
@PublishedApi
internal fun noGetter(): Nothing = throw UnsupportedOperationException(NO_GETTER)

/** Base type of all JavaPoet DSL spec builders. */
internal interface SpecBuilder<T> {

    /** Converts this DSL spec builder to native JavaPoet specs. */
    fun build(): T
}

/** Subclass of this type have access to many convenient inline functions to JavaPoet constant values. */
internal interface TypedSpecBuilder {

    val void: TypeName get() = TypeName.VOID
    val boolean: TypeName get() = TypeName.BOOLEAN
    val byte: TypeName get() = TypeName.BYTE
    val short: TypeName get() = TypeName.SHORT
    val int: TypeName get() = TypeName.INT
    val long: TypeName get() = TypeName.LONG
    val char: TypeName get() = TypeName.CHAR
    val float: TypeName get() = TypeName.FLOAT
    val double: TypeName get() = TypeName.DOUBLE
    val `object`: ClassName get() = TypeName.OBJECT
}

@Suppress("SpellCheckingInspection")
internal interface ModifieredSpecBuilder {

    val public: MutableCollection<Modifier> get() = mutableListOf(Modifier.PUBLIC)
    val protected: MutableCollection<Modifier> get() = mutableListOf(Modifier.PROTECTED)
    val private: MutableCollection<Modifier> get() = mutableListOf(Modifier.PRIVATE)
    val abstract: MutableCollection<Modifier> get() = mutableListOf(Modifier.ABSTRACT)
    val default: MutableCollection<Modifier> get() = mutableListOf(Modifier.DEFAULT)
    val static: MutableCollection<Modifier> get() = mutableListOf(Modifier.STATIC)
    val final: MutableCollection<Modifier> get() = mutableListOf(Modifier.FINAL)
    val transient: MutableCollection<Modifier> get() = mutableListOf(Modifier.TRANSIENT)
    val volatile: MutableCollection<Modifier> get() = mutableListOf(Modifier.VOLATILE)
    val synchronized: MutableCollection<Modifier> get() = mutableListOf(Modifier.SYNCHRONIZED)
    val native: MutableCollection<Modifier> get() = mutableListOf(Modifier.NATIVE)
    val strictfp: MutableCollection<Modifier> get() = mutableListOf(Modifier.STRICTFP)

    /**
     * Add single/multiple [Modifier] to this spec builder.
     * A preferable way to achieve this is to use [plus] operator (e.g.: `modifiers = public + static`).
     */
    var modifiers: Collection<Modifier>

    /** Instead of recreating a collection every [Collection.plus], add the item to this collection. */
    operator fun MutableCollection<Modifier>.plus(others: MutableCollection<Modifier>): MutableCollection<Modifier> =
        also { it += others }
}