package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.collections.AnnotationSpecCollection
import com.hendraanggrian.javapoet.collections.AnnotationSpecCollectionScope
import com.hendraanggrian.javapoet.collections.FieldSpecCollection
import com.hendraanggrian.javapoet.collections.FieldSpecCollectionScope
import com.hendraanggrian.javapoet.collections.JavadocCollection
import com.hendraanggrian.javapoet.collections.JavadocCollectionScope
import com.hendraanggrian.javapoet.collections.MethodSpecCollection
import com.hendraanggrian.javapoet.collections.MethodSpecCollectionScope
import com.hendraanggrian.javapoet.collections.TypeNameCollection
import com.hendraanggrian.javapoet.collections.TypeSpecCollection
import com.hendraanggrian.javapoet.collections.TypeSpecCollectionScope
import com.hendraanggrian.javapoet.collections.TypeVariableNameCollection
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

    /** Enum constants of this type. */
    val enumConstants: MutableMap<String, TypeSpec> get() = nativeBuilder.enumConstants

    /** Modifiers of this type. */
    val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers

    /** Originating elements of this type. */
    val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements

    /** Always qualified names of this type. */
    val alwaysQualifiedNames: MutableSet<String> get() = nativeBuilder.alwaysQualifiedNames

    /** Javadoc of this type. */
    val javadoc: JavadocCollection = object : JavadocCollection() {
        override fun append(format: String, vararg args: Any): Unit =
            format.internalFormat(args) { format2, args2 -> nativeBuilder.addJavadoc(format2, *args2) }

        override fun append(code: CodeBlock) {
            nativeBuilder.addJavadoc(code)
        }
    }

    /** Configures javadoc of this type. */
    fun javadoc(configuration: JavadocCollectionScope.() -> Unit): Unit =
        JavadocCollectionScope(javadoc).configuration()

    /** Annotations of this type. */
    val annotations: AnnotationSpecCollection = AnnotationSpecCollection(nativeBuilder.annotations)

    /** Configures annotations of this type. */
    fun annotations(configuration: AnnotationSpecCollectionScope.() -> Unit): Unit =
        AnnotationSpecCollectionScope(annotations).configuration()

    /** Add type modifiers. */
    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Type variables of this type. */
    val typeVariables: TypeVariableNameCollection = TypeVariableNameCollection(nativeBuilder.typeVariables)

    /** Configures type variables of this method. */
    fun typeVariables(configuration: TypeVariableNameCollection.() -> Unit): Unit = typeVariables.configuration()

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
    val superinterfaces: TypeNameCollection = TypeNameCollection(nativeBuilder.superinterfaces)

    /** Configures super interfaces of this type. */
    fun superinterfaces(configuration: TypeNameCollection.() -> Unit): Unit = superinterfaces.configuration()

    /** Add enum constant with name and specified [TypeSpec]. */
    fun addEnumConstant(name: String, spec: TypeSpec = TypeSpec.anonymousClassBuilder("").build()) {
        nativeBuilder.addEnumConstant(name, spec)
    }

    /** Fields of this type. */
    val fields: FieldSpecCollection = FieldSpecCollection(nativeBuilder.fieldSpecs)

    /** Configures fields of this type. */
    fun fields(configuration: FieldSpecCollectionScope.() -> Unit): Unit =
        FieldSpecCollectionScope(fields).configuration()

    /** Add static block containing [code]. */
    fun addStaticBlock(code: CodeBlock) {
        nativeBuilder.addStaticBlock(code)
    }

    /** Add initializer block containing [code]. */
    fun addInitializerBlock(code: CodeBlock) {
        nativeBuilder.addInitializerBlock(code)
    }

    /** Methods of this type. */
    val methods: MethodSpecCollection = MethodSpecCollection(nativeBuilder.methodSpecs)

    /** Configures methods of this type. */
    fun methods(configuration: MethodSpecCollectionScope.() -> Unit): Unit =
        MethodSpecCollectionScope(methods).configuration()

    /** Types of this type. */
    val types: TypeSpecCollection = TypeSpecCollection(nativeBuilder.typeSpecs)

    /** Configures types of this type. */
    fun types(configuration: TypeSpecCollectionScope.() -> Unit): Unit =
        TypeSpecCollectionScope(types).configuration()

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
