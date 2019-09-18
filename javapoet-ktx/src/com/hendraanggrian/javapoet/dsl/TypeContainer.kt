package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.TypeSpecBuilder
import com.hendraanggrian.javapoet.buildAnnotationType
import com.hendraanggrian.javapoet.buildAnonymousType
import com.hendraanggrian.javapoet.buildClassType
import com.hendraanggrian.javapoet.buildEnumType
import com.hendraanggrian.javapoet.buildInterfaceType
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeSpec

private interface TypeAddable {

    /** Add type to this container, returning the type added. */
    fun add(spec: TypeSpec): TypeSpec
}

abstract class TypeCollection internal constructor() : TypeAddable {

    /** Add class type from [type], returning the type added. */
    fun addClass(type: String): TypeSpec =
        add(buildClassType(type))

    /** Add class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addClass(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildClassType(type, builderAction))

    /** Add class type from [type], returning the type added. */
    fun addClass(type: ClassName): TypeSpec =
        add(buildClassType(type))

    /** Add class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addClass(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildClassType(type, builderAction))

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: String): TypeSpec =
        add(buildInterfaceType(type))

    /** Add interface type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addInterface(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildInterfaceType(type, builderAction))

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: ClassName): TypeSpec =
        add(buildInterfaceType(type))

    /** Add interface type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addInterface(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildInterfaceType(type, builderAction))

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: String): TypeSpec =
        add(buildEnumType(type))

    /** Add enum type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addEnum(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildEnumType(type, builderAction))

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: ClassName): TypeSpec =
        add(buildEnumType(type))

    /** Add enum type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addEnum(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildEnumType(type, builderAction))

    /** Add anonymous type from block, returning the type added. */
    fun addAnonymous(format: String, vararg args: Any): TypeSpec =
        add(buildAnonymousType(format, *args))

    /** Add anonymous type from block with custom initialization [builderAction], returning the type added. */
    inline fun addAnonymous(format: String, vararg args: Any, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildAnonymousType(format, *args, builderAction = builderAction))

    /** Add anonymous type from [code], returning the type added. */
    fun addAnonymous(code: CodeBlock): TypeSpec =
        add(buildAnonymousType(code))

    /** Add anonymous type from [code] with custom initialization [builderAction], returning the type added. */
    inline fun addAnonymous(code: CodeBlock, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildAnonymousType(code, builderAction = builderAction))

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: String): TypeSpec =
        add(buildAnnotationType(type))

    /** Add annotation type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addAnnotation(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildAnnotationType(type, builderAction))

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: ClassName): TypeSpec =
        add(buildAnnotationType(type))

    /** Add annotation type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addAnnotation(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(buildAnnotationType(type, builderAction))
}

/** A [TypeContainer] is responsible for managing a set of type instances. */
abstract class TypeContainer internal constructor() : TypeCollection() {

    /** Configure this container with DSL. */
    inline operator fun invoke(configuration: TypeContainerScope.() -> Unit) =
        TypeContainerScope(this).configuration()
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@JavapoetDslMarker
class TypeContainerScope @PublishedApi internal constructor(container: TypeContainer) :
    TypeContainer(), TypeAddable by container
