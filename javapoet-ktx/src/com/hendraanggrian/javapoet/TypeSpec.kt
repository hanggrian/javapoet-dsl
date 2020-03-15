package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationSpecContainer
import com.hendraanggrian.javapoet.dsl.AnnotationSpecContainerScope
import com.hendraanggrian.javapoet.dsl.FieldSpecContainer
import com.hendraanggrian.javapoet.dsl.FieldSpecContainerScope
import com.hendraanggrian.javapoet.dsl.JavadocContainer
import com.hendraanggrian.javapoet.dsl.JavadocContainerScope
import com.hendraanggrian.javapoet.dsl.MethodSpecContainer
import com.hendraanggrian.javapoet.dsl.MethodSpecContainerScope
import com.hendraanggrian.javapoet.dsl.TypeSpecContainer
import com.hendraanggrian.javapoet.dsl.TypeSpecContainerScope
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeVariableName
import java.lang.reflect.Type
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

/** Builds a new class [TypeSpec] from [type]. */
fun classTypeSpecOf(type: String): TypeSpec = TypeSpec.classBuilder(type).build()

/**
 * Builds a new class [TypeSpec] from [type],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildClassTypeSpec(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpec.classBuilder(type).build(builderAction)

/** Builds a new class [TypeSpec] from [type]. */
fun classTypeSpecOf(type: ClassName): TypeSpec = TypeSpec.classBuilder(type).build()

/**
 * Builds a new class [TypeSpec] from [type],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildClassTypeSpec(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpec.classBuilder(type).build(builderAction)

/** Builds a new interface [TypeSpec] from [type]. */
fun interfaceTypeSpecOf(type: String): TypeSpec = TypeSpec.interfaceBuilder(type).build()

/**
 * Builds a new interface [TypeSpec] from [type],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildInterfaceTypeSpec(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpec.interfaceBuilder(type).build(builderAction)

/** Builds a new interface [TypeSpec] from [type]. */
fun interfaceTypeSpecOf(type: ClassName): TypeSpec = TypeSpec.interfaceBuilder(type).build()

/**
 * Builds a new interface [TypeSpec] from [type],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildInterfaceTypeSpec(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpec.interfaceBuilder(type).build(builderAction)

/** Builds a new enum [TypeSpec] from [type]. */
fun enumTypeSpecOf(type: String): TypeSpec = TypeSpec.enumBuilder(type).build()

/**
 * Builds a new enum [TypeSpec] from [type],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildEnumTypeSpec(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpec.enumBuilder(type).build(builderAction)

/** Builds a new enum [TypeSpec] from [type]. */
fun enumTypeSpecOf(type: ClassName): TypeSpec = TypeSpec.enumBuilder(type).build()

/**
 * Builds a new enum [TypeSpec] from [type],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildEnumTypeSpec(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpec.enumBuilder(type).build(builderAction)

/** Builds a new anonymous [TypeSpec] from [format] using formatted [args]. */
fun anonymousTypeSpecOf(format: String, vararg args: Any): TypeSpec =
    format.formatWith(args) { s, array -> TypeSpec.anonymousClassBuilder(s, *array).build() }

/**
 * Builds a new anonymous [TypeSpec] from [format] using formatted [args],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 * Not inlining this function since [formatWith] is not inlined.
 */
fun buildAnonymousTypeSpec(format: String, vararg args: Any, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    format.formatWith(args) { s, array -> TypeSpec.anonymousClassBuilder(s, *array).build(builderAction) }

/** Builds a new anonymous [TypeSpec] from [code]. */
fun anonymousTypeSpecOf(code: CodeBlock): TypeSpec = TypeSpec.anonymousClassBuilder(code).build()

/**
 * Builds a new anonymous [TypeSpec] from [code],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnonymousTypeSpec(code: CodeBlock, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpec.anonymousClassBuilder(code).build(builderAction)

/** Builds a new annotation [TypeSpec] from [type]. */
fun annotationTypeSpecOf(type: String): TypeSpec = TypeSpec.annotationBuilder(type).build()

/**
 * Builds a new annotation [TypeSpec] from [type],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnnotationTypeSpec(type: String, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpec.annotationBuilder(type).build(builderAction)

/** Builds a new annotation [TypeSpec] from [type]. */
fun annotationTypeSpecOf(type: ClassName): TypeSpec = TypeSpec.annotationBuilder(type).build()

/**
 * Builds a new annotation [TypeSpec] from [type],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildAnnotationTypeSpec(type: ClassName, builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpec.annotationBuilder(type).build(builderAction)

/** Modify existing [TypeSpec.Builder] using provided [builderAction] and then building it. */
inline fun TypeSpec.Builder.build(builderAction: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(this).apply(builderAction).build()

/** Wrapper of [TypeSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDslMarker
class TypeSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: TypeSpec.Builder) {

    /** Configure javadoc without DSL. */
    val javadoc: JavadocContainer = object : JavadocContainer() {
        override fun append(format: String, vararg args: Any): Unit =
            format.formatWith(args) { s, array -> nativeBuilder.addJavadoc(s, *array) }

        override fun append(code: CodeBlock) {
            nativeBuilder.addJavadoc(code)
        }
    }

    /** Configure javadoc with DSL. */
    inline fun javadoc(configuration: JavadocContainerScope.() -> Unit) =
        JavadocContainerScope(javadoc).configuration()

    /** Configure annotations without DSL. */
    val annotations: AnnotationSpecContainer = object : AnnotationSpecContainer() {
        override fun add(spec: AnnotationSpec) {
            nativeBuilder.addAnnotation(spec)
        }
    }

    /** Configure annotations with DSL. */
    inline fun annotations(configuration: AnnotationSpecContainerScope.() -> Unit) =
        AnnotationSpecContainerScope(annotations).configuration()

    /** Add type modifiers. */
    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Add type variables. */
    fun addTypeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    /** Add type variables. */
    fun addTypeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    /** Set superclass to type. */
    var superClass: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.superclass(value)
        }

    /** Set superclass to [type]. */
    fun superClass(type: Type) {
        nativeBuilder.superclass(type)
    }

    /** Set superclass to [type]. */
    fun superClass(type: KClass<*>) = superClass(type.java)

    /** Set superclass to [T]. */
    inline fun <reified T> superClass() = superClass(T::class)

    /** Add superinterface to [type]. */
    fun addSuperInterface(type: TypeName) {
        nativeBuilder.addSuperinterface(type)
    }

    /** Add superinterface to [type]. */
    fun addSuperInterface(type: Type) {
        nativeBuilder.addSuperinterface(type)
    }

    /** Add superinterface to [type]. */
    fun addSuperInterface(type: KClass<*>) = addSuperInterface(type.java)

    /** Add superinterface to [T]. */
    inline fun <reified T> addSuperInterface() = addSuperInterface(T::class)

    /** Add enum constant named [name]. */
    fun addEnumConstant(name: String) {
        nativeBuilder.addEnumConstant(name)
    }

    /** Add enum constant named [name] with specified [TypeSpec]. */
    fun addEnumConstant(name: String, spec: TypeSpec) {
        nativeBuilder.addEnumConstant(name, spec)
    }

    /** Configure fields without DSL. */
    val fields: FieldSpecContainer = object : FieldSpecContainer() {
        override fun add(spec: FieldSpec) {
            nativeBuilder.addField(spec)
        }
    }

    /** Configure fields with DSL. */
    inline fun fields(configuration: FieldSpecContainerScope.() -> Unit) =
        FieldSpecContainerScope(fields).configuration()

    /** Add static block containing [code]. */
    fun addStaticBlock(code: CodeBlock): CodeBlock = code.also { nativeBuilder.addStaticBlock(it) }

    /** Add static block containing code with custom initialization [builderAction]. */
    inline fun addStaticBlock(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        addStaticBlock(buildCodeBlock(builderAction))

    /** Add initializer block containing [code]. */
    fun addInitializerBlock(code: CodeBlock): CodeBlock = code.also { nativeBuilder.addInitializerBlock(it) }

    /** Add initializer block containing code with custom initialization [builderAction]. */
    inline fun addInitializerBlock(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        addInitializerBlock(buildCodeBlock(builderAction))

    /** Configure methods without DSL. */
    val methods: MethodSpecContainer = object : MethodSpecContainer() {
        override fun add(spec: MethodSpec) {
            nativeBuilder.addMethod(spec)
        }
    }

    /** Configure methods with DSL. */
    inline fun methods(configuration: MethodSpecContainerScope.() -> Unit) =
        MethodSpecContainerScope(methods).configuration()

    /** Configure types without DSL. */
    val types: TypeSpecContainer = object : TypeSpecContainer() {
        override fun add(spec: TypeSpec) {
            nativeBuilder.addType(spec)
        }
    }

    /** Configure types with DSL. */
    inline fun types(configuration: TypeSpecContainerScope.() -> Unit) =
        TypeSpecContainerScope(types).configuration()

    /** Add originating element. */
    fun addOriginatingElement(originatingElement: Element) {
        nativeBuilder.addOriginatingElement(originatingElement)
    }

    /** Returns native spec. */
    fun build(): TypeSpec = nativeBuilder.build()
}
