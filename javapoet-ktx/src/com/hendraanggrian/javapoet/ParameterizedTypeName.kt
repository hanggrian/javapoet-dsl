package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * Returns a [ParameterizedTypeName] applying [KClass] to [T].
 * @see typeNameOf
 */
inline fun <reified T> parameterizedTypeNameOf(vararg typeArguments: KClass<*>): ParameterizedTypeName =
    T::class.parameterizedBy(*typeArguments)

/**
 * Returns a [ParameterizedTypeName] applying [TypeName] arguments to [ClassName].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/-parameterized-type-name/parameterized-by/)
 */
fun ClassName.parameterizedBy(vararg typeArguments: TypeName): ParameterizedTypeName =
    ParameterizedTypeName.get(this, *typeArguments)

/**
 * Returns a [ParameterizedTypeName] applying [Type] arguments to [Class].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/-parameterized-type-name/parameterized-by/)
 */
fun Class<*>.parameterizedBy(vararg typeArguments: Type): ParameterizedTypeName =
    ParameterizedTypeName.get(this, *typeArguments)

/**
 * Returns a [ParameterizedTypeName] applying [KClass] arguments to [KClass].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/-parameterized-type-name/parameterized-by/)
 */
fun KClass<*>.parameterizedBy(vararg typeArguments: KClass<*>): ParameterizedTypeName =
    ParameterizedTypeName.get(java, *typeArguments.toJavaClasses())

/**
 * Returns a [ParameterizedTypeName] equivalent to [ParameterizedType].
 *
 * **See Also**
 *
 * [KotlinPoet counterpart](https://square.github.io/kotlinpoet/1.x/kotlinpoet/com.squareup.kotlinpoet/java.lang.reflect.-parameterized-type/as-parameterized-type-name/)
 */
fun ParameterizedType.asParameterizedTypeName(): ParameterizedTypeName = ParameterizedTypeName.get(this)
