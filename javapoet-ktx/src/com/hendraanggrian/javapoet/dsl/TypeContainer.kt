package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.TypeSpecBuilder
import com.hendraanggrian.javapoet.buildAnnotationTypeSpec
import com.hendraanggrian.javapoet.buildAnonymousTypeSpec
import com.hendraanggrian.javapoet.buildClassTypeSpec
import com.hendraanggrian.javapoet.buildEnumTypeSpec
import com.hendraanggrian.javapoet.buildInterfaceTypeSpec
import com.hendraanggrian.javapoet.toAnnotationTypeSpec
import com.hendraanggrian.javapoet.toAnonymousTypeSpec
import com.hendraanggrian.javapoet.toClassTypeSpec
import com.hendraanggrian.javapoet.toEnumTypeSpec
import com.hendraanggrian.javapoet.toInterfaceTypeSpec
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
        add(type.toClassTypeSpec())

    /** Add class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addClass(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildClassTypeSpec(type, builderAction))

    /** Add class type from [type], returning the type added. */
    fun addClass(type: ClassName): TypeSpec =
        add(type.toClassTypeSpec())

    /** Add class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addClass(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildClassTypeSpec(type, builderAction))

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: String): TypeSpec =
        add(type.toInterfaceTypeSpec())

    /** Add interface type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addInterface(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildInterfaceTypeSpec(type, builderAction))

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: ClassName): TypeSpec =
        add(type.toInterfaceTypeSpec())

    /** Add interface type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addInterface(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildInterfaceTypeSpec(type, builderAction))

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: String): TypeSpec =
        add(type.toEnumTypeSpec())

    /** Add enum type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addEnum(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildEnumTypeSpec(type, builderAction))

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: ClassName): TypeSpec =
        add(type.toEnumTypeSpec())

    /** Add enum type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addEnum(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildEnumTypeSpec(type, builderAction))

    /** Add anonymous type from block, returning the type added. */
    fun addAnonymous(format: String, vararg args: Any): TypeSpec =
        add(format.toAnonymousTypeSpec(*args))

    /** Add anonymous type from block with custom initialization [builderAction], returning the type added. */
    inline fun addAnonymous(format: String, vararg args: Any, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildAnonymousTypeSpec(format, *args, builderAction = builderAction))

    /** Add anonymous type from [block], returning the type added. */
    fun addAnonymous(block: CodeBlock): TypeSpec =
        add(block.toAnonymousTypeSpec())

    /** Add anonymous type from [block] with custom initialization [builderAction], returning the type added. */
    inline fun addAnonymous(block: CodeBlock, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildAnonymousTypeSpec(block, builderAction = builderAction))

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: String): TypeSpec =
        add(type.toAnnotationTypeSpec())

    /** Add annotation type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addAnnotation(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildAnnotationTypeSpec(type, builderAction))

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: ClassName): TypeSpec =
        add(type.toAnnotationTypeSpec())

    /** Add annotation type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addAnnotation(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildAnnotationTypeSpec(type, builderAction))

    /** Configure this container with DSL. */
    inline operator fun TypeContainer.invoke(configuration: TypeContainerScope.() -> Unit) =
        TypeContainerScope(this).configuration()
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
class TypeContainerScope @PublishedApi internal constructor(collection: TypeCollection) :
    TypeContainer(), TypeCollection by collection
