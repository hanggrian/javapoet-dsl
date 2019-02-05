package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.TypeSpecBuilder
import com.hendraanggrian.javapoet.TypeSpecBuilderImpl
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeSpec

interface TypeSpecManager {

    fun type(name: String, builder: TypeSpecBuilder.() -> Unit)

    fun type(className: ClassName, builder: TypeSpecBuilder.() -> Unit)

    fun iface(name: String, builder: TypeSpecBuilder.() -> Unit)

    fun iface(className: ClassName, builder: TypeSpecBuilder.() -> Unit)

    fun enum(name: String, builder: TypeSpecBuilder.() -> Unit)

    fun enum(className: ClassName, builder: TypeSpecBuilder.() -> Unit)

    fun anonymous(typeArgumentsFormat: String, vararg args: Any, builder: TypeSpecBuilder.() -> Unit)

    fun anonymous(typeArguments: CodeBlock, builder: TypeSpecBuilder.() -> Unit)

    fun annotation(name: String, builder: TypeSpecBuilder.() -> Unit)

    fun annotation(className: ClassName, builder: TypeSpecBuilder.() -> Unit)

    fun createType(name: String, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        TypeSpecBuilderImpl(TypeSpec.classBuilder(name))
            .apply(builder)
            .nativeBuilder
            .build()

    fun createType(className: ClassName, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        TypeSpecBuilderImpl(TypeSpec.classBuilder(className))
            .apply(builder)
            .nativeBuilder
            .build()

    fun createInterface(name: String, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        TypeSpecBuilderImpl(TypeSpec.interfaceBuilder(name))
            .apply(builder)
            .nativeBuilder
            .build()

    fun createInterface(className: ClassName, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        TypeSpecBuilderImpl(TypeSpec.interfaceBuilder(className))
            .apply(builder)
            .nativeBuilder
            .build()

    fun createEnum(name: String, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        TypeSpecBuilderImpl(TypeSpec.enumBuilder(name))
            .apply(builder)
            .nativeBuilder
            .build()

    fun createEnum(className: ClassName, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        TypeSpecBuilderImpl(TypeSpec.enumBuilder(className))
            .apply(builder)
            .nativeBuilder
            .build()

    fun createAnonymous(
        typeArgumentsFormat: String,
        vararg args: Any,
        builder: TypeSpecBuilder.() -> Unit
    ): TypeSpec = TypeSpecBuilderImpl(TypeSpec.anonymousClassBuilder(typeArgumentsFormat, *args))
        .apply(builder)
        .nativeBuilder
        .build()

    fun createAnonymous(typeArguments: CodeBlock, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        TypeSpecBuilderImpl(TypeSpec.anonymousClassBuilder(typeArguments))
            .apply(builder)
            .nativeBuilder
            .build()

    fun createAnnotation(name: String, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        TypeSpecBuilderImpl(TypeSpec.annotationBuilder(name))
            .apply(builder)
            .nativeBuilder
            .build()

    fun createAnnotation(className: ClassName, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        TypeSpecBuilderImpl(TypeSpec.annotationBuilder(className))
            .apply(builder)
            .nativeBuilder
            .build()
}