package com.hendraanggrian.javapoet

import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import com.squareup.javapoet.WildcardTypeName
import java.lang.reflect.GenericArrayType
import java.lang.reflect.ParameterizedType
import javax.lang.model.element.TypeElement
import javax.lang.model.element.TypeParameterElement
import javax.lang.model.type.ArrayType
import javax.lang.model.type.TypeMirror
import javax.lang.model.type.TypeVariable
import javax.lang.model.type.WildcardType
import kotlin.reflect.KClass

fun typeNameOf(mirror: TypeMirror): TypeName =
    TypeName.get(mirror)

fun typeNameOf(type: KClass<*>): TypeName =
    TypeName.get(type.java)

inline fun <reified T> typeNameOf(): TypeName =
    typeNameOf(T::class)

fun arrayTypeNameOf(componentType: TypeName): ArrayTypeName =
    ArrayTypeName.of(componentType)

fun arrayTypeNameOf(componentType: KClass<*>): ArrayTypeName =
    ArrayTypeName.of(componentType.java)

inline fun <reified T> arrayTypeNameOf(): ArrayTypeName =
    ArrayTypeName.of(T::class.java)

fun arrayTypeNameOf(mirror: ArrayType): ArrayTypeName =
    ArrayTypeName.get(mirror)

fun arrayTypeNameOf(type: GenericArrayType): ArrayTypeName =
    ArrayTypeName.get(type)

fun classNameOf(type: KClass<*>): ClassName =
    ClassName.get(type.java)

inline fun <reified T> classNameOf(): ClassName =
    classNameOf(T::class)

fun classNameOf(name: String): ClassName =
    ClassName.bestGuess(name)

fun classNameOf(packageName: String, simpleName: String, vararg simpleNames: String): ClassName =
    ClassName.get(packageName, simpleName, *simpleNames)

fun classNameOf(element: TypeElement): ClassName =
    ClassName.get(element)

fun parameterizedTypeNameOf(rawType: ClassName, vararg typeArguments: TypeName): ParameterizedTypeName =
    ParameterizedTypeName.get(rawType, *typeArguments)

fun parameterizedTypeNameOf(rawType: KClass<*>, vararg typeArguments: KClass<*>): ParameterizedTypeName =
    ParameterizedTypeName.get(rawType.java, *typeArguments.mapJava())

inline fun <reified T> parameterizedTypeNameOf(vararg typeArguments: KClass<*>): ParameterizedTypeName =
    parameterizedTypeNameOf(T::class, *typeArguments)

fun parameterizedTypeNameOf(type: ParameterizedType): ParameterizedTypeName =
    ParameterizedTypeName.get(type)

fun typeVariableNameOf(name: String): TypeVariableName =
    TypeVariableName.get(name)

fun typeVariableNameOf(name: String, vararg bounds: TypeName): TypeVariableName =
    TypeVariableName.get(name, *bounds)

fun typeVariableNameOf(name: String, vararg bounds: KClass<*>): TypeVariableName =
    TypeVariableName.get(name, *bounds.mapJava())

fun typeVariableNameOf(mirror: TypeVariable): TypeVariableName =
    TypeVariableName.get(mirror)

fun typeVariableNameOf(element: TypeParameterElement): TypeVariableName =
    TypeVariableName.get(element)

fun typeVariableNameOf(type: java.lang.reflect.TypeVariable<*>): TypeVariableName =
    TypeVariableName.get(type)

fun subtypeWildcardTypeNameOf(upperBound: TypeName): WildcardTypeName =
    WildcardTypeName.subtypeOf(upperBound)

fun subtypeWildcardTypeNameOf(upperBound: KClass<*>): WildcardTypeName =
    WildcardTypeName.subtypeOf(upperBound.java)

inline fun <reified T> subtypeWildcardTypeNameOf(): WildcardTypeName =
    subtypeWildcardTypeNameOf(T::class)

fun supertypeWildcardTypeNameOf(lowerBound: TypeName): WildcardTypeName =
    WildcardTypeName.supertypeOf(lowerBound)

fun supertypeWildcardTypeNameOf(lowerBound: KClass<*>): WildcardTypeName =
    WildcardTypeName.supertypeOf(lowerBound.java)

inline fun <reified T> supertypeWildcardTypeNameOf(): WildcardTypeName =
    supertypeWildcardTypeNameOf(T::class)

fun wildcardTypeNameOf(mirror: WildcardType): TypeName =
    WildcardTypeName.get(mirror)

fun wildcardTypeNameOf(wildcardName: java.lang.reflect.WildcardType): TypeName =
    WildcardTypeName.get(wildcardName)
