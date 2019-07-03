package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.TypeSpecBuilder
import com.hendraanggrian.javapoet.invoke
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeSpec

/** A [TypeContainer] is responsible for managing a set of type instances. */
abstract class TypeContainer internal constructor() {

    abstract fun add(spec: TypeSpec): TypeSpec

    fun addClass(type: String): TypeSpec =
        add(TypeSpec.classBuilder(type).build())

    inline fun addClass(type: String, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.classBuilder(type)(builder))

    fun addClass(type: ClassName): TypeSpec =
        add(TypeSpec.classBuilder(type).build())

    inline fun addClass(type: ClassName, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.classBuilder(type)(builder))

    fun addInterface(type: String): TypeSpec =
        add(TypeSpec.interfaceBuilder(type).build())

    inline fun addInterface(type: String, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.interfaceBuilder(type)(builder))

    fun addInterface(type: ClassName): TypeSpec =
        add(TypeSpec.interfaceBuilder(type).build())

    inline fun addInterface(type: ClassName, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.interfaceBuilder(type)(builder))

    fun addEnum(type: String): TypeSpec =
        add(TypeSpec.enumBuilder(type).build())

    inline fun addEnum(type: String, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.enumBuilder(type)(builder))

    fun addEnum(type: ClassName): TypeSpec =
        add(TypeSpec.enumBuilder(type).build())

    inline fun addEnum(type: ClassName, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.enumBuilder(type)(builder))

    fun addAnonymous(format: String, vararg args: Any): TypeSpec =
        add(TypeSpec.anonymousClassBuilder(format, *args).build())

    inline fun addAnonymous(format: String, vararg args: Any, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.anonymousClassBuilder(format, *args)(builder))

    fun addAnonymous(block: CodeBlock): TypeSpec =
        add(TypeSpec.anonymousClassBuilder(block).build())

    inline fun addAnonymous(block: CodeBlock, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.anonymousClassBuilder(block)(builder))

    fun addAnnotation(type: String): TypeSpec =
        add(TypeSpec.annotationBuilder(type).build())

    inline fun addAnnotation(type: String, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.annotationBuilder(type)(builder))

    fun addAnnotation(type: ClassName): TypeSpec =
        add(TypeSpec.annotationBuilder(type).build())

    inline fun addAnnotation(type: ClassName, builder: TypeSpecBuilder.() -> Unit): TypeSpec =
        add(TypeSpec.annotationBuilder(type)(builder))

    inline operator fun TypeContainer.invoke(configuration: TypeContainerScope.() -> Unit) =
        TypeContainerScope(this).configuration()
}

/**
 * Receiver for the `types` block providing an extended set of operators for the configuration.
 */
class TypeContainerScope @PublishedApi internal constructor(private val container: TypeContainer) :
    TypeContainer() {

    override fun add(spec: TypeSpec): TypeSpec = container.add(spec)
}