package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import javax.lang.model.element.TypeParameterElement
import javax.lang.model.type.TypeVariable
import kotlin.reflect.KClass

/** Returns a [TypeVariableName] named [name] without bounds. */
fun typeVariableNameOf(name: String): TypeVariableName =
    TypeVariableName.get(name)

/** Returns a [TypeVariableName] named [name] with [bounds]. */
fun typeVariableNameOf(name: String, vararg bounds: TypeName): TypeVariableName =
    TypeVariableName.get(name, *bounds)

/** Returns a [TypeVariableName] named [name] with [bounds]. */
fun typeVariableNameOf(name: String, vararg bounds: KClass<*>): TypeVariableName =
    TypeVariableName.get(name, *bounds.toJavaClasses())

/** Returns a [TypeVariableName] equivalent to this [TypeParameterElement].  */
fun TypeParameterElement.asTypeVariableName(): TypeVariableName =
    TypeVariableName.get(this)

/** Returns a [TypeVariableName] equivalent to this [TypeVariable].  */
fun TypeVariable.asTypeVariableName(): TypeVariableName =
    TypeVariableName.get(this)

/** Returns a [TypeVariableName] equivalent to this [java.lang.reflect.TypeVariable].  */
fun java.lang.reflect.TypeVariable<*>.asTypeVariableName(): TypeVariableName =
    TypeVariableName.get(this)
