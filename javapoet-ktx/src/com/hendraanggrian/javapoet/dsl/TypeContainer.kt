package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.TypeSpecs
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
        add(TypeSpecs.classOf(type))

    /** Add class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addClass(type: String, builderAction: TypeSpecs.Builder.() -> Unit): TypeSpec =
        add(TypeSpecs.classOf(type, builderAction))

    /** Add class type from [type], returning the type added. */
    fun addClass(type: ClassName): TypeSpec =
        add(TypeSpecs.classOf(type))

    /** Add class type from [type] with custom initialization [builder], returning the type added. */
    inline fun addClass(type: ClassName, builderAction: TypeSpecs.Builder.() -> Unit): TypeSpec =
        add(TypeSpecs.classOf(type, builderAction))

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: String): TypeSpec =
        add(TypeSpecs.interfaceOf(type))

    /** Add interface type from [type] with custom initialization [builder], returning the type added. */
    inline fun addInterface(type: String, builderAction: TypeSpecs.Builder.() -> Unit): TypeSpec =
        add(TypeSpecs.interfaceOf(type, builderAction))

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: ClassName): TypeSpec =
        add(TypeSpecs.interfaceOf(type))

    /** Add interface type from [type] with custom initialization [builder], returning the type added. */
    inline fun addInterface(type: ClassName, builderAction: TypeSpecs.Builder.() -> Unit): TypeSpec =
        add(TypeSpecs.interfaceOf(type, builderAction))

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: String): TypeSpec =
        add(TypeSpecs.enumOf(type))

    /** Add enum type from [type] with custom initialization [builder], returning the type added. */
    inline fun addEnum(type: String, builderAction: TypeSpecs.Builder.() -> Unit): TypeSpec =
        add(TypeSpecs.enumOf(type, builderAction))

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: ClassName): TypeSpec =
        add(TypeSpecs.enumOf(type))

    /** Add enum type from [type] with custom initialization [builder], returning the type added. */
    inline fun addEnum(type: ClassName, builderAction: TypeSpecs.Builder.() -> Unit): TypeSpec =
        add(TypeSpecs.enumOf(type, builderAction))

    /** Add anonymous type from block, returning the type added. */
    fun addAnonymous(format: String, vararg args: Any): TypeSpec =
        add(TypeSpecs.anonymousOf(format, *args))

    /** Add anonymous type from block with custom initialization [builder], returning the type added. */
    inline fun addAnonymous(format: String, vararg args: Any, builderAction: TypeSpecs.Builder.() -> Unit): TypeSpec =
        add(TypeSpecs.anonymousOf(format, *args, builderAction = builderAction))

    /** Add anonymous type from [block], returning the type added. */
    fun addAnonymous(block: CodeBlock): TypeSpec =
        add(TypeSpecs.anonymousOf(block))

    /** Add anonymous type from [block] with custom initialization [builder], returning the type added. */
    inline fun addAnonymous(block: CodeBlock, builderAction: TypeSpecs.Builder.() -> Unit): TypeSpec =
        add(TypeSpecs.anonymousOf(block, builderAction = builderAction))

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: String): TypeSpec =
        add(TypeSpecs.annotationOf(type))

    /** Add annotation type from [type] with custom initialization [builder], returning the type added. */
    inline fun addAnnotation(type: String, builderAction: TypeSpecs.Builder.() -> Unit): TypeSpec =
        add(TypeSpecs.annotationOf(type, builderAction))

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: ClassName): TypeSpec =
        add(TypeSpecs.annotationOf(type))

    /** Add annotation type from [type] with custom initialization [builder], returning the type added. */
    inline fun addAnnotation(type: ClassName, builderAction: TypeSpecs.Builder.() -> Unit): TypeSpec =
        add(TypeSpecs.annotationOf(type, builderAction))

    /** Configure this container with DSL. */
    inline operator fun TypeContainer.invoke(configuration: TypeContainerScope.() -> Unit) =
        TypeContainerScope(this).configuration()
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
class TypeContainerScope @PublishedApi internal constructor(collection: TypeCollection) :
    TypeContainer(), TypeCollection by collection
