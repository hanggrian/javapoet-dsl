@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec

interface MethodSpecBuilder {

    val nativeBuilder: MethodSpec.Builder
}

class MethodSpecBuilderImpl(override val nativeBuilder: MethodSpec.Builder) : MethodSpecBuilder

interface TypeSpecBuilder {

    val nativeBuilder: TypeSpec.Builder

    fun type(name: String, builder: TypeSpecBuilder.() -> Unit) {
        nativeBuilder.addType(
            TypeSpecBuilderImpl(TypeSpec.classBuilder(name))
                .apply(builder)
                .nativeBuilder
                .build()
        )
    }

    fun method(name: String, builder: MethodSpecBuilder.() -> Unit) {
        nativeBuilder.addMethod(
            MethodSpecBuilderImpl(MethodSpec.methodBuilder(name))
                .apply(builder)
                .nativeBuilder
                .build()
        )
    }
}

class TypeSpecBuilderImpl(override val nativeBuilder: TypeSpec.Builder) : TypeSpecBuilder

interface JavaFileBuilder {

    fun type(name: String, builder: TypeSpecBuilder.() -> Unit) {
        TypeSpec.classBuilder(name)
    }

    fun type(className: ClassName, builder: TypeSpecBuilder.() -> Unit): TypeSpec.Builder =
        TypeSpec.classBuilder(className)

    fun iface(name: String, builder: TypeSpecBuilder.() -> Unit): TypeSpec.Builder =
        TypeSpec.interfaceBuilder(name)

    fun iface(className: ClassName, builder: TypeSpecBuilder.() -> Unit): TypeSpec.Builder =
        TypeSpec.interfaceBuilder(className)

    fun enum(name: String, builder: TypeSpecBuilder.() -> Unit): TypeSpec.Builder =
        TypeSpec.enumBuilder(name)

    fun enum(className: ClassName, builder: TypeSpecBuilder.() -> Unit): TypeSpec.Builder =
        TypeSpec.enumBuilder(className)

    fun anonymous(
        typeArgumentsFormat: String,
        vararg args: Any,
        builder: TypeSpecBuilder.() -> Unit
    ): TypeSpec.Builder = TypeSpec.anonymousClassBuilder(typeArgumentsFormat, *args)

    fun anonymous(typeArguments: CodeBlock, builder: TypeSpecBuilder.() -> Unit): TypeSpec.Builder =
        TypeSpec.anonymousClassBuilder(typeArguments)

    fun annotation(name: String, builder: TypeSpecBuilder.() -> Unit): TypeSpec.Builder =
        TypeSpec.annotationBuilder(name)

    fun annotation(className: ClassName, builder: TypeSpecBuilder.() -> Unit): TypeSpec.Builder =
        TypeSpec.annotationBuilder(className)
}

class JavaFileBuilderImpl : JavaFileBuilder {

}

inline fun buildJavaFile(packageName: String, builder: JavaFileBuilder.() -> Unit) {
    // JavaFile.builder(packageName)
}

fun asd() {
    buildJavaFile("cas") {
        type("asd") {
            type("asd") {

            }
            method("asd") {

            }
        }
    }
}