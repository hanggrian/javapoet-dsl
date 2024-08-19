@file:OptIn(ExperimentalContracts::class)

package com.hanggrian.javapoet

import com.hanggrian.javapoet.internals.Internals
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
public inline fun anonymousTypeSpecOf(format: String, vararg args: Any): TypeSpec =
    Internals.format(format, args) { format2, args2 ->
        TypeSpec
            .anonymousClassBuilder(format2, *args2)
            .build()
    }

/** Creates new class [TypeSpec] using parameters. */
public inline fun anonymousTypeSpecOf(code: CodeBlock): TypeSpec =
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
public inline fun buildClassTypeSpec(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new class [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildClassTypeSpec(
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
public inline fun buildInterfaceTypeSpec(
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
public inline fun buildInterfaceTypeSpec(
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
public inline fun buildEnumTypeSpec(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new enum [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildEnumTypeSpec(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .apply(configuration)
        .build()
}

/**
 * Builds new anonymous [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildAnonymousTypeSpec(
    format: String,
    vararg args: Any,
    noinline configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return Internals.format(format, args) { format2, args2 ->
        TypeSpecBuilder(TypeSpec.anonymousClassBuilder(format2, *args2))
            .apply(configuration)
            .build()
    }
}

/**
 * Builds new anonymous [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun buildAnonymousTypeSpec(
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
public inline fun buildAnnotationTypeSpec(
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
public inline fun buildAnnotationTypeSpec(
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
public inline fun TypeSpecHandler.addClass(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new class [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addClass(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.classBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new interface [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addInterface(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.interfaceBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new interface [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addInterface(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.interfaceBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new enum [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addEnum(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new enum [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addEnum(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new anonymous [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addAnonymous(
    format: String,
    vararg args: Any,
    noinline configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return Internals.format(format, args) { format2, args2 ->
        TypeSpecBuilder(TypeSpec.anonymousClassBuilder(format2, *args2))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/**
 * Inserts new anonymous [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addAnonymous(
    code: CodeBlock,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.anonymousClassBuilder(code))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new annotation [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addAnnotation(
    name: String,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.annotationBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Inserts new annotation [TypeSpec] by populating newly created [TypeSpecBuilder] using provided
 * [configuration].
 */
public inline fun TypeSpecHandler.addAnnotation(
    name: ClassName,
    configuration: TypeSpecBuilder.() -> Unit,
): TypeSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return TypeSpecBuilder(TypeSpec.annotationBuilder(name))
        .apply(configuration)
        .build()
        .also(::add)
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created [TypeSpecBuilder]
 * using provided [configuration].
 */
public fun TypeSpecHandler.addingClass(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.classBuilder(it))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/**
 * Property delegate for inserting new interface [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.addingInterface(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.interfaceBuilder(it))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/**
 * Property delegate for inserting new interface [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.addingEnum(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.enumBuilder(it))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/**
 * Property delegate for inserting new interface [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.addingAnonymous(
    vararg args: Any,
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        Internals.format(it, args) { format2, args2 ->
            TypeSpecBuilder(TypeSpec.anonymousClassBuilder(format2, args2))
                .apply(configuration)
                .build()
                .also(::add)
        }
    }
}

/**
 * Property delegate for inserting new interface [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
public fun TypeSpecHandler.addingAnnotation(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider {
        TypeSpecBuilder(TypeSpec.annotationBuilder(it))
            .apply(configuration)
            .build()
            .also(::add)
    }
}

/** Responsible for managing a set of [TypeSpec] instances. */
public interface TypeSpecHandler {
    public fun add(type: TypeSpec)

    public fun addClass(name: String): TypeSpec = classTypeSpecOf(name).also(::add)

    public fun addClass(name: ClassName): TypeSpec = classTypeSpecOf(name).also(::add)

    public fun addInterface(name: String): TypeSpec = interfaceTypeSpecOf(name).also(::add)

    public fun addInterface(name: ClassName): TypeSpec = interfaceTypeSpecOf(name).also(::add)

    public fun addAnonymous(format: String, vararg args: Any): TypeSpec =
        anonymousTypeSpecOf(format, *args).also(::add)

    public fun addAnonymous(code: CodeBlock): TypeSpec = anonymousTypeSpecOf(code).also(::add)

    public fun addAnnotation(name: String): TypeSpec = annotationTypeSpecOf(name).also(::add)

    public fun addAnnotation(name: ClassName): TypeSpec = annotationTypeSpecOf(name).also(::add)

    public fun addingClass(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { classTypeSpecOf(it).also(::add) }

    public fun addingInterface(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { interfaceTypeSpecOf(it).also(::add) }

    public fun addingAnonymous(vararg args: Any): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { anonymousTypeSpecOf(it, *args).also(::add) }

    public fun addingAnnotation(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { annotationTypeSpecOf(it).also(::add) }
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@JavaPoetDsl
public open class TypeSpecHandlerScope private constructor(handler: TypeSpecHandler) :
    TypeSpecHandler by handler {
        public inline operator fun String.invoke(
            configuration: TypeSpecBuilder.() -> Unit,
        ): TypeSpec = addClass(this, configuration)

        public companion object {
            public fun of(handler: TypeSpecHandler): TypeSpecHandlerScope =
                TypeSpecHandlerScope(handler)
        }
    }

/** Wrapper of [TypeSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavaPoetDsl
public class TypeSpecBuilder(private val nativeBuilder: TypeSpec.Builder) {
    public val annotations: AnnotationSpecHandler =
        object : AnnotationSpecHandler {
            override fun add(annotation: AnnotationSpec) {
                annotationSpecs += annotation
            }
        }

    public val fields: FieldSpecHandler =
        object : FieldSpecHandler {
            override fun add(field: FieldSpec) {
                fieldSpecs += field
            }
        }

    public val methods: MethodSpecHandler =
        object : MethodSpecHandler {
            override fun add(method: MethodSpec) {
                methodSpecs += method
            }
        }

    public val types: TypeSpecHandler =
        object : TypeSpecHandler {
            override fun add(type: TypeSpec) {
                typeSpecs += type
            }
        }

    /** Invokes DSL to configure [AnnotationSpec] collection. */
    public inline fun annotations(configuration: AnnotationSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        AnnotationSpecHandlerScope
            .of(annotations)
            .configuration()
    }

    /** Invokes DSL to configure [FieldSpec] collection. */
    public inline fun fields(configuration: FieldSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        FieldSpecHandlerScope
            .of(fields)
            .configuration()
    }

    /** Invokes DSL to configure [MethodSpec] collection. */
    public inline fun methods(configuration: MethodSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        MethodSpecHandlerScope
            .of(methods)
            .configuration()
    }

    /** Invokes DSL to configure [TypeSpec] collection. */
    public inline fun types(configuration: TypeSpecHandlerScope.() -> Unit) {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        TypeSpecHandlerScope
            .of(types)
            .configuration()
    }

    public val annotationSpecs: MutableList<AnnotationSpec> get() = nativeBuilder.annotations
    public val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers
    public val typeVariables: MutableList<TypeVariableName> get() = nativeBuilder.typeVariables
    public val superinterfaces: MutableList<TypeName> get() = nativeBuilder.superinterfaces
    public val enumConstants: MutableMap<String, TypeSpec> get() = nativeBuilder.enumConstants
    public val fieldSpecs: MutableList<FieldSpec> get() = nativeBuilder.fieldSpecs
    public val methodSpecs: MutableList<MethodSpec> get() = nativeBuilder.methodSpecs
    public val typeSpecs: MutableList<TypeSpec> get() = nativeBuilder.typeSpecs
    public val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements
    public val alwaysQualifiedNames: MutableSet<String> get() = nativeBuilder.alwaysQualifiedNames

    public fun addJavadoc(format: String, vararg args: Any): Unit =
        Internals.format(format, args) { format2, args2 ->
            nativeBuilder.addJavadoc(format2, *args2)
        }

    public fun addJavadoc(block: CodeBlock) {
        nativeBuilder.addJavadoc(block)
    }

    public fun addModifiers(vararg modifiers: Modifier) {
        this.modifiers += modifiers
    }

    public fun addTypeVariables(vararg typeVariables: TypeVariableName) {
        this.typeVariables += typeVariables
    }

    public var superclass: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.superclass(value)
        }

    public fun setSuperclass(type: Class<*>) {
        superclass = type.name2
    }

    public fun setSuperclass(type: KClass<*>) {
        superclass = type.name
    }

    public inline fun <reified T> setSuperclass() {
        superclass = T::class.name
    }

    public fun addSuperinterfaces(vararg superinterfaces: TypeName) {
        this.superinterfaces += superinterfaces
    }

    public fun addSuperinterfaces(vararg superinterfaces: Class<*>) {
        this.superinterfaces += superinterfaces.map { it.name2 }
    }

    public fun addSuperinterfaces(vararg superinterfaces: KClass<*>) {
        this.superinterfaces += superinterfaces.map { it.name }
    }

    public inline fun <reified T> addSuperinterface() {
        superinterfaces += T::class.name
    }

    public fun addSuperinterface(
        superinterface: TypeMirror,
        avoidNestedTypeNameClashes: Boolean = true,
    ) {
        nativeBuilder.addSuperinterface(superinterface, avoidNestedTypeNameClashes)
    }

    public fun addEnumConstant(
        name: String,
        type: TypeSpec = TypeSpec.anonymousClassBuilder("").build(),
    ) {
        enumConstants[name] = type
    }

    public fun addStaticBlock(code: CodeBlock) {
        nativeBuilder.addStaticBlock(code)
    }

    public fun addStaticBlock(format: String, vararg args: Any) {
        nativeBuilder.addStaticBlock(codeBlockOf(format, *args))
    }

    public fun addInitializerBlock(code: CodeBlock) {
        nativeBuilder.addInitializerBlock(code)
    }

    public fun addInitializerBlock(format: String, vararg args: Any) {
        nativeBuilder.addInitializerBlock(codeBlockOf(format, *args))
    }

    public fun addOriginatingElement(originatingElement: Element) {
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
