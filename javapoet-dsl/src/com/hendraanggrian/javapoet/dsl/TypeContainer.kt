package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.TypeSpecBuilder
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeSpec

abstract class TypeContainer internal constructor() : TypeContainerDelegate() {

    operator fun invoke(configuration: TypeContainerScope.() -> Unit) =
        TypeContainerScope(this).configuration()
}

@JavapoetDslMarker
@Suppress("NOTHING_TO_INLINE")
class TypeContainerScope @PublishedApi internal constructor(private val container: TypeContainer) :
    TypeContainerDelegate() {

    override fun add(spec: TypeSpec): TypeSpec = container.add(spec)

    operator fun String.invoke(builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        addClass(this, builder)

    operator fun ClassName.invoke(builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        addClass(this, builder)
}

/** This class is abstract instead of sealed because [com.hendraanggrian.javapoet.JavaFileBuilder] inherited it. */
abstract class TypeContainerDelegate internal constructor() {

    /** Add type to this spec builder. */
    abstract fun add(spec: TypeSpec): TypeSpec

    /** Add class type to this spec builder. */
    fun addClass(type: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofClass(type, builder))

    /** Add class type to this spec builder. */
    fun addClass(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofClass(type, builder))

    /** Add interface type to this spec builder. */
    fun addInterface(type: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofInterface(type, builder))

    /** Add interface type to this spec builder. */
    fun addInterface(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofInterface(type, builder))

    /** Add enum type to this spec builder. */
    fun addEnum(type: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofEnum(type, builder))

    /** Add enum type to this spec builder. */
    fun addEnum(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofEnum(type, builder))

    /** Add anonymous type to this spec builder. */
    fun addAnonymous(format: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofAnonymous(format, *args, builder = builder))

    /** Add anonymous type to this spec builder. */
    fun addAnonymous(block: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofAnonymous(block, builder))

    /** Add annotation type to this spec builder. */
    fun addAnnotation(type: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofAnnotation(type, builder))

    /** Add annotation type to this spec builder. */
    fun addAnnotation(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofAnnotation(type, builder))

    operator fun plusAssign(spec: TypeSpec) {
        add(spec)
    }
}