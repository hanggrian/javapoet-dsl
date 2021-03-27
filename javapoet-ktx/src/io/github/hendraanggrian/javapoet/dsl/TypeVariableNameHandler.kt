package io.github.hendraanggrian.javapoet.dsl

import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import io.github.hendraanggrian.javapoet.SpecDslMarker
import io.github.hendraanggrian.javapoet.typeVarBy
import io.github.hendraanggrian.javapoet.typeVarOf
import java.lang.reflect.Type
import kotlin.reflect.KClass

/** A [TypeVariableNameHandler] is responsible for managing a set of type variable name instances. */
open class TypeVariableNameHandler internal constructor(actualList: MutableList<TypeVariableName>) :
    MutableList<TypeVariableName> by actualList {

    /** Add a [TypeVariableName] without bounds. */
    fun add(name: String): Boolean = add(name.typeVarOf())

    /** Returns a [TypeVariableName] with [TypeName] bounds. */
    fun add(name: String, vararg bounds: TypeName): Boolean = add(name.typeVarBy(*bounds))

    /** Returns a [TypeVariableName] with [Type] bounds. */
    fun add(name: String, vararg bounds: Type): Boolean = add(name.typeVarBy(*bounds))

    /** Returns a [TypeVariableName] with [KClass] bounds. */
    fun add(name: String, vararg bounds: KClass<*>): Boolean = add(name.typeVarBy(*bounds))

    /** Convenient method to add type name with operator function. */
    @Suppress("NOTHING_TO_INLINE")
    inline operator fun plusAssign(name: String) {
        add(name)
    }
}

/** Receiver for the `typeVariables` block providing an extended set of operators for the configuration. */
@SpecDslMarker
class TypeVariableNameHandlerScope internal constructor(actualList: MutableList<TypeVariableName>) :
    TypeVariableNameHandler(actualList) {

    /** @see TypeVariableNameHandler.add */
    operator fun String.invoke(vararg bounds: TypeName): Boolean = add(this, *bounds)

    /** @see TypeVariableNameHandler.add */
    operator fun String.invoke(vararg bounds: Type): Boolean = add(this, *bounds)

    /** @see TypeVariableNameHandler.add */
    operator fun String.invoke(vararg bounds: KClass<*>): Boolean = add(this, *bounds)
}
