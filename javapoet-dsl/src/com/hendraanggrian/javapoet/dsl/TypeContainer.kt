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

    inline operator fun plusAssign(spec: TypeSpec) {
        add(spec)
    }

    inline operator fun invoke(configuration: TypeContainerScope.() -> Unit) =
        configuration(TypeContainerScope(this))
}

@JavapoetDslMarker
class TypeContainerScope @PublishedApi internal constructor(private val container: TypeContainer) :
    TypeContainerDelegate {

    override fun add(spec: TypeSpec): TypeSpec = container.add(spec)

    inline operator fun String.invoke(noinline builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        addClass(this, builder)

    inline operator fun ClassName.invoke(noinline builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        addClass(this, builder)
}

internal interface TypeContainerDelegate {

    /** Add type to this spec builder. */
    fun add(spec: TypeSpec): TypeSpec

    /** Add class type to this spec builder. */
    fun addClass(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildClassTypeSpec(name, builder))

    /** Add class type to this spec builder. */
    fun addClass(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildClassTypeSpec(type, builder))

    /** Add interface type to this spec builder. */
    fun addInterface(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildInterfaceTypeSpec(name, builder))

    /** Add interface type to this spec builder. */
    fun addInterface(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildInterfaceTypeSpec(type, builder))

    /** Add enum type to this spec builder. */
    fun addEnum(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildEnumTypeSpec(name, builder))

    /** Add enum type to this spec builder. */
    fun addEnum(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildEnumTypeSpec(type, builder))

    /** Add anonymous type to this spec builder. */
    fun addAnonymous(format: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildAnonymousTypeSpec(format, *args, builder = builder))

    /** Add anonymous type to this spec builder. */
    fun addAnonymous(block: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildAnonymousTypeSpec(block, builder))

    /** Add annotation type to this spec builder. */
    fun addAnnotation(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildAnnotationTypeSpec(name, builder))

    /** Add annotation type to this spec builder. */
    fun addAnnotation(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(buildAnnotationTypeSpec(type, builder))
}