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

/** Converts string to class [TypeSpec]. */
fun String.toClassType(): TypeSpec =
    TypeSpec.classBuilder(this).build()

/**
 * Builds new [TypeSpec] by populating newly created [TypeSpecBuilder] using provided [builderAction]
 * and then building it.
 */
inline fun buildClassType(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(type)).apply(builderAction).build()

/** Converts class name to class [TypeSpec]. */
fun ClassName.toClassType(): TypeSpec =
    TypeSpec.classBuilder(this).build()

/**
 * Builds new [TypeSpec] by populating newly created [TypeSpecBuilder] using provided [builderAction]
 * and then building it.
 */
inline fun buildClassType(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(type)).apply(builderAction).build()

/** Converts string to interface [TypeSpec]. */
fun String.toInterfaceType(): TypeSpec =
    TypeSpec.interfaceBuilder(this).build()

/**
 * Builds new [TypeSpec] by populating newly created [TypeSpecBuilder] using provided [builderAction]
 * and then building it.
 */
inline fun buildInterfaceType(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(type)).apply(builderAction).build()

/** Converts class name to interface [TypeSpec]. */
fun ClassName.toInterfaceType(): TypeSpec =
    TypeSpec.interfaceBuilder(this).build()

/**
 * Builds new [TypeSpec] by populating newly created [TypeSpecBuilder] using provided [builderAction]
 * and then building it.
 */
inline fun buildInterfaceType(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(type)).apply(builderAction).build()

/** Converts string to enum [TypeSpec]. */
fun String.toEnumType(): TypeSpec =
    TypeSpec.enumBuilder(this).build()

/**
 * Builds new [TypeSpec] by populating newly created [TypeSpecBuilder] using provided [builderAction]
 * and then building it.
 */
inline fun buildEnumType(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(type)).apply(builderAction).build()

/** Converts class name to enum [TypeSpec]. */
fun ClassName.toEnumType(): TypeSpec =
    TypeSpec.enumBuilder(this).build()

/**
 * Builds new [TypeSpec] by populating newly created [TypeSpecBuilder] using provided [builderAction]
 * and then building it.
 */
inline fun buildEnumType(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(type)).apply(builderAction).build()

/** Converts string to anonymous [TypeSpec] using formatted [args]. */
fun String.toAnonymousType(vararg args: Any): TypeSpec =
    formatWith(args) { s, array -> TypeSpec.anonymousClassBuilder(s, *array).build() }

/**
 * Builds new [TypeSpec] by populating newly created [TypeSpecBuilder] using provided [builderAction]
 * and then building it.
 */
inline fun buildAnonymousType(
    format: String,
    vararg args: Any,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec =
    format.formatWith(args) { s, array ->
        TypeSpecBuilder(TypeSpec.anonymousClassBuilder(s, *array)).apply(builderAction).build()
    }

/** Converts code to anonymous [TypeSpec]. */
fun CodeBlock.toAnonymousType(): TypeSpec =
    TypeSpec.anonymousClassBuilder(this).build()

/**
 * Builds new [TypeSpec] by populating newly created [TypeSpecBuilder] using provided [builderAction]
 * and then building it.
 */
inline fun buildAnonymousType(block: CodeBlock, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.anonymousClassBuilder(block)).apply(builderAction).build()

/** Converts string to annotation [TypeSpec]. */
fun String.toAnnotationType(): TypeSpec =
    TypeSpec.annotationBuilder(this).build()

/**
 * Builds new [TypeSpec] by populating newly created [TypeSpecBuilder] using provided [builderAction]
 * and then building it.
 */
inline fun buildAnnotationType(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.annotationBuilder(type)).apply(builderAction).build()

/** Converts class name to annotation [TypeSpec]. */
fun ClassName.toAnnotationType(): TypeSpec =
    TypeSpec.annotationBuilder(this).build()

/**
 * Builds new [TypeSpec] by populating newly created [TypeSpecBuilder] using provided [builderAction]
 * and then building it.
 */
inline fun buildAnnotationType(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.annotationBuilder(type)).apply(builderAction).build()

/** Wrapper of [TypeSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDslMarker
class TypeSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: TypeSpec.Builder) {

    val javadoc: JavadocContainer = object : JavadocContainer() {
        override fun append(format: String, vararg args: Any): Unit =
            format.formatWith(args) { s, array -> nativeBuilder.addJavadoc(s, *array) }

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
        addStaticBlock(buildCode(builderAction))

    fun addInitializerBlock(block: CodeBlock): CodeBlock =
        block.also { nativeBuilder.addInitializerBlock(it) }

    inline fun addInitializerBlock(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        addInitializerBlock(buildCode(builderAction))

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
