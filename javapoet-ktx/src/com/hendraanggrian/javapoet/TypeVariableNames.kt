package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import javax.lang.model.element.TypeParameterElement
import javax.lang.model.type.TypeVariable
import kotlin.reflect.KClass

/** Returns type variable named [name] without bounds. */
fun typeVariableNameOf(name: String): TypeVariableName =
    TypeVariableName.get(name)

/** Returns type variable named [name] with [bounds]. */
fun typeVariableNameOf(name: String, vararg bounds: TypeName): TypeVariableName =
    TypeVariableName.get(name, *bounds)

/** Returns type variable named [name] with [bounds]. */
fun typeVariableNameOf(name: String, vararg bounds: KClass<*>): TypeVariableName =
    TypeVariableName.get(name, *bounds.toJavaClasses())

/** Returns type variable equivalent to [mirror]. */
fun typeVariableNameOf(mirror: TypeVariable): TypeVariableName =
    TypeVariableName.get(mirror)

/** Returns type variable equivalent to [element]. */
fun typeVariableNameOf(element: TypeParameterElement): TypeVariableName =
    TypeVariableName.get(element)

/** Returns type variable equivalent to [type]. */
fun typeVariableNameOf(type: java.lang.reflect.TypeVariable<*>): TypeVariableName =
    TypeVariableName.get(type)
