package com.hendraanggrian.javapoet.collections

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

/** A [TypeSpecList] is responsible for managing a set of type instances. */
open class TypeSpecList internal constructor(actualList: MutableList<TypeSpec>) :
    MutableList<TypeSpec> by actualList {

    /** Add class type from name. */
    fun addClass(type: String): Boolean = add(classTypeSpecOf(type))

    /** Add class type from [ClassName]. */
    fun addClass(type: ClassName): Boolean = add(classTypeSpecOf(type))

    /** Add interface type from name. */
    fun addInterface(type: String): Boolean = add(interfaceTypeSpecOf(type))

    /** Add interface type from [ClassName]. */
    fun addInterface(type: ClassName): Boolean = add(interfaceTypeSpecOf(type))

    /** Add enum type from name. */
    fun addEnum(type: String): Boolean = add(enumTypeSpecOf(type))

    /** Add enum type from [ClassName]. */
    fun addEnum(type: ClassName): Boolean = add(enumTypeSpecOf(type))

    /** Add anonymous type from formatting. */
    fun addAnonymous(format: String, vararg args: Any): Boolean = add(anonymousTypeSpecOf(format, *args))

    /** Add anonymous type from [CodeBlock]. */
    fun addAnonymous(code: CodeBlock): Boolean = add(anonymousTypeSpecOf(code))

    /** Add annotation type from name. */
    fun addAnnotation(type: String): Boolean = add(annotationTypeSpecOf(type))

    /** Add annotation type from [ClassName]. */
    fun addAnnotation(type: ClassName): Boolean = add(annotationTypeSpecOf(type))

    /** Add class type from name with custom initialization [builderAction]. */
    inline fun addClass(
        type: String,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildClassTypeSpec(type, builderAction))

    /** Add class type from [ClassName] with custom initialization [builderAction]. */
    inline fun addClass(
        type: ClassName,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildClassTypeSpec(type, builderAction))

    /** Add interface type from name with custom initialization [builderAction]. */
    inline fun addInterface(
        type: String,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildInterfaceTypeSpec(type, builderAction))

    /** Add interface type from [ClassName] with custom initialization [builderAction]. */
    inline fun addInterface(
        type: ClassName,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildInterfaceTypeSpec(type, builderAction))

    /** Add enum type from name with custom initialization [builderAction]. */
    inline fun addEnum(
        type: String,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildEnumTypeSpec(type, builderAction))

    /** Add enum type from [ClassName] with custom initialization [builderAction]. */
    inline fun addEnum(
        type: ClassName,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildEnumTypeSpec(type, builderAction))

    /**
     * Add anonymous type from formatting with custom initialization [builderAction].
     * Not inlining this function since `buildAnonymousType` is not inlined.
     */
    fun addAnonymous(
        format: String,
        vararg args: Any,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildAnonymousTypeSpec(format, *args, builderAction = builderAction))

    /** Add anonymous type from [CodeBlock] with custom initialization [builderAction]. */
    inline fun addAnonymous(
        code: CodeBlock,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildAnonymousTypeSpec(code, builderAction = builderAction))

    /** Add annotation type from name with custom initialization [builderAction]. */
    inline fun addAnnotation(
        type: String,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildAnnotationTypeSpec(type, builderAction))

    /** Add annotation type from [ClassName] with custom initialization [builderAction]. */
    inline fun addAnnotation(
        type: ClassName,
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildAnnotationTypeSpec(type, builderAction))
}

/** Receiver for the `types` function type providing an extended set of operators for the configuration. */
@JavapoetDslMarker
class TypeSpecListScope(actualList: TypeSpecList) : TypeSpecList(actualList) {

    /** Convenient method to add class with receiver type. */
    inline operator fun String.invoke(
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = addClass(this, builderAction)

    /** Convenient method to add class with receiver type. */
    inline operator fun ClassName.invoke(
        builderAction: TypeSpecBuilder.() -> Unit
    ): Boolean = addClass(this, builderAction)
}
