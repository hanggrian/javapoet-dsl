@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeVariableName
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/**
 * Builds new class [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
inline fun buildClassTypeSpec(name: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(name)).apply(configuration).build()
}

/**
 * Builds new interface [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
inline fun buildInterfaceTypeSpec(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.interfaceBuilder(name)).apply(configuration).build()
}

/**
 * Builds new enum [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
inline fun buildEnumTypeSpec(name: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(name)).apply(configuration).build()
}

/**
 * Builds new anonymous [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration]. Not inlining this function since [internalFormat] is not inlined.
 */
fun buildAnonymousTypeSpec(
    format: String,
    vararg args: Any,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return format.internalFormat(args) { format2, args2 ->
        TypeSpecBuilder(TypeSpec.anonymousClassBuilder(format2, *args2)).apply(configuration)
            .build()
    }
}

/**
 * Builds new annotation [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
inline fun buildAnnotationTypeSpec(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.annotationBuilder(name)).apply(configuration).build()
}

/**
 * Inserts new class [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
inline fun TypeSpecHandler.classType(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildClassTypeSpec(name, configuration).also(::type)
}

/**
 * Inserts new interface [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
inline fun TypeSpecHandler.interfaceType(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildInterfaceTypeSpec(name, configuration).also(::type)
}

/**
 * Inserts new enum [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
inline fun TypeSpecHandler.enumType(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildEnumTypeSpec(name, configuration).also(::type)
}

/**
 * Inserts new anonymous [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
fun TypeSpecHandler.anonymousType(
    format: String,
    vararg args: Any,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildAnonymousTypeSpec(format, *args, configuration = configuration).also(::type)
}

/**
 * Inserts new annotation [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
inline fun TypeSpecHandler.annotationType(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildAnnotationTypeSpec(name, configuration).also(::type)
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created [TypeSpecBuilder]
 * using provided [configuration].
 */
fun TypeSpecHandler.classTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildClassTypeSpec(it, configuration).also(::type) }
}

/**
 * Property delegate for inserting new interface [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
fun TypeSpecHandler.interfaceTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildInterfaceTypeSpec(it, configuration).also(::type) }
}

/**
 * Property delegate for inserting new interface [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
fun TypeSpecHandler.enumTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildEnumTypeSpec(it, configuration).also(::type) }
}

/**
 * Property delegate for inserting new interface [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
fun TypeSpecHandler.anonymousTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        buildAnonymousTypeSpec(it, configuration = configuration).also(::type)
    }
}

/**
 * Property delegate for inserting new interface [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
fun TypeSpecHandler.annotationTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildAnnotationTypeSpec(it, configuration).also(::type) }
}

/** Invokes DSL to configure [TypeSpec] collection. */
fun TypeSpecHandler.types(configuration: TypeSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    TypeSpecHandlerScope(this).configuration()
}

/** Responsible for managing a set of [TypeSpec] instances. */
sealed interface TypeSpecHandler {
    fun type(type: TypeSpec)

    fun classType(name: String): TypeSpec = TypeSpec.classBuilder(name).build().also(::type)

    fun interfaceType(name: String): TypeSpec = TypeSpec.interfaceBuilder(name).build().also(::type)

    fun enumType(name: String): TypeSpec = TypeSpec.enumBuilder(name).build().also(::type)

    fun anonymousType(format: String, vararg args: Any): TypeSpec =
        format.internalFormat(args) { format2, args2 ->
            TypeSpecBuilder(TypeSpec.anonymousClassBuilder(format2, *args2))
                .build()
                .also(::type)
        }

    fun annotationType(name: String): TypeSpec =
        TypeSpec.annotationBuilder(name).build().also(::type)

    fun classTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { TypeSpec.classBuilder(it).build().also(::type) }

    fun interfaceTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { TypeSpec.interfaceBuilder(it).build().also(::type) }

    fun enumTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { TypeSpec.enumBuilder(it).build().also(::type) }

    fun anonymousTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { TypeSpec.anonymousClassBuilder(it).build().also(::type) }

    fun annotationTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { TypeSpec.annotationBuilder(it).build().also(::type) }
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@JavapoetDsl
class TypeSpecHandlerScope internal constructor(
    handler: TypeSpecHandler,
) : TypeSpecHandler by handler {
    /** @see classType */
    operator fun String.invoke(configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildClassTypeSpec(this, configuration).also(::type)
}

/** Wrapper of [TypeSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDsl
class TypeSpecBuilder(
    private val nativeBuilder: TypeSpec.Builder,
) : AnnotationSpecHandler, FieldSpecHandler, MethodSpecHandler, TypeSpecHandler {
    val annotations: MutableList<AnnotationSpec> get() = nativeBuilder.annotations
    val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers
    val typeVariables: MutableList<TypeVariableName> get() = nativeBuilder.typeVariables
    val superinterfaces: MutableList<TypeName> get() = nativeBuilder.superinterfaces
    val enumConstants: MutableMap<String, TypeSpec> get() = nativeBuilder.enumConstants
    val fields: MutableList<FieldSpec> get() = nativeBuilder.fieldSpecs
    val methods: MutableList<MethodSpec> get() = nativeBuilder.methodSpecs
    val types: MutableList<TypeSpec> get() = nativeBuilder.typeSpecs
    val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements
    val alwaysQualifiedNames: MutableSet<String> get() = nativeBuilder.alwaysQualifiedNames

    fun javadoc(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.addJavadoc(format2, *args2)
        }

    fun javadoc(block: CodeBlock) {
        nativeBuilder.addJavadoc(block)
    }

    override fun annotation(annotation: AnnotationSpec) {
        nativeBuilder.addAnnotation(annotation)
    }

    fun modifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    fun typeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    fun typeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    var superclass: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.superclass(value)
        }

    fun superclass(type: KClass<*>) {
        nativeBuilder.superclass(type.java)
    }

    inline fun <reified T> superclass(): Unit = superclass(T::class)

    fun superinterfaces(superinterfaces: Iterable<TypeName>) {
        nativeBuilder.addSuperinterfaces(superinterfaces)
    }

    fun superinterface(superinterface: TypeName) {
        nativeBuilder.addSuperinterface(superinterface)
    }

    fun superinterface(superinterface: KClass<*>) {
        nativeBuilder.addSuperinterface(superinterface.java)
    }

    inline fun <reified T> superinterface(): Unit = superinterface(T::class)

    fun superinterface(superinterface: TypeMirror, avoidNestedTypeNameClashes: Boolean = true) {
        nativeBuilder.addSuperinterface(superinterface, avoidNestedTypeNameClashes)
    }

    fun enumConstant(name: String, spec: TypeSpec = TypeSpec.anonymousClassBuilder("").build()) {
        nativeBuilder.addEnumConstant(name, spec)
    }

    override fun field(field: FieldSpec) {
        nativeBuilder.addField(field)
    }

    fun staticBlock(code: CodeBlock) {
        nativeBuilder.addStaticBlock(code)
    }

    fun staticBlock(format: String, vararg args: Any) {
        nativeBuilder.addStaticBlock(codeBlockOf(format, *args))
    }

    fun initializerBlock(code: CodeBlock) {
        nativeBuilder.addInitializerBlock(code)
    }

    fun initializerBlock(format: String, vararg args: Any) {
        nativeBuilder.addInitializerBlock(codeBlockOf(format, *args))
    }

    override fun method(method: MethodSpec) {
        nativeBuilder.addMethod(method)
    }

    override fun type(type: TypeSpec) {
        nativeBuilder.addType(type)
    }

    fun originatingElement(originatingElement: Element) {
        nativeBuilder.addOriginatingElement(originatingElement)
    }

    fun alwaysQualify(vararg simpleNames: String) {
        nativeBuilder.alwaysQualify(*simpleNames)
    }

    fun avoidClashesWithNestedClasses(typeElement: TypeElement) {
        nativeBuilder.avoidClashesWithNestedClasses(typeElement)
    }

    fun avoidClashesWithNestedClasses(type: KClass<*>) {
        nativeBuilder.avoidClashesWithNestedClasses(type.java)
    }

    inline fun <reified T> avoidClashesWithNestedClasses(): Unit =
        avoidClashesWithNestedClasses(T::class)

    fun build(): TypeSpec = nativeBuilder.build()
}
