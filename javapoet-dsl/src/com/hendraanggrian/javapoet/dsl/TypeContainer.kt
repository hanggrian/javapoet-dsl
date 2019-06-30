@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.TypeSpecBuilder
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeSpec

abstract class TypeContainer internal constructor() : TypeContainerDelegate() {

    /** Open DSL to configure this container. */
    inline operator fun invoke(configuration: TypeContainerScope.() -> Unit) =
        TypeContainerScope(this).configuration()
}

@JavapoetDslMarker
class TypeContainerScope @PublishedApi internal constructor(private val container: TypeContainer) :
    TypeContainerDelegate() {

    override fun add(spec: TypeSpec): TypeSpec = container.add(spec)
}

/** This class is abstract instead of sealed because [com.hendraanggrian.javapoet.JavaFileBuilder] inherited it. */
abstract class TypeContainerDelegate internal constructor() {

    /** Add spec to this container, returning the spec added. */
    abstract fun add(spec: TypeSpec): TypeSpec

    fun addClass(type: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofClass(type, builder))

    fun addClass(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofClass(type, builder))

    fun addInterface(type: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofInterface(type, builder))

    fun addInterface(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofInterface(type, builder))

    fun addEnum(type: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofEnum(type, builder))

    fun addEnum(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofEnum(type, builder))

    fun addAnonymous(format: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofAnonymous(format, *args, builder = builder))

    fun addAnonymous(block: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofAnonymous(block, builder))

    fun addAnnotation(type: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofAnnotation(type, builder))

    fun addAnnotation(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofAnnotation(type, builder))

    inline operator fun plusAssign(spec: TypeSpec) {
        add(spec)
    }
}