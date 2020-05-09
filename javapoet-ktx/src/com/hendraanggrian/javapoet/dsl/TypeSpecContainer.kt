package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.TypeSpecBuilder
import com.hendraanggrian.javapoet.annotationTypeSpecOf
import com.hendraanggrian.javapoet.anonymousTypeSpecOf
import com.hendraanggrian.javapoet.buildAnnotationTypeSpec
import com.hendraanggrian.javapoet.buildAnonymousTypeSpec
import com.hendraanggrian.javapoet.buildClassTypeSpec
import com.hendraanggrian.javapoet.buildEnumTypeSpec
import com.hendraanggrian.javapoet.buildInterfaceTypeSpec
import com.hendraanggrian.javapoet.classTypeSpecOf
import com.hendraanggrian.javapoet.enumTypeSpecOf
import com.hendraanggrian.javapoet.interfaceTypeSpecOf
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeSpec

private interface TypeSpecAddable {

    /** Add type to this container. */
    fun add(spec: TypeSpec)

    /** Add collection of types to this container. */
    fun addAll(specs: Iterable<TypeSpec>): Boolean
}

/** A [TypeSpecContainer] is responsible for managing a set of type instances. */
abstract class TypeSpecContainer : TypeSpecAddable {

    /** Add class type from [type], returning the type added. */
    fun addClass(type: String): TypeSpec =
        classTypeSpecOf(type).also { add(it) }

    /** Add class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addClass(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildClassTypeSpec(type, builderAction).also { add(it) }

    /** Add class type from [type], returning the type added. */
    fun addClass(type: ClassName): TypeSpec =
        classTypeSpecOf(type).also { add(it) }

    /** Add class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addClass(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildClassTypeSpec(type, builderAction).also { add(it) }

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: String): TypeSpec =
        interfaceTypeSpecOf(type).also { add(it) }

    /** Add interface type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addInterface(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildInterfaceTypeSpec(type, builderAction).also { add(it) }

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: ClassName): TypeSpec =
        interfaceTypeSpecOf(type).also { add(it) }

    /** Add interface type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addInterface(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildInterfaceTypeSpec(type, builderAction).also { add(it) }

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: String): TypeSpec =
        enumTypeSpecOf(type).also { add(it) }

    /** Add enum type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addEnum(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildEnumTypeSpec(type, builderAction).also { add(it) }

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: ClassName): TypeSpec =
        enumTypeSpecOf(type).also { add(it) }

    /** Add enum type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addEnum(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildEnumTypeSpec(type, builderAction).also { add(it) }

    /** Add anonymous type from block, returning the type added. */
    fun addAnonymous(format: String, vararg args: Any): TypeSpec =
        anonymousTypeSpecOf(format, *args).also { add(it) }

    /**
     * Add anonymous type from block with custom initialization [builderAction], returning the type added.
     * Not inlining this function since `buildAnonymousType` is not inlined.
     */
    fun addAnonymous(format: String, vararg args: Any, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnonymousTypeSpec(format, *args, builderAction = builderAction).also { add(it) }

    /** Add anonymous type from [code], returning the type added. */
    fun addAnonymous(code: CodeBlock): TypeSpec =
        anonymousTypeSpecOf(code).also { add(it) }

    /** Add anonymous type from [code] with custom initialization [builderAction], returning the type added. */
    inline fun addAnonymous(code: CodeBlock, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnonymousTypeSpec(code, builderAction = builderAction).also { add(it) }

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: String): TypeSpec =
        annotationTypeSpecOf(type).also { add(it) }

    /** Add annotation type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addAnnotation(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnnotationTypeSpec(type, builderAction).also { add(it) }

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: ClassName): TypeSpec =
        annotationTypeSpecOf(type).also { add(it) }

    /** Add annotation type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addAnnotation(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnnotationTypeSpec(type, builderAction).also { add(it) }

    /** Convenient method to add type with operator function. */
    operator fun plusAssign(spec: TypeSpec): Unit = add(spec)

    /** Convenient method to add collection of types with operator function. */
    operator fun plusAssign(specs: Iterable<TypeSpec>) {
        addAll(specs)
    }
}

/** Receiver for the `types` function type providing an extended set of operators for the configuration. */
@JavapoetDslMarker
class TypeSpecContainerScope(container: TypeSpecContainer) : TypeSpecContainer(),
    TypeSpecAddable by container {

    /** Convenient method to add class with receiver type. */
    inline operator fun String.invoke(builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        addClass(this, builderAction)

    /** Convenient method to add class with receiver type. */
    inline operator fun ClassName.invoke(builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        addClass(this, builderAction)
}
