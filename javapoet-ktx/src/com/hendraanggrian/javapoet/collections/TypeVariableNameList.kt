package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.SpecLoader
import com.hendraanggrian.javapoet.createSpecLoader
import com.hendraanggrian.javapoet.genericsBy
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** A [TypeVariableNameList] is responsible for managing a set of type variable name instances. */
class TypeVariableNameList internal constructor(actualList: MutableList<TypeVariableName>) :
    MutableList<TypeVariableName> by actualList {

    /** Add a type variable name without bounds. */
    fun add(name: String): TypeVariableName = name.genericsBy().also(::add)

    /** Returns a type variable name with [TypeName] bounds. */
    fun add(name: String, vararg bounds: TypeName): TypeVariableName = name.genericsBy(*bounds).also(::add)

    /** Returns a type variable name with [Type] bounds. */
    fun add(name: String, vararg bounds: Type): TypeVariableName = name.genericsBy(*bounds).also(::add)

    /** Returns a type variable name with [KClass] bounds. */
    fun add(name: String, vararg bounds: KClass<*>): TypeVariableName = name.genericsBy(*bounds).also(::add)

    /** Returns a type variable name with collection of [TypeName] bounds. */
    fun add(name: String, bounds: List<TypeName>): TypeVariableName = name.genericsBy(bounds).also(::add)

    /** Returns a type variable name with collection of [Type] bounds. */
    @JvmName("addWithTypes")
    fun add(name: String, bounds: Iterable<Type>): TypeVariableName = name.genericsBy(bounds).also(::add)

    /** Returns a type variable name with collection of [KClass] bounds. */
    @JvmName("addWithClasses")
    fun add(name: String, bounds: Iterable<KClass<*>>): TypeVariableName = name.genericsBy(bounds).also(::add)

    /** Convenient method to add type variable name with operator function. */
    inline operator fun plusAssign(name: String) {
        add(name)
    }

    /** Property delegate for adding type variable name without bounds. */
    val adding: SpecLoader<TypeVariableName> get() = createSpecLoader(::add)

    /** Property delegate for adding type variable name with [TypeName] bounds. */
    fun adding(vararg bounds: TypeName): SpecLoader<TypeVariableName> = createSpecLoader { add(it, *bounds) }

    /** Property delegate for adding type variable name with [Type] bounds. */
    fun adding(vararg bounds: Type): SpecLoader<TypeVariableName> = createSpecLoader { add(it, *bounds) }

    /** Property delegate for adding type variable name with [KClass] bounds. */
    fun adding(vararg bounds: KClass<*>): SpecLoader<TypeVariableName> = createSpecLoader { add(it, *bounds) }

    /** Property delegate for adding type variable name with collection of [TypeName] bounds. */
    fun adding(bounds: List<TypeName>): SpecLoader<TypeVariableName> = createSpecLoader { add(it, bounds) }

    /** Property delegate for adding type variable name with collection of [Type] bounds. */
    @JvmName("addingType")
    fun adding(bounds: Iterable<Type>): SpecLoader<TypeVariableName> = createSpecLoader { add(it, bounds) }

    /** Property delegate for adding type variable name with collection of [KClass] bounds. */
    @JvmName("addingClass")
    fun adding(bounds: Iterable<KClass<*>>): SpecLoader<TypeVariableName> = createSpecLoader { add(it, bounds) }
}
