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
    operator fun plusAssign(type: Type): Unit = plusAssign(type.asTypeName())

    /** Convenient method to add type name with operator function. */
    operator fun plusAssign(type: KClass<*>): Unit = plusAssign(type.asTypeName())
}

/** Receiver for the `superinterfaces` function type providing an extended set of operators for the configuration. */
@SpecDslMarker
class TypeNameHandlerScope(actualList: MutableList<TypeName>) : TypeNameHandler(actualList)
