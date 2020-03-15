@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * Returns a [ParameterizedTypeName] applying [TypeName] arguments to [ClassName].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/-parameterized-type-name/parameterized-by/)
 */
inline fun ClassName.parameterizedBy(vararg typeArguments: TypeName): ParameterizedTypeName =
    ParameterizedTypeName.get(this, *typeArguments)

/** Returns a [ParameterizedTypeName] applying [Type] arguments to [ClassName]. */
fun ClassName.parameterizedBy(vararg typeArguments: Type = emptyArray()): ParameterizedTypeName =
    parameterizedBy(*typeArguments.map { it.asTypeName() }.toTypedArray())

/** Returns a [ParameterizedTypeName] applying [KClass] arguments to [ClassName]. */
fun ClassName.parameterizedBy(vararg typeArguments: KClass<*> = emptyArray()): ParameterizedTypeName =
    parameterizedBy(*typeArguments.map { it.asTypeName() }.toTypedArray())

/**
 * Returns a [ParameterizedTypeName] applying [Type] arguments to [Class].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/-parameterized-type-name/parameterized-by/)
 */
inline fun Class<*>.parameterizedBy(vararg typeArguments: Type): ParameterizedTypeName =
    ParameterizedTypeName.get(this, *typeArguments)

/** Returns a [ParameterizedTypeName] applying [TypeName] arguments to [Class]. */
fun Class<*>.parameterizedBy(vararg typeArguments: TypeName = emptyArray()): ParameterizedTypeName =
    asClassName().parameterizedBy(*typeArguments)

/** Returns a [ParameterizedTypeName] applying [KClass] arguments to [Class]. */
fun Class<*>.parameterizedBy(vararg typeArguments: KClass<*> = emptyArray()): ParameterizedTypeName =
    asClassName().parameterizedBy(*typeArguments)

/**
 * Returns a [ParameterizedTypeName] applying [KClass] arguments to [KClass].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/-parameterized-type-name/parameterized-by/)
 */
inline fun KClass<*>.parameterizedBy(vararg typeArguments: KClass<*>): ParameterizedTypeName =
    ParameterizedTypeName.get(java, *typeArguments.toJavaClasses())

/** Returns a [ParameterizedTypeName] applying [Type] arguments to [KClass]. */
fun KClass<*>.parameterizedBy(vararg typeArguments: Type = emptyArray()): ParameterizedTypeName =
    asClassName().parameterizedBy(*typeArguments)

/** Returns a [ParameterizedTypeName] applying [TypeName] arguments to [KClass]. */
fun KClass<*>.parameterizedBy(vararg typeArguments: TypeName = emptyArray()): ParameterizedTypeName =
    asClassName().parameterizedBy(*typeArguments)

/**
 * Returns a [ParameterizedTypeName] applying [TypeName] argument list to [ClassName].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/-parameterized-type-name/parameterized-by/)
 */
fun ClassName.parameterizedBy(typeArguments: Collection<TypeName>): ParameterizedTypeName =
    parameterizedBy(*typeArguments.toTypedArray())

/**
 * Returns a [ParameterizedTypeName] applying [Type] argument list to [Class].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/-parameterized-type-name/parameterized-by/)
 */
fun Class<*>.parameterizedBy(typeArguments: Collection<Type>): ParameterizedTypeName =
    parameterizedBy(*typeArguments.toTypedArray())

/**
 * Returns a [ParameterizedTypeName] applying [KClass] argument list to [KClass].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/-parameterized-type-name/parameterized-by/)
 */
fun KClass<*>.parameterizedBy(typeArguments: Collection<KClass<*>>): ParameterizedTypeName =
    parameterizedBy(*typeArguments.toTypedArray())

/**
 * Returns a [ParameterizedTypeName] equivalent to [ParameterizedType].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/java.lang.reflect.-parameterized-type/as-parameterized-type-name/)
 */
inline fun ParameterizedType.asParameterizedTypeName(): ParameterizedTypeName = ParameterizedTypeName.get(this)
