package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.TypeSpecBuilder
import com.hendraanggrian.javapoet.TypeSpecBuilderImpl
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeSpec

interface TypeSpecManager {

    fun type(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    fun type(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)

    fun interfaceType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    fun interfaceType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)

    fun enumType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    fun enumType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)

    fun anonymousType(typeArgumentsFormat: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)? = null)

    fun anonymousType(typeArguments: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)? = null)

    fun annotationType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null)

    fun annotationType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null)

    fun createType(name: String, builder: (TypeSpecBuilder.() -> Unit)?): TypeSpec =
        TypeSpecBuilderImpl(TypeSpec.classBuilder(name))
            .also { builder?.invoke(it) }
            .nativeBuilder
            .build()

    fun createType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?): TypeSpec =
        TypeSpecBuilderImpl(TypeSpec.classBuilder(className))
            .also { builder?.invoke(it) }
            .nativeBuilder
            .build()

    fun createInterfaceType(name: String, builder: (TypeSpecBuilder.() -> Unit)?): TypeSpec =
        TypeSpecBuilderImpl(TypeSpec.interfaceBuilder(name))
            .also { builder?.invoke(it) }
            .nativeBuilder
            .build()

    fun createInterfaceType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?): TypeSpec =
        TypeSpecBuilderImpl(TypeSpec.interfaceBuilder(className))
            .also { builder?.invoke(it) }
            .nativeBuilder
            .build()

    fun createEnumType(name: String, builder: (TypeSpecBuilder.() -> Unit)?): TypeSpec =
        TypeSpecBuilderImpl(TypeSpec.enumBuilder(name))
            .also { builder?.invoke(it) }
            .nativeBuilder
            .build()

    fun createEnumType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?): TypeSpec =
        TypeSpecBuilderImpl(TypeSpec.enumBuilder(className))
            .also { builder?.invoke(it) }
            .nativeBuilder
            .build()

    fun createAnonymousType(
        typeArgumentsFormat: String,
        vararg args: Any,
        builder: (TypeSpecBuilder.() -> Unit)?
    ): TypeSpec = TypeSpecBuilderImpl(TypeSpec.anonymousClassBuilder(typeArgumentsFormat, *args))
        .also { builder?.invoke(it) }
        .nativeBuilder
        .build()

    fun createAnonymousType(typeArguments: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)?): TypeSpec =
        TypeSpecBuilderImpl(TypeSpec.anonymousClassBuilder(typeArguments))
            .also { builder?.invoke(it) }
            .nativeBuilder
            .build()

    fun createAnnotationType(name: String, builder: (TypeSpecBuilder.() -> Unit)?): TypeSpec =
        TypeSpecBuilderImpl(TypeSpec.annotationBuilder(name))
            .also { builder?.invoke(it) }
            .nativeBuilder
            .build()

    fun createAnnotationType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?): TypeSpec =
        TypeSpecBuilderImpl(TypeSpec.annotationBuilder(className))
            .also { builder?.invoke(it) }
            .nativeBuilder
            .build()
}