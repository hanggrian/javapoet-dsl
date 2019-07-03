package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.TypeSpecBuilder
import com.hendraanggrian.javapoet.invoke
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeSpec

/** A [TypeContainer] is responsible for managing a set of type instances. */
abstract class TypeContainer internal constructor() {

    /** Add type to this container, returning the type added. */
    abstract fun add(spec: TypeSpec): TypeSpec

    /** Add class type from [type], returning the type added. */
    fun addClass(type: String): TypeSpec =
        add(TypeSpec.classBuilder(type).build())

    /** Add class type from [type] with custom initialization [builder], returning the type added. */
    inline fun addClass(type: String, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.classBuilder(type)(builder))

    /** Add class type from [type], returning the type added. */
    fun addClass(type: ClassName): TypeSpec =
        add(TypeSpec.classBuilder(type).build())

    /** Add class type from [type] with custom initialization [builder], returning the type added. */
    inline fun addClass(type: ClassName, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.classBuilder(type)(builder))

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: String): TypeSpec =
        add(TypeSpec.interfaceBuilder(type).build())

    /** Add interface type from [type] with custom initialization [builder], returning the type added. */
    inline fun addInterface(type: String, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.interfaceBuilder(type)(builder))

    /** Add interface type from [type], returning the type added. */
    fun addInterface(type: ClassName): TypeSpec =
        add(TypeSpec.interfaceBuilder(type).build())

    /** Add interface type from [type] with custom initialization [builder], returning the type added. */
    inline fun addInterface(type: ClassName, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.interfaceBuilder(type)(builder))

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: String): TypeSpec =
        add(TypeSpec.enumBuilder(type).build())

    /** Add enum type from [type] with custom initialization [builder], returning the type added. */
    inline fun addEnum(type: String, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.enumBuilder(type)(builder))

    /** Add enum type from [type], returning the type added. */
    fun addEnum(type: ClassName): TypeSpec =
        add(TypeSpec.enumBuilder(type).build())

    /** Add enum type from [type] with custom initialization [builder], returning the type added. */
    inline fun addEnum(type: ClassName, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.enumBuilder(type)(builder))

    /** Add anonymous type from block, returning the type added. */
    fun addAnonymous(format: String, vararg args: Any): TypeSpec =
        add(TypeSpec.anonymousClassBuilder(format, *args).build())

    /** Add anonymous type from block with custom initialization [builder], returning the type added. */
    inline fun addAnonymous(format: String, vararg args: Any, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.anonymousClassBuilder(format, *args)(builder))

    /** Add anonymous type from [block], returning the type added. */
    fun addAnonymous(block: CodeBlock): TypeSpec =
        add(TypeSpec.anonymousClassBuilder(block).build())

    /** Add anonymous type from [block] with custom initialization [builder], returning the type added. */
    inline fun addAnonymous(block: CodeBlock, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.anonymousClassBuilder(block)(builder))

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: String): TypeSpec =
        add(TypeSpec.annotationBuilder(type).build())

    /** Add annotation type from [type] with custom initialization [builder], returning the type added. */
    inline fun addAnnotation(type: String, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.annotationBuilder(type)(builder))

    /** Add annotation type from [type], returning the type added. */
    fun addAnnotation(type: ClassName): TypeSpec =
        add(TypeSpec.annotationBuilder(type).build())

    /** Add annotation type from [type] with custom initialization [builder], returning the type added. */
    inline fun addAnnotation(type: ClassName, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.annotationBuilder(type)(builder))

    /** Configure this container with DSL. */
    inline operator fun TypeContainer.invoke(configuration: TypeContainerScope.() -> Unit) =
        TypeContainerScope(this).configuration()
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
class TypeContainerScope @PublishedApi internal constructor(private val container: TypeContainer) :
    TypeContainer() {

    override fun add(spec: TypeSpec): TypeSpec = container.add(spec)
}