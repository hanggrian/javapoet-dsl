@file:JvmMultifileClass
@file:JvmName("SpecBuildersKt")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationContainer
import com.hendraanggrian.javapoet.dsl.FieldContainer
import com.hendraanggrian.javapoet.dsl.JavadocContainer
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

fun String.toClassTypeSpec(): TypeSpec =
    TypeSpec.classBuilder(this).build()

inline fun buildClassTypeSpec(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(type)).apply(builderAction).build()

fun ClassName.toClassTypeSpec(): TypeSpec =
    TypeSpec.classBuilder(this).build()

inline fun buildClassTypeSpec(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(type)).apply(builderAction).build()

fun String.toInterfaceTypeSpec(): TypeSpec =
    TypeSpec.interfaceBuilder(this).build()

inline fun buildInterfaceTypeSpec(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(type)).apply(builderAction).build()

fun ClassName.toInterfaceTypeSpec(): TypeSpec =
    TypeSpec.interfaceBuilder(this).build()

inline fun buildInterfaceTypeSpec(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(type)).apply(builderAction).build()

fun String.toEnumTypeSpec(): TypeSpec =
    TypeSpec.enumBuilder(this).build()

inline fun buildEnumTypeSpec(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(type)).apply(builderAction).build()

fun ClassName.toEnumTypeSpec(): TypeSpec =
    TypeSpec.enumBuilder(this).build()

inline fun buildEnumTypeSpec(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(type)).apply(builderAction).build()

fun String.toAnonymousTypeSpec(vararg args: Any): TypeSpec =
    format(this, args) { s, array -> TypeSpec.anonymousClassBuilder(s, *array).build() }

inline fun buildAnonymousTypeSpec(
    format: String,
    vararg args: Any,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec =
    format(format, args) { s, array ->
        TypeSpecBuilder(TypeSpec.anonymousClassBuilder(s, *array)).apply(builderAction).build()
    }

fun CodeBlock.toAnonymousTypeSpec(): TypeSpec =
    TypeSpec.anonymousClassBuilder(this).build()

inline fun buildAnonymousTypeSpec(block: CodeBlock, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.anonymousClassBuilder(block)).apply(builderAction).build()

fun String.toAnnotationTypeSpec(): TypeSpec =
    TypeSpec.annotationBuilder(this).build()

inline fun buildAnnotationTypeSpec(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.annotationBuilder(type)).apply(builderAction).build()

fun ClassName.toAnnotationTypeSpec(): TypeSpec =
    TypeSpec.annotationBuilder(this).build()

inline fun buildAnnotationTypeSpec(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.annotationBuilder(type)).apply(builderAction).build()

/** Wrapper of [TypeSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDslMarker
class TypeSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: TypeSpec.Builder) {

    val javadoc: JavadocContainer = object : JavadocContainer() {
        override fun append(format: String, vararg args: Any) {
            format(format, args) { s, array -> nativeBuilder.addJavadoc(s, *array) }
        }

        override fun append(block: CodeBlock): CodeBlock =
            block.also { nativeBuilder.addJavadoc(it) }
    }

    val annotations: AnnotationContainer = object : AnnotationContainer() {
        override fun add(spec: AnnotationSpec): AnnotationSpec =
            spec.also { nativeBuilder.addAnnotation(it) }
    }

    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    fun addTypeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    fun addTypeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    var superClass: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.superclass(value)
        }

    fun superClass(type: KClass<*>) {
        nativeBuilder.superclass(type.java)
    }

    inline fun <reified T> superClass() =
        superClass(T::class)

    fun addSuperInterface(type: TypeName) {
        nativeBuilder.addSuperinterface(type)
    }

    fun addSuperInterface(type: KClass<*>) {
        nativeBuilder.addSuperinterface(type.java)
    }

    inline fun <reified T> addSuperInterface() =
        addSuperInterface(T::class)

    fun addEnumConstant(name: String) {
        nativeBuilder.addEnumConstant(name)
    }

    fun addEnumConstant(name: String, spec: TypeSpec) {
        nativeBuilder.addEnumConstant(name, spec)
    }

    val fields: FieldContainer = object : FieldContainer() {
        override fun add(spec: FieldSpec): FieldSpec =
            spec.also { nativeBuilder.addField(it) }
    }

    fun addStaticBlock(block: CodeBlock): CodeBlock =
        block.also { nativeBuilder.addStaticBlock(it) }

    inline fun addStaticBlock(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        addStaticBlock(buildCodeBlock(builderAction))

    fun addInitializerBlock(block: CodeBlock): CodeBlock =
        block.also { nativeBuilder.addInitializerBlock(it) }

    inline fun addInitializerBlock(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        addInitializerBlock(buildCodeBlock(builderAction))

    val methods: MethodContainer = object : MethodContainer() {
        override fun add(spec: MethodSpec): MethodSpec =
            spec.also { nativeBuilder.addMethod(it) }
    }

    val types: TypeContainer = object : TypeContainer() {
        override fun add(spec: TypeSpec): TypeSpec =
            spec.also { nativeBuilder.addType(it) }
    }

    fun addOriginatingElement(originatingElement: Element) {
        nativeBuilder.addOriginatingElement(originatingElement)
    }

    fun build(): TypeSpec =
        nativeBuilder.build()
}
