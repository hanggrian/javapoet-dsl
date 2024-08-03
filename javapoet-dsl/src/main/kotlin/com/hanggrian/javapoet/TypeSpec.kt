@file:OptIn(ExperimentalContracts::class)

package com.hanggrian.javapoet

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
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

// Deliberately skipping enum type without configuration because enum type requires at least one
// item.

/** Creates new class [TypeSpec] using parameters. */
public inline fun classTypeSpecOf(name: String): TypeSpec =
    TypeSpec
        .classBuilder(name)
        .build()

/** Creates new class [TypeSpec] using parameters. */
public inline fun classTypeSpecOf(name: ClassName): TypeSpec =
    TypeSpec
        .classBuilder(name)
        .build()

/** Creates new class [TypeSpec] using parameters. */
public inline fun interfaceTypeSpecOf(name: String): TypeSpec =
    TypeSpec
        .interfaceBuilder(name)
        .build()

/** Creates new class [TypeSpec] using parameters. */
public inline fun interfaceTypeSpecOf(name: ClassName): TypeSpec =
    TypeSpec
        .interfaceBuilder(name)
        .build()

/** Creates new class [TypeSpec] using parameters. */
public fun anonymousTypeSpecOf(format: String, vararg args: Any): TypeSpec =
    format.internalFormat(args) { format2, args2 ->
        TypeSpec
            .anonymousClassBuilder(format2, *args2)
            .build()
    }

/** Creates new class [TypeSpec] using parameters. */
public fun anonymousTypeSpecOf(code: CodeBlock): TypeSpec =
    TypeSpec
        .anonymousClassBuilder(code)
        .build()

/** Creates new class [TypeSpec] using parameters. */
public inline fun annotationTypeSpecOf(name: String): TypeSpec =
    TypeSpec
        .annotationBuilder(name)
        .build()

/** Creates new class [TypeSpec] using parameters. */
public inline fun annotationTypeSpecOf(name: ClassName): TypeSpec =
    TypeSpec
        .annotationBuilder(name)
        .build()

/**
 * Builds new class [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildClassTypeSpec(name: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new class [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildClassTypeSpec(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new interface [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildInterfaceTypeSpec(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.interfaceBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new interface [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildInterfaceTypeSpec(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.interfaceBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new enum [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildEnumTypeSpec(name: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new enum [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildEnumTypeSpec(name: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new anonymous [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildAnonymousTypeSpec(
    format: String,
    vararg args: Any,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return format.internalFormat(args) { format2, args2 ->
        TypeSpecBuilder(TypeSpec.anonymousClassBuilder(format2, *args2))
            .apply(configuration)
            .build()
    }
}

/**
 * Builds new anonymous [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildAnonymousTypeSpec(
    code: CodeBlock,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.anonymousClassBuilder(code))
        .apply(configuration)
        .build()
}

/**
 * Builds new annotation [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildAnnotationTypeSpec(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.annotationBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new annotation [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun buildAnnotationTypeSpec(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.annotationBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Inserts new class [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.classType(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(name))
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Inserts new class [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.classType(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(name))
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Inserts new interface [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.interfaceType(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.interfaceBuilder(name))
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Inserts new interface [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.interfaceType(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.interfaceBuilder(name))
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Inserts new enum [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.enumType(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Inserts new enum [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.enumType(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .apply(configuration)
        .build()
        .also(::type)
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
    return format.internalFormat(args) { format2, args2 ->
        TypeSpecBuilder(TypeSpec.anonymousClassBuilder(format2, *args2))
            .apply(configuration)
            .build()
            .also(::type)
    }
}

/**
 * Inserts new anonymous [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.anonymousType(
    code: CodeBlock,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.anonymousClassBuilder(code))
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Inserts new annotation [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.annotationType(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.annotationBuilder(name))
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Inserts new annotation [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public fun TypeSpecHandler.annotationType(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.annotationBuilder(name))
        .apply(configuration)
        .build()
        .also(::type)
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created [TypeSpecBuilder]
 * using provided [configuration].
 */
public fun TypeSpecHandler.classTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.classBuilder(it))
            .apply(configuration)
            .build()
            .also(::type)
    }
}

/**
 * Property delegate for inserting new interface [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.interfaceTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.interfaceBuilder(it))
            .apply(configuration)
            .build()
            .also(::type)
    }
}

/**
 * Property delegate for inserting new interface [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.enumTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.enumBuilder(it))
            .apply(configuration)
            .build()
            .also(::type)
    }
}

/**
 * Property delegate for inserting new interface [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.anonymousTyping(
    vararg args: Any,
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        it.internalFormat(args) { format2, args2 ->
            TypeSpecBuilder(TypeSpec.anonymousClassBuilder(format2, args2))
                .apply(configuration)
                .build()
                .also(::type)
        }
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
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.annotationBuilder(it))
            .apply(configuration)
            .build()
            .also(::type)
    }
}

/** Invokes DSL to configure [TypeSpec] collection. */
public fun TypeSpecHandler.types(configuration: TypeSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    TypeSpecHandlerScope
        .of(this)
        .configuration()
}

/** Responsible for managing a set of [TypeSpec] instances. */
public interface TypeSpecHandler {
    public fun type(type: TypeSpec)

    public fun classType(name: String): TypeSpec = classTypeSpecOf(name).also(::type)

    public fun classType(name: ClassName): TypeSpec = classTypeSpecOf(name).also(::type)

    public fun interfaceType(name: String): TypeSpec = interfaceTypeSpecOf(name).also(::type)

    public fun interfaceType(name: ClassName): TypeSpec = interfaceTypeSpecOf(name).also(::type)

    public fun anonymousType(format: String, vararg args: Any): TypeSpec =
        anonymousTypeSpecOf(format, *args).also(::type)

    public fun anonymousType(code: CodeBlock): TypeSpec = anonymousTypeSpecOf(code).also(::type)

    public fun annotationType(name: String): TypeSpec = annotationTypeSpecOf(name).also(::type)

    public fun annotationType(name: ClassName): TypeSpec = annotationTypeSpecOf(name).also(::type)

    public fun classTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { classTypeSpecOf(it).also(::type) }

    public fun interfaceTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { interfaceTypeSpecOf(it).also(::type) }

    public fun anonymousTyping(vararg args: Any): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { anonymousTypeSpecOf(it, *args).also(::type) }

    public fun annotationTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { annotationTypeSpecOf(it).also(::type) }
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@JavapoetDsl
public open class TypeSpecHandlerScope private constructor(handler: TypeSpecHandler) :
    TypeSpecHandler by handler {
        /**
         * @see classType
         */
        public operator fun String.invoke(configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
            classType(this, configuration)

        public companion object {
            public fun of(handler: TypeSpecHandler): TypeSpecHandlerScope =
                TypeSpecHandlerScope(handler)
        }
    }

/** Wrapper of [TypeSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDsl
public class TypeSpecBuilder(private val nativeBuilder: TypeSpec.Builder) :
    AnnotationSpecHandler,
    FieldSpecHandler,
    MethodSpecHandler,
    TypeSpecHandler {
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
