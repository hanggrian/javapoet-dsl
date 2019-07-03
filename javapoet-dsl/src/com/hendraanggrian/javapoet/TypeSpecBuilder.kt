@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationContainer
import com.hendraanggrian.javapoet.dsl.CodeContainer
import com.hendraanggrian.javapoet.dsl.FieldContainer
import com.hendraanggrian.javapoet.dsl.MethodContainer
import com.hendraanggrian.javapoet.dsl.TypeContainer
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeVariableName
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

inline fun buildClassTypeSpec(type: String, noinline builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(type))
        .also { builder?.invoke(it) }
        .build()

inline fun buildClassTypeSpec(type: ClassName, noinline builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(type))
        .also { builder?.invoke(it) }
        .build()

inline fun buildInterfaceTypeSpec(type: String, noinline builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(type))
        .also { builder?.invoke(it) }
        .build()

inline fun buildInterfaceTypeSpec(type: ClassName, noinline builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(type))
        .also { builder?.invoke(it) }
        .build()

inline fun buildEnumTypeSpec(type: String, noinline builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(type))
        .also { builder?.invoke(it) }
        .build()

inline fun buildEnumTypeSpec(type: ClassName, noinline builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(type))
        .also { builder?.invoke(it) }
        .build()

inline fun buildAnonymousTypeSpec(
    format: String,
    vararg args: Any,
    noinline builder: (TypeSpecBuilder.() -> Unit)? = null
): TypeSpec = TypeSpecBuilder(TypeSpec.anonymousClassBuilder(format, *args))
    .also { builder?.invoke(it) }
    .build()

inline fun buildAnonymousTypeSpec(block: CodeBlock, noinline builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.anonymousClassBuilder(block))
        .also { builder?.invoke(it) }
        .build()

inline fun buildAnnotationTypeSpec(type: String, noinline builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.annotationBuilder(type))
        .also { builder?.invoke(it) }
        .build()

inline fun buildAnnotationTypeSpec(type: ClassName, noinline builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.annotationBuilder(type))
        .also { builder?.invoke(it) }
        .build()

class TypeSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: TypeSpec.Builder) {

    val javadoc: CodeContainer = object : CodeContainer() {
        override fun add(format: String, vararg args: Any) {
            nativeBuilder.addJavadoc(format, *args)
        }

        override fun add(block: CodeBlock): CodeBlock = block.also { nativeBuilder.addJavadoc(it) }
    }

    val annotations: AnnotationContainer = object : AnnotationContainer() {
        override fun add(spec: AnnotationSpec): AnnotationSpec = spec.also { nativeBuilder.addAnnotation(it) }
    }

    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    fun addTypeVariable(name: TypeVariableName) {
        nativeBuilder.addTypeVariable(name)
    }

    fun addTypeVariables(names: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(names)
    }

    var superClass: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.superclass(value)
        }

    fun superClass(type: KClass<*>) {
        nativeBuilder.superclass(type.java)
    }

    inline fun <reified T> superClass() = superClass(T::class)

    fun addSuperInterface(type: TypeName) {
        nativeBuilder.addSuperinterface(type)
    }

    fun addSuperInterface(type: KClass<*>) {
        nativeBuilder.addSuperinterface(type.java)
    }

    inline fun <reified T> addSuperInterface() = addSuperInterface(T::class)

    fun addEnumConstant(name: String) {
        nativeBuilder.addEnumConstant(name)
    }

    fun addEnumConstant(name: String, spec: TypeSpec) {
        nativeBuilder.addEnumConstant(name, spec)
    }

    inline fun addEnumConstant(name: String, specName: String, noinline builder: (TypeSpecBuilder.() -> Unit)? = null) =
        addEnumConstant(name, buildEnumTypeSpec(specName, builder))

    val fields: FieldContainer = object : FieldContainer() {
        override fun add(spec: FieldSpec): FieldSpec = spec.also { nativeBuilder.addField(it) }
    }

    fun addStaticBlock(block: CodeBlock) {
        nativeBuilder.addStaticBlock(block)
    }

    inline fun addStaticBlock(builder: CodeBlockBuilder.() -> Unit) = addStaticBlock(buildCodeBlock(builder))

    fun addInitializerBlock(block: CodeBlock) {
        nativeBuilder.addInitializerBlock(block)
    }

    inline fun addInitializerBlock(builder: CodeBlockBuilder.() -> Unit) = addInitializerBlock(buildCodeBlock(builder))

    val methods: MethodContainer = object : MethodContainer() {
        override fun add(spec: MethodSpec): MethodSpec = spec.also { nativeBuilder.addMethod(it) }
    }

    val types: TypeContainer = object : TypeContainer() {
        override fun add(spec: TypeSpec): TypeSpec = spec.also { nativeBuilder.addType(it) }
    }

    fun addOriginatingElement(originatingElement: Element) {
        nativeBuilder.addOriginatingElement(originatingElement)
    }

    fun build(): TypeSpec = nativeBuilder.build()
}