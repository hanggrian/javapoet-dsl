package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.collections.AnnotationSpecList
import com.hendraanggrian.javapoet.collections.AnnotationSpecListScope
import com.hendraanggrian.javapoet.collections.EnumConstantMap
import com.hendraanggrian.javapoet.collections.EnumConstantMapScope
import com.hendraanggrian.javapoet.collections.FieldSpecList
import com.hendraanggrian.javapoet.collections.FieldSpecListScope
import com.hendraanggrian.javapoet.collections.JavadocContainer
import com.hendraanggrian.javapoet.collections.JavadocContainerScope
import com.hendraanggrian.javapoet.collections.MethodSpecList
import com.hendraanggrian.javapoet.collections.MethodSpecListScope
import com.hendraanggrian.javapoet.collections.TypeNameList
import com.hendraanggrian.javapoet.collections.TypeSpecList
import com.hendraanggrian.javapoet.collections.TypeSpecListScope
import com.hendraanggrian.javapoet.collections.TypeVariableNameList
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import java.lang.reflect.Type
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import kotlin.reflect.KClass

/**
 * Builds new class [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildClassTypeSpec(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(type)).apply(configuration).build()

/**
 * Builds new class [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildClassTypeSpec(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(type)).apply(configuration).build()

/**
 * Builds new interface [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildInterfaceTypeSpec(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(type)).apply(configuration).build()

/**
 * Builds new interface [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildInterfaceTypeSpec(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(type)).apply(configuration).build()

/**
 * Builds new enum [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildEnumTypeSpec(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(type)).apply(configuration).build()

/**
 * Builds new enum [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildEnumTypeSpec(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(type)).apply(configuration).build()

/**
 * Builds new anonymous [TypeSpec] from formatting,
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 * Not inlining this function since [internalFormat] is not inlined.
 */
fun buildAnonymousTypeSpec(format: String, vararg args: Any, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    format.internalFormat(args) { format2, args2 ->
        TypeSpecBuilder(TypeSpec.anonymousClassBuilder(format2, *args2)).apply(configuration).build()
    }

/**
 * Builds new anonymous [TypeSpec] from [CodeBlock],
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildAnonymousTypeSpec(code: CodeBlock, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.anonymousClassBuilder(code)).apply(configuration).build()

/**
 * Builds new annotation [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildAnnotationTypeSpec(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.annotationBuilder(type)).apply(configuration).build()

/**
 * Builds new annotation [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [configuration].
 */
fun buildAnnotationTypeSpec(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
    TypeSpecBuilder(TypeSpec.annotationBuilder(type)).apply(configuration).build()

/** Modify existing [TypeSpec.Builder] using provided [configuration]. */
fun TypeSpec.Builder.edit(configuration: TypeSpecBuilder.() -> Unit): TypeSpec.Builder =
    TypeSpecBuilder(this).apply(configuration).nativeBuilder

/**
 * Wrapper of [TypeSpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@SpecMarker
class TypeSpecBuilder internal constructor(val nativeBuilder: TypeSpec.Builder) {

    /** Modifiers of this type. */
    val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers

    /** Originating elements of this type. */
    val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements

    /** Always qualified names of this type. */
    val alwaysQualifiedNames: MutableSet<String> get() = nativeBuilder.alwaysQualifiedNames

    /** Javadoc of this type. */
    val javadoc: JavadocContainer = object : JavadocContainer {
        override fun append(format: String, vararg args: Any): Unit =
            format.internalFormat(args) { format2, args2 -> nativeBuilder.addJavadoc(format2, *args2) }

        override fun append(code: CodeBlock) {
            nativeBuilder.addJavadoc(code)
        }
    }

    /** Configures javadoc of this type. */
    fun javadoc(configuration: JavadocContainerScope.() -> Unit): Unit =
        JavadocContainerScope(javadoc).configuration()

    /** Annotations of this type. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configures annotations of this type. */
    fun annotations(configuration: AnnotationSpecListScope.() -> Unit): Unit =
        AnnotationSpecListScope(annotations).configuration()

    /** Add type modifiers. */
    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Type variables of this type. */
    val typeVariables: TypeVariableNameList = TypeVariableNameList(nativeBuilder.typeVariables)

    /** Configures type variables of this method. */
    fun typeVariables(configuration: TypeVariableNameList.() -> Unit): Unit = typeVariables.configuration()

    /** Set superclass to type. */
    var superclass: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.superclass(value)
        }

    /** Set superclass to [type]. */
    fun superclass(type: Type) {
        nativeBuilder.superclass(type)
    }

    /** Set superclass to [type]. */
    fun superclass(type: KClass<*>) {
        nativeBuilder.superclass(type.java)
    }

    /** Set superclass to [T]. */
    inline fun <reified T> superclass(): Unit = superclass(T::class)

    /** Super interfaces of this type. */
    val superinterfaces: TypeNameList = TypeNameList(nativeBuilder.superinterfaces)

    /** Configures super interfaces of this type. */
    fun superinterfaces(configuration: TypeNameList.() -> Unit): Unit = superinterfaces.configuration()

    /** Enum constants of this type. */
    val enumConstants: EnumConstantMap = EnumConstantMap(nativeBuilder.enumConstants)

    /** Configures enum constants of this type. */
    fun enumConstants(configuration: EnumConstantMapScope.() -> Unit): Unit =
        EnumConstantMapScope(enumConstants).configuration()

    /** Fields of this type. */
    val fields: FieldSpecList = FieldSpecList(nativeBuilder.fieldSpecs)

    /** Configures fields of this type. */
    fun fields(configuration: FieldSpecListScope.() -> Unit): Unit = FieldSpecListScope(fields).configuration()

    /** Add static block from [CodeBlock]. */
    fun addStaticBlock(code: CodeBlock) {
        nativeBuilder.addStaticBlock(code)
    }

    /** Add static block from formatted string. */
    fun addStaticBlock(format: String, vararg args: Any) {
        nativeBuilder.addStaticBlock(codeBlockOf(format, *args))
    }

    /** Add initializer block from [CodeBlock]. */
    fun addInitializerBlock(code: CodeBlock) {
        nativeBuilder.addInitializerBlock(code)
    }

    /** Add initializer block from formatted string. */
    fun addInitializerBlock(format: String, vararg args: Any) {
        nativeBuilder.addInitializerBlock(codeBlockOf(format, *args))
    }

    /** Methods of this type. */
    val methods: MethodSpecList = MethodSpecList(nativeBuilder.methodSpecs)

    /** Configures methods of this type. */
    fun methods(configuration: MethodSpecListScope.() -> Unit): Unit = MethodSpecListScope(methods).configuration()

    /** Types of this type. */
    val types: TypeSpecList = TypeSpecList(nativeBuilder.typeSpecs)

    /** Configures types of this type. */
    fun types(configuration: TypeSpecListScope.() -> Unit): Unit = TypeSpecListScope(types).configuration()

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

    inline fun <reified T> avoidClashesWithNestedClasses(): Unit = avoidClashesWithNestedClasses(T::class)

    /** Returns native spec. */
    fun build(): TypeSpec = nativeBuilder.build()
}
