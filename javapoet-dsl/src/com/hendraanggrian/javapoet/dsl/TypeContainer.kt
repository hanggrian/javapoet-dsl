@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.TypeSpecBuilder
import com.hendraanggrian.javapoet.buildAnnotationTypeSpec
import com.hendraanggrian.javapoet.buildAnonymousTypeSpec
import com.hendraanggrian.javapoet.buildClassTypeSpec
import com.hendraanggrian.javapoet.buildEnumTypeSpec
import com.hendraanggrian.javapoet.buildInterfaceTypeSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeSpec

abstract class TypeContainer internal constructor() : TypeContainerDelegate {

    inline operator fun plusAssign(spec: TypeSpec) = add(spec)

    inline operator fun invoke(configuration: TypeContainerScope.() -> Unit) =
        configuration(TypeContainerScope(this))
}

@JavapoetDslMarker
class TypeContainerScope @PublishedApi internal constructor(private val container: TypeContainer) :
    TypeContainerDelegate {

    override fun add(spec: TypeSpec) = container.add(spec)
}

internal interface TypeContainerDelegate {

    /** Add type to this spec builder. */
    fun add(spec: TypeSpec)

    /** Add class type to this spec builder. */
    fun addClassType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        add(buildClassTypeSpec(name, builder))

    /** Add class type to this spec builder. */
    fun addClassType(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        add(buildClassTypeSpec(type, builder))

    /** Add interface type to this spec builder. */
    fun addInterfaceType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        add(buildInterfaceTypeSpec(name, builder))

    /** Add interface type to this spec builder. */
    fun addInterfaceType(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        add(buildInterfaceTypeSpec(type, builder))

    /** Add enum type to this spec builder. */
    fun addEnumType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        add(buildEnumTypeSpec(name, builder))

    /** Add enum type to this spec builder. */
    fun addEnumType(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        add(buildEnumTypeSpec(type, builder))

    /** Add anonymous type to this spec builder. */
    fun addAnonymousType(format: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        add(buildAnonymousTypeSpec(format, *args, builder = builder))

    /** Add anonymous type to this spec builder. */
    fun addAnonymousType(block: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        add(buildAnonymousTypeSpec(block, builder))

    /** Add annotation type to this spec builder. */
    fun addAnnotationType(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        add(buildAnnotationTypeSpec(name, builder))

    /** Add annotation type to this spec builder. */
    fun addAnnotationType(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null) =
        add(buildAnnotationTypeSpec(type, builder))
}