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

object TypeNames {

    fun of(mirror: TypeMirror): TypeName =
        TypeName.get(mirror)

    fun of(type: KClass<*>): TypeName =
        TypeName.get(type.java)

    inline fun <reified T> of(): TypeName =
        of(T::class)

    fun arrayOf(componentType: TypeName): ArrayTypeName =
        ArrayTypeName.of(componentType)

    fun arrayOf(componentType: KClass<*>): ArrayTypeName =
        ArrayTypeName.of(componentType.java)

    inline fun <reified T> arrayOf(): ArrayTypeName =
        arrayOf(T::class)

    fun arrayOf(mirror: ArrayType): ArrayTypeName =
        ArrayTypeName.get(mirror)

    fun arrayOf(mirror: GenericArrayType): ArrayTypeName =
        ArrayTypeName.get(mirror)

    fun classOf(type: KClass<*>): ClassName =
        ClassName.get(type.java)

    fun guessClass(type: String): ClassName =
        ClassName.bestGuess(type)

    fun classOf(packageName: String, simpleName: String, vararg simpleNames: String): ClassName =
        ClassName.get(packageName, simpleName, *simpleNames)

    fun classOf(element: TypeElement): ClassName =
        ClassName.get(element)

    fun parameterizedOf(rawType: ClassName, vararg typeArguments: TypeName): ParameterizedTypeName =
        ParameterizedTypeName.get(rawType, *typeArguments)

    fun parameterizedOf(rawType: KClass<*>, vararg typeArguments: KClass<*>): ParameterizedTypeName =
        ParameterizedTypeName.get(rawType.java, *typeArguments.mapJava())

    fun parameterizedOf(type: ParameterizedType): ParameterizedTypeName =
        ParameterizedTypeName.get(type)

    fun typeVariableOf(name: String): TypeVariableName =
        TypeVariableName.get(name)

    fun typeVariableOf(name: String, vararg bounds: TypeName): TypeVariableName =
        TypeVariableName.get(name, *bounds)

    fun typeVariableOf(name: String, vararg bounds: KClass<*>): TypeVariableName =
        TypeVariableName.get(name, *bounds.mapJava())

    fun typeVariableOf(mirror: TypeVariable): TypeVariableName =
        TypeVariableName.get(mirror)

    fun typeVariableOf(element: TypeParameterElement): TypeVariableName =
        TypeVariableName.get(element)

    fun typeVariableOf(type: java.lang.reflect.TypeVariable<*>): TypeVariableName =
        TypeVariableName.get(type)

    fun wildcardSubtypeOf(upperBound: TypeName): WildcardTypeName =
        WildcardTypeName.subtypeOf(upperBound)

    fun wildcardSubtypeOf(upperBound: KClass<*>): WildcardTypeName =
        WildcardTypeName.subtypeOf(upperBound.java)

    fun wildcardSupertypeOf(lowerBound: TypeName): WildcardTypeName =
        WildcardTypeName.supertypeOf(lowerBound)

    fun wildcardSupertypeOf(lowerBound: KClass<*>): WildcardTypeName =
        WildcardTypeName.supertypeOf(lowerBound.java)

    fun wildcardOf(mirror: WildcardType): TypeName =
        WildcardTypeName.get(mirror)

    fun wildcardOf(mirror: java.lang.reflect.WildcardType): TypeName =
        WildcardTypeName.get(mirror)
}
