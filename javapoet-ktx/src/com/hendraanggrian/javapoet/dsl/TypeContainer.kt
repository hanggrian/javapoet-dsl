package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.TypeSpecBuilder
import com.hendraanggrian.javapoet.buildAnnotationType
import com.hendraanggrian.javapoet.buildAnonymousType
import com.hendraanggrian.javapoet.buildClassType
import com.hendraanggrian.javapoet.buildEnumType
import com.hendraanggrian.javapoet.buildInterfaceType
import com.hendraanggrian.javapoet.toAnnotationType
import com.hendraanggrian.javapoet.toAnonymousType
import com.hendraanggrian.javapoet.toClassType
import com.hendraanggrian.javapoet.toEnumType
import com.hendraanggrian.javapoet.toInterfaceType
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeSpec

internal interface TypeCollection {

    /** Add type to this container, returning the type added. */
    fun add(spec: TypeSpec): TypeSpec
}

/** A [TypeContainer] is responsible for managing a set of type instances. */
abstract class TypeContainer internal constructor() : TypeCollection {

    /** Add class type from [type], returning the type added. */
    fun addClass(type: String): TypeSpec =
        add(type.toClassType())

    /** Add class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addClass(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildClassType(type, builderAction))

    /** Add class type from [type], returning the type added. */
    fun addClass(type: ClassName): TypeSpec =
        add(type.toClassType())

    /** Add class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addClass(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildClassType(type, builderAction))

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: String): TypeSpec =
        add(type.toInterfaceType())

    /** Add interface type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addInterface(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildInterfaceType(type, builderAction))

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: ClassName): TypeSpec =
        add(type.toInterfaceType())

    /** Add interface type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addInterface(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildInterfaceType(type, builderAction))

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: String): TypeSpec =
        add(type.toEnumType())

    /** Add enum type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addEnum(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildEnumType(type, builderAction))

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: ClassName): TypeSpec =
        add(type.toEnumType())

    /** Add enum type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addEnum(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildEnumType(type, builderAction))

    /** Add anonymous type from block, returning the type added. */
    fun addAnonymous(format: String, vararg args: Any): TypeSpec =
        add(format.toAnonymousType(*args))

    /** Add anonymous type from block with custom initialization [builderAction], returning the type added. */
    inline fun addAnonymous(format: String, vararg args: Any, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildAnonymousType(format, *args, builderAction = builderAction))

    /** Add anonymous type from [block], returning the type added. */
    fun addAnonymous(block: CodeBlock): TypeSpec =
        add(block.toAnonymousType())

    /** Add anonymous type from [block] with custom initialization [builderAction], returning the type added. */
    inline fun addAnonymous(block: CodeBlock, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildAnonymousType(block, builderAction = builderAction))

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: String): TypeSpec =
        add(type.toAnnotationType())

    /** Add annotation type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addAnnotation(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildAnnotationType(type, builderAction))

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: ClassName): TypeSpec =
        add(type.toAnnotationType())

    /** Add annotation type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addAnnotation(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildAnnotationType(type, builderAction))

    /** Configure this container with DSL. */
    inline operator fun TypeContainer.invoke(configuration: TypeContainerScope.() -> Unit) =
        TypeContainerScope(this).configuration()
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
class TypeContainerScope @PublishedApi internal constructor(collection: TypeCollection) :
    TypeContainer(), TypeCollection by collection
