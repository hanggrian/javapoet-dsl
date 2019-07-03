@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.TypeSpecBuilder
import com.hendraanggrian.javapoet.buildAnnotationTypeSpec
import com.hendraanggrian.javapoet.buildAnonymousTypeSpec
import com.hendraanggrian.javapoet.buildClassTypeSpec
import com.hendraanggrian.javapoet.buildEnumTypeSpec
import com.hendraanggrian.javapoet.buildInterfaceTypeSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeSpec

internal interface TypeCollection {

    fun add(spec: TypeSpec): TypeSpec

    fun addClass(type: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildClassTypeSpec(type, builder))

    fun addClass(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildClassTypeSpec(type, builder))

    fun addInterface(type: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildInterfaceTypeSpec(type, builder))

    fun addInterface(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildInterfaceTypeSpec(type, builder))

    fun addEnum(type: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildEnumTypeSpec(type, builder))

    fun addEnum(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildEnumTypeSpec(type, builder))

    fun addAnonymous(format: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildAnonymousTypeSpec(format, *args, builder = builder))

    fun addAnonymous(block: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildAnonymousTypeSpec(block, builder))

    fun addAnnotation(type: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildAnnotationTypeSpec(type, builder))

    fun addAnnotation(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildAnnotationTypeSpec(type, builder))
}

/** A [TypeContainer] is responsible for managing a set of type instances. */
abstract class TypeContainer internal constructor() : TypeCollection {

    inline operator fun TypeContainer.invoke(configuration: TypeContainerScope.() -> Unit) =
        TypeContainerScope(this).configuration()
}

/**
 * Receiver for the `types` block providing an extended set of operators for the configuration.
 */
class TypeContainerScope @PublishedApi internal constructor(collection: TypeCollection) :
    TypeContainer(), TypeCollection by collection