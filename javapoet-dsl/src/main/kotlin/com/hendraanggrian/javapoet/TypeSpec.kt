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
public inline fun buildClassTypeSpec(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(name)).apply(configuration).build()
}

/**
 * Builds new interface [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildInterfaceTypeSpec(
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
public inline fun buildEnumTypeSpec(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(name)).apply(configuration).build()
}

/**
 * Builds new anonymous [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration]. Not inlining this function since [internalFormat] is not inlined.
 */
public fun buildAnonymousTypeSpec(
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
public inline fun buildAnnotationTypeSpec(
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
public inline fun TypeSpecHandler.classType(
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
public inline fun TypeSpecHandler.interfaceType(
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
public inline fun TypeSpecHandler.enumType(
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
public fun TypeSpecHandler.anonymousType(
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
public inline fun TypeSpecHandler.annotationType(
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
public fun TypeSpecHandler.classTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildClassTypeSpec(it, configuration).also(::type) }
}

/**
 * Property delegate for inserting new interface [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.interfaceTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildInterfaceTypeSpec(it, configuration).also(::type) }
}

/**
 * Property delegate for inserting new interface [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.enumTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildEnumTypeSpec(it, configuration).also(::type) }
}

/**
 * Property delegate for inserting new interface [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.anonymousTyping(
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
public fun TypeSpecHandler.annotationTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildAnnotationTypeSpec(it, configuration).also(::type) }
}

/** Invokes DSL to configure [TypeSpec] collection. */
public fun TypeSpecHandler.types(configuration: TypeSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    TypeSpecHandlerScope.of(this).configuration()
}

/** Responsible for managing a set of [TypeSpec] instances. */
public interface TypeSpecHandler {
    public fun type(type: TypeSpec)

    public fun classType(name: String): TypeSpec = TypeSpec.classBuilder(name).build().also(::type)

    public fun interfaceType(name: String): TypeSpec =
        TypeSpec.interfaceBuilder(name).build().also(::type)

    public fun enumType(name: String): TypeSpec = TypeSpec.enumBuilder(name).build().also(::type)

    public fun anonymousType(format: String, vararg args: Any): TypeSpec =
        format.internalFormat(args) { format2, args2 ->
            TypeSpecBuilder(TypeSpec.anonymousClassBuilder(format2, *args2))
                .build()
                .also(::type)
        }

    public fun annotationType(name: String): TypeSpec =
        TypeSpec.annotationBuilder(name).build().also(::type)

    public fun classTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { TypeSpec.classBuilder(it).build().also(::type) }

    public fun interfaceTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { TypeSpec.interfaceBuilder(it).build().also(::type) }

    public fun enumTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { TypeSpec.enumBuilder(it).build().also(::type) }

    public fun anonymousTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { TypeSpec.anonymousClassBuilder(it).build().also(::type) }

    public fun annotationTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { TypeSpec.annotationBuilder(it).build().also(::type) }
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@JavapoetDsl
public open class TypeSpecHandlerScope private constructor(
    handler: TypeSpecHandler,
) : TypeSpecHandler by handler {
    public companion object {
        public fun of(handler: TypeSpecHandler): TypeSpecHandlerScope =
            TypeSpecHandlerScope(handler)
    }

    /** @see classType */
    public operator fun String.invoke(configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildClassTypeSpec(this, configuration).also(::type)
}

/** Wrapper of [TypeSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDsl
public class TypeSpecBuilder(
    private val nativeBuilder: TypeSpec.Builder,
) : AnnotationSpecHandler, FieldSpecHandler, MethodSpecHandler, TypeSpecHandler {
    public val annotations: MutableList<AnnotationSpec> get() = nativeBuilder.annotations
    public val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers
    public val typeVariables: MutableList<TypeVariableName> get() = nativeBuilder.typeVariables
    public val superinterfaces: MutableList<TypeName> get() = nativeBuilder.superinterfaces
    public val enumConstants: MutableMap<String, TypeSpec> get() = nativeBuilder.enumConstants
    public val fields: MutableList<FieldSpec> get() = nativeBuilder.fieldSpecs
    public val methods: MutableList<MethodSpec> get() = nativeBuilder.methodSpecs
    public val types: MutableList<TypeSpec> get() = nativeBuilder.typeSpecs
    public val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements
    public val alwaysQualifiedNames: MutableSet<String> get() = nativeBuilder.alwaysQualifiedNames

    public fun javadoc(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.addJavadoc(format2, *args2)
        }

    public fun javadoc(block: CodeBlock) {
        nativeBuilder.addJavadoc(block)
    }

    public override fun annotation(annotation: AnnotationSpec) {
        nativeBuilder.addAnnotation(annotation)
    }

    public fun modifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    public fun typeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    public fun typeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    public var superclass: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.superclass(value)
        }

    public fun superclass(type: Class<*>) {
        nativeBuilder.superclass(type)
    }

    public fun superclass(type: KClass<*>) {
        nativeBuilder.superclass(type.java)
    }

    public inline fun <reified T> superclass(): Unit = superclass(T::class)

    public fun superinterfaces(superinterfaces: Iterable<TypeName>) {
        nativeBuilder.addSuperinterfaces(superinterfaces)
    }

    public fun superinterface(superinterface: TypeName) {
        nativeBuilder.addSuperinterface(superinterface)
    }

    public fun superinterface(superinterface: Class<*>) {
        nativeBuilder.addSuperinterface(superinterface)
    }

    public fun superinterface(superinterface: KClass<*>) {
        nativeBuilder.addSuperinterface(superinterface.java)
    }

    public inline fun <reified T> superinterface(): Unit = superinterface(T::class)

    public fun superinterface(
        superinterface: TypeMirror,
        avoidNestedTypeNameClashes: Boolean = true,
    ) {
        nativeBuilder.addSuperinterface(superinterface, avoidNestedTypeNameClashes)
    }

    public fun enumConstant(
        name: String,
        spec: TypeSpec = TypeSpec.anonymousClassBuilder("").build(),
    ) {
        nativeBuilder.addEnumConstant(name, spec)
    }

    public override fun field(field: FieldSpec) {
        nativeBuilder.addField(field)
    }

    public fun staticBlock(code: CodeBlock) {
        nativeBuilder.addStaticBlock(code)
    }

    public fun staticBlock(format: String, vararg args: Any) {
        nativeBuilder.addStaticBlock(codeBlockOf(format, *args))
    }

    public fun initializerBlock(code: CodeBlock) {
        nativeBuilder.addInitializerBlock(code)
    }

    public fun initializerBlock(format: String, vararg args: Any) {
        nativeBuilder.addInitializerBlock(codeBlockOf(format, *args))
    }

    public override fun method(method: MethodSpec) {
        nativeBuilder.addMethod(method)
    }

    public override fun type(type: TypeSpec) {
        nativeBuilder.addType(type)
    }

    public fun originatingElement(originatingElement: Element) {
        nativeBuilder.addOriginatingElement(originatingElement)
    }

    public fun alwaysQualify(vararg simpleNames: String) {
        nativeBuilder.alwaysQualify(*simpleNames)
    }

    public fun avoidClashesWithNestedClasses(typeElement: TypeElement) {
        nativeBuilder.avoidClashesWithNestedClasses(typeElement)
    }

    public fun avoidClashesWithNestedClasses(type: Class<*>) {
        nativeBuilder.avoidClashesWithNestedClasses(type)
    }

    public fun avoidClashesWithNestedClasses(type: KClass<*>) {
        nativeBuilder.avoidClashesWithNestedClasses(type.java)
    }

    public inline fun <reified T> avoidClashesWithNestedClasses(): Unit =
        avoidClashesWithNestedClasses(T::class)

    public fun build(): TypeSpec = nativeBuilder.build()
}
