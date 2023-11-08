@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeVariableName
import java.lang.reflect.Type
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
    return buildClassTypeSpec(name, configuration).also(types::add)
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
    return buildInterfaceTypeSpec(name, configuration).also(types::add)
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
    return buildEnumTypeSpec(name, configuration).also(types::add)
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
    return buildAnonymousTypeSpec(format, *args, configuration = configuration).also(types::add)
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
    return buildAnnotationTypeSpec(name, configuration).also(types::add)
}

/**
 * Property delegate for inserting new class [TypeSpec] by populating newly created [TypeSpecBuilder]
 * using provided [configuration].
 */
fun TypeSpecHandler.classTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildClassTypeSpec(it, configuration).also(types::add) }
}

/**
 * Property delegate for inserting new interface [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
fun TypeSpecHandler.interfaceTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildInterfaceTypeSpec(it, configuration).also(types::add) }
}

/**
 * Property delegate for inserting new interface [TypeSpec] by populating newly created
 * [TypeSpecBuilder] using provided [configuration].
 */
fun TypeSpecHandler.enumTyping(
    configuration: TypeSpecBuilder.() -> Unit,
): SpecDelegateProvider<TypeSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildEnumTypeSpec(it, configuration).also(types::add) }
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
        buildAnonymousTypeSpec(it, configuration = configuration).also(types::add)
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
    return SpecDelegateProvider { buildAnnotationTypeSpec(it, configuration).also(types::add) }
}

/** Invokes DSL to configure [TypeSpec] collection. */
fun TypeSpecHandler.types(configuration: TypeSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    TypeSpecHandlerScope(types).configuration()
}

/** Responsible for managing a set of [TypeSpec] instances. */
sealed interface TypeSpecHandler {
    val types: MutableList<TypeSpec>

    fun classType(name: String): TypeSpec = TypeSpec.classBuilder(name).build().also(types::add)

    fun interfaceType(name: String): TypeSpec =
        TypeSpec.interfaceBuilder(name).build().also(types::add)

    fun enumType(name: String): TypeSpec = TypeSpec.enumBuilder(name).build().also(types::add)

    fun anonymousType(format: String, vararg args: Any): TypeSpec =
        format.internalFormat(args) { format2, args2 ->
            TypeSpecBuilder(TypeSpec.anonymousClassBuilder(format2, *args2))
                .build()
                .also(types::add)
        }

    fun annotationType(name: String): TypeSpec =
        TypeSpec.annotationBuilder(name).build().also(types::add)

    fun classTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { TypeSpec.classBuilder(it).build().also(types::add) }

    fun interfaceTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { TypeSpec.interfaceBuilder(it).build().also(types::add) }

    fun enumTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { TypeSpec.enumBuilder(it).build().also(types::add) }

    fun anonymousTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { TypeSpec.anonymousClassBuilder(it).build().also(types::add) }

    fun annotationTyping(): SpecDelegateProvider<TypeSpec> =
        SpecDelegateProvider { TypeSpec.annotationBuilder(it).build().also(types::add) }
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@JavapoetSpecDsl
class TypeSpecHandlerScope(
    actualList: MutableList<TypeSpec>,
) : MutableList<TypeSpec> by actualList

/** Wrapper of [TypeSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetSpecDsl
class TypeSpecBuilder(
    private val nativeBuilder: TypeSpec.Builder,
) : AnnotationSpecHandler, FieldSpecHandler, MethodSpecHandler, TypeSpecHandler {
    override val annotations: MutableList<AnnotationSpec> = nativeBuilder.annotations
    val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers
    val typeVariables: MutableList<TypeVariableName> = nativeBuilder.typeVariables
    val superinterfaces: MutableList<TypeName> = nativeBuilder.superinterfaces
    val enumConstants: MutableMap<String, TypeSpec> = nativeBuilder.enumConstants
    override val fields: MutableList<FieldSpec> = nativeBuilder.fieldSpecs
    override val methods: MutableList<MethodSpec> = nativeBuilder.methodSpecs
    override val types: MutableList<TypeSpec> = nativeBuilder.typeSpecs
    val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements
    val alwaysQualifiedNames: MutableSet<String> get() = nativeBuilder.alwaysQualifiedNames

    val javadoc: JavadocContainer =
        object : JavadocContainer {
            override fun append(format: String, vararg args: Any): Unit =
                format.internalFormat(args) { format2, args2 ->
                    nativeBuilder.addJavadoc(format2, *args2)
                }

            override fun append(code: CodeBlock) {
                nativeBuilder.addJavadoc(code)
            }
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

    fun superclass(type: Type) {
        nativeBuilder.superclass(type)
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

    fun superinterface(superinterface: Type) {
        nativeBuilder.addSuperinterface(superinterface)
    }

    fun superinterface(superinterface: KClass<*>) {
        nativeBuilder.addSuperinterface(superinterface.java)
    }

    inline fun <reified T> superinterface(): Unit = superinterface(T::class)

    fun superinterface(superinterface: TypeMirror, avoidNestedTypeNameClashes: Boolean = true) {
        nativeBuilder.addSuperinterface(superinterface, avoidNestedTypeNameClashes)
    }

    fun enumConstant(name: String) {
        nativeBuilder.addEnumConstant(name)
    }

    fun enumConstant(name: String, spec: TypeSpec) {
        nativeBuilder.addEnumConstant(name, spec)
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

    fun originatingElement(originatingElement: Element) {
        nativeBuilder.addOriginatingElement(originatingElement)
    }

    fun alwaysQualify(vararg simpleNames: String) {
        nativeBuilder.alwaysQualify(*simpleNames)
    }

    fun avoidClashesWithNestedClasses(typeElement: TypeElement) {
        nativeBuilder.avoidClashesWithNestedClasses(typeElement)
    }

    fun avoidClashesWithNestedClasses(type: Class<*>) {
        nativeBuilder.avoidClashesWithNestedClasses(type)
    }

    fun avoidClashesWithNestedClasses(type: KClass<*>) {
        nativeBuilder.avoidClashesWithNestedClasses(type.java)
    }

    inline fun <reified T> avoidClashesWithNestedClasses(): Unit =
        avoidClashesWithNestedClasses(T::class)

    fun build(): TypeSpec = nativeBuilder.build()
}
