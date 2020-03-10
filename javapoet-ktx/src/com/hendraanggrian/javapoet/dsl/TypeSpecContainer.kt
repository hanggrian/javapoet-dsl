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

/** A [TypeSpecContainer] is responsible for managing a set of type instances. */
abstract class TypeSpecContainer {

    /** Add type to this container. */
    abstract fun add(spec: TypeSpec)

    /** Add class type from [type], returning the type added. */
    fun addClass(type: String): TypeSpec = buildClassType(type).also { add(it) }

    /** Add class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addClass(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildClassType(type, builderAction).also { add(it) }

    /** Add class type from [type], returning the type added. */
    fun addClass(type: ClassName): TypeSpec = buildClassType(type).also { add(it) }

    /** Add class type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addClass(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildClassType(type, builderAction).also { add(it) }

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: String): TypeSpec = buildInterfaceType(type).also { add(it) }

    /** Add interface type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addInterface(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildInterfaceType(type, builderAction).also { add(it) }

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: ClassName): TypeSpec = buildInterfaceType(type).also { add(it) }

    /** Add interface type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addInterface(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildInterfaceType(type, builderAction).also { add(it) }

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: String): TypeSpec = buildEnumType(type).also { add(it) }

    /** Add enum type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addEnum(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildEnumType(type, builderAction).also { add(it) }

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: ClassName): TypeSpec = buildEnumType(type).also { add(it) }

    /** Add enum type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addEnum(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildEnumType(type, builderAction).also { add(it) }

    /** Add anonymous type from block, returning the type added. */
    fun addAnonymous(format: String, vararg args: Any): TypeSpec = buildAnonymousType(format, *args).also { add(it) }

    /**
     * Add anonymous type from block with custom initialization [builderAction], returning the type added.
     * Not inlining this function since `buildAnonymousType` is not inlined.
     */
    fun addAnonymous(format: String, vararg args: Any, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnonymousType(format, *args, builderAction = builderAction).also { add(it) }

    /** Add anonymous type from [code], returning the type added. */
    fun addAnonymous(code: CodeBlock): TypeSpec = buildAnonymousType(code).also { add(it) }

    /** Add anonymous type from [code] with custom initialization [builderAction], returning the type added. */
    inline fun addAnonymous(code: CodeBlock, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnonymousType(code, builderAction = builderAction).also { add(it) }

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: String): TypeSpec = buildAnnotationType(type).also { add(it) }

    /** Add annotation type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addAnnotation(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnnotationType(type, builderAction).also { add(it) }

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: ClassName): TypeSpec = buildAnnotationType(type).also { add(it) }

    /** Add annotation type from [type] with custom initialization [builderAction], returning the type added. */
    inline fun addAnnotation(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnnotationType(type, builderAction).also { add(it) }

    /** Convenient method to add type with operator function. */
    operator fun plusAssign(spec: TypeSpec) = add(spec)
}

/** Receiver for the `types` function type providing an extended set of operators for the configuration. */
@JavapoetDslMarker
class TypeSpecContainerScope(private val container: TypeSpecContainer) : TypeSpecContainer() {

    override fun add(spec: TypeSpec) = container.add(spec)

    /** Convenient method to add class with receiver type. */
    inline operator fun String.invoke(builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        addClass(this, builderAction)

    /** Convenient method to add class with receiver type. */
    inline operator fun ClassName.invoke(builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
        addClass(this, builderAction)
}
