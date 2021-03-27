package io.github.hendraanggrian.javapoet.dsl

import com.squareup.javapoet.TypeName
import io.github.hendraanggrian.javapoet.SpecDslMarker
import io.github.hendraanggrian.javapoet.asTypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** A [TypeNameHandler] is responsible for managing a set of type name instances. */
open class TypeNameHandler internal constructor(actualList: MutableList<TypeName>) :
    MutableList<TypeName> by actualList {

    /** Add type name from [Class]. */
    fun add(type: Type): Boolean = add(type.asTypeName())

    /** Add type name from [KClass]. */
    fun add(type: KClass<*>): Boolean = add(type.asTypeName())

    /** Add type name from [T]. */
    inline fun <reified T> add(): Boolean = add(T::class.asTypeName())

    /** Convenient method to add type name with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun plusAssign(type: Type) {
        add(type)
    }

    /** Convenient method to add type name with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun plusAssign(type: KClass<*>) {
        add(type)
    }
}

/** Receiver for the `superinterfaces` block providing an extended set of operators for the configuration. */
@SpecDslMarker
class TypeNameHandlerScope internal constructor(actualList: MutableList<TypeName>) : TypeNameHandler(actualList)
