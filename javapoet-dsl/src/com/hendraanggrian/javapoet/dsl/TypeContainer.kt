package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.JavapoetDslMarker
import com.hendraanggrian.javapoet.TypeSpecBuilder
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeSpec

abstract class TypeContainer internal constructor() : TypeContainerDelegate {

    inline operator fun invoke(configuration: TypeContainerScope.() -> Unit) =
        configuration(TypeContainerScope(this))
}

@JavapoetDslMarker
@Suppress("NOTHING_TO_INLINE")
class TypeContainerScope @PublishedApi internal constructor(private val container: TypeContainer) :
    TypeContainerDelegate by container {

    inline operator fun String.invoke(noinline builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        addClass(this, builder)

    inline operator fun ClassName.invoke(noinline builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        addClass(this, builder)
}

interface TypeContainerDelegate {

    /** Add type to this spec builder. */
    fun add(spec: TypeSpec): TypeSpec

    /** Add class type to this spec builder. */
    fun addClass(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofClass(name, builder))

    /** Add class type to this spec builder. */
    fun addClass(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofClass(type, builder))

    /** Add interface type to this spec builder. */
    fun addInterface(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofInterface(name, builder))

    /** Add interface type to this spec builder. */
    fun addInterface(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofInterface(type, builder))

    /** Add enum type to this spec builder. */
    fun addEnum(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofEnum(name, builder))

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
    fun addAnnotation(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofAnnotation(name, builder))

    /** Add annotation type to this spec builder. */
    fun addAnnotation(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
        add(TypeSpecBuilder.ofAnnotation(type, builder))

    operator fun plusAssign(spec: TypeSpec) {
        add(spec)
    }
}