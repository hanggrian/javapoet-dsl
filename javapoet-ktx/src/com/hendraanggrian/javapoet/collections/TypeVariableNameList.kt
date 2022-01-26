package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.genericsBy
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** A [TypeVariableNameList] is responsible for managing a set of type variable name instances. */
class TypeVariableNameList internal constructor(actualList: MutableList<TypeVariableName>) :
    MutableList<TypeVariableName> by actualList {

    /** Add a [TypeVariableName] without bounds. */
    fun add(name: String): Boolean = add(name.genericsBy())

    /** Returns a [TypeVariableName] with [TypeName] bounds. */
    fun add(name: String, vararg bounds: TypeName): Boolean = add(name.genericsBy(*bounds))

    /** Returns a [TypeVariableName] with [Type] bounds. */
    fun add(name: String, vararg bounds: Type): Boolean = add(name.genericsBy(*bounds))

    /** Returns a [TypeVariableName] with [KClass] bounds. */
    fun add(name: String, vararg bounds: KClass<*>): Boolean = add(name.genericsBy(*bounds))

    /** Returns a [TypeVariableName] with collection of [TypeName] bounds. */
    fun add(name: String, bounds: List<TypeName>): Boolean = add(name.genericsBy(bounds))

    /** Returns a [TypeVariableName] with collection of [Type] bounds. */
    @JvmName("addType")
    fun add(name: String, bounds: Iterable<Type>): Boolean = add(name.genericsBy(bounds))

    /** Returns a [TypeVariableName] with collection of [KClass] bounds. */
    @JvmName("addClass")
    fun add(name: String, bounds: Iterable<KClass<*>>): Boolean = add(name.genericsBy(bounds))

    /** Convenient method to add type variable name with operator function. */
    inline operator fun plusAssign(name: String) {
        add(name)
    }
}
