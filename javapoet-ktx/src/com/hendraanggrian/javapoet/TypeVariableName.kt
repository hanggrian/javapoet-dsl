package com.hendraanggrian.javapoet

import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import java.lang.reflect.Type
import javax.lang.model.element.TypeParameterElement
import javax.lang.model.type.TypeVariable
import kotlin.reflect.KClass

/** Returns a [TypeVariableName] without bounds. */
fun String.typeVariableBy(): TypeVariableName = TypeVariableName.get(this)

/** Returns a [TypeVariableName] with [TypeName] bounds. */
fun String.typeVariableBy(vararg bounds: TypeName): TypeVariableName = TypeVariableName.get(this, *bounds)

/** Returns a [TypeVariableName] with [Type] bounds. */
fun String.typeVariableBy(vararg bounds: Type): TypeVariableName = TypeVariableName.get(this, *bounds)

/** Returns a [TypeVariableName] with [KClass] bounds. */
fun String.typeVariableBy(vararg bounds: KClass<*>): TypeVariableName =
    TypeVariableName.get(this, *bounds.toJavaClasses())

/**
 * Returns a [TypeVariableName] equivalent to [TypeParameterElement].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/javax.lang.model.element.-type-parameter-element/as-type-variable-name/)
 */
fun TypeParameterElement.asTypeVariableName(): TypeVariableName = TypeVariableName.get(this)

/**
 * Returns a [TypeVariableName] equivalent to [TypeVariable].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/javax.lang.model.type.-type-variable/as-type-variable-name/)
 */
fun TypeVariable.asTypeVariableName(): TypeVariableName = TypeVariableName.get(this)

/**
 * Returns a [TypeVariableName] equivalent to [java.lang.reflect.TypeVariable].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/javax.lang.model.type.-type-variable/as-type-variable-name/)
 */
fun java.lang.reflect.TypeVariable<*>.asTypeVariableName(): TypeVariableName = TypeVariableName.get(this)
