package io.github.hendraanggrian.javapoet

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import io.github.hendraanggrian.javapoet.collections.AnnotationSpecList
import io.github.hendraanggrian.javapoet.collections.AnnotationSpecListScope
import io.github.hendraanggrian.javapoet.collections.FieldSpecList
import io.github.hendraanggrian.javapoet.collections.FieldSpecListScope
import io.github.hendraanggrian.javapoet.collections.JavadocContainer
import io.github.hendraanggrian.javapoet.collections.JavadocContainerScope
import io.github.hendraanggrian.javapoet.collections.MethodSpecList
import io.github.hendraanggrian.javapoet.collections.MethodSpecListScope
import io.github.hendraanggrian.javapoet.collections.TypeNameList
import io.github.hendraanggrian.javapoet.collections.TypeSpecList
import io.github.hendraanggrian.javapoet.collections.TypeSpecListScope
import io.github.hendraanggrian.javapoet.collections.TypeVariableNameList
import java.lang.reflect.Type
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import kotlin.reflect.KClass

/** Builds new class [TypeSpec] with name. */
fun classTypeSpecOf(type: String): TypeSpec = TypeSpec.classBuilder(type).build()

/** Builds new class [TypeSpec] from [ClassName]. */
fun classTypeSpecOf(type: ClassName): TypeSpec = TypeSpec.classBuilder(type).build()

/** Builds new interface [TypeSpec] from name. */
fun interfaceTypeSpecOf(type: String): TypeSpec = TypeSpec.interfaceBuilder(type).build()

/** Builds new interface [TypeSpec] from [ClassName]. */
fun interfaceTypeSpecOf(type: ClassName): TypeSpec = TypeSpec.interfaceBuilder(type).build()

/** Builds new enum [TypeSpec] from name. */
fun enumTypeSpecOf(type: String): TypeSpec = TypeSpec.enumBuilder(type).build()

/** Builds new enum [TypeSpec] from [ClassName]. */
fun enumTypeSpecOf(type: ClassName): TypeSpec = TypeSpec.enumBuilder(type).build()

/** Builds new anonymous [TypeSpec] from formatting. */
fun anonymousTypeSpecOf(format: String, vararg args: Any): TypeSpec =
    format.internalFormat(args) { s, array -> TypeSpec.anonymousClassBuilder(s, *array).build() }

/** Builds new anonymous [TypeSpec] from [CodeBlock]. */
fun anonymousTypeSpecOf(code: CodeBlock): TypeSpec = TypeSpec.anonymousClassBuilder(code).build()

/** Builds new annotation [TypeSpec] from name. */
fun annotationTypeSpecOf(type: String): TypeSpec = TypeSpec.annotationBuilder(type).build()

/** Builds new annotation [TypeSpec] from [ClassName]. */
fun annotationTypeSpecOf(type: ClassName): TypeSpec = TypeSpec.annotationBuilder(type).build()

/**
 * Builds new class [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [builderAction].
 */
inline fun buildClassTypeSpec(
    type: String,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpecBuilder(TypeSpec.classBuilder(type)).apply(builderAction).build()

/**
 * Builds new class [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction].
 */
inline fun buildClassTypeSpec(
    type: ClassName,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpecBuilder(TypeSpec.classBuilder(type)).apply(builderAction).build()

/**
 * Builds new interface [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [builderAction].
 */
inline fun buildInterfaceTypeSpec(
    type: String,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpecBuilder(TypeSpec.interfaceBuilder(type)).apply(builderAction).build()

/**
 * Builds new interface [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction].
 */
inline fun buildInterfaceTypeSpec(
    type: ClassName,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpecBuilder(TypeSpec.interfaceBuilder(type)).apply(builderAction).build()

/**
 * Builds new enum [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [builderAction].
 */
inline fun buildEnumTypeSpec(
    type: String,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpecBuilder(TypeSpec.enumBuilder(type)).apply(builderAction).build()

/**
 * Builds new enum [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction].
 */
inline fun buildEnumTypeSpec(
    type: ClassName,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpecBuilder(TypeSpec.enumBuilder(type)).apply(builderAction).build()

/**
 * Builds new anonymous [TypeSpec] from formatting,
 * by populating newly created [TypeSpecBuilder] using provided [builderAction].
 * Not inlining this function since [internalFormat] is not inlined.
 */
fun buildAnonymousTypeSpec(
    format: String,
    vararg args: Any,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = format.internalFormat(args) { s, array ->
    TypeSpecBuilder(TypeSpec.anonymousClassBuilder(s, *array)).apply(builderAction).build()
}

/**
 * Builds new anonymous [TypeSpec] from [CodeBlock],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction].
 */
inline fun buildAnonymousTypeSpec(
    code: CodeBlock,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpecBuilder(TypeSpec.anonymousClassBuilder(code)).apply(builderAction).build()

/**
 * Builds new annotation [TypeSpec] from name,
 * by populating newly created [TypeSpecBuilder] using provided [builderAction].
 */
inline fun buildAnnotationTypeSpec(
    type: String,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpecBuilder(TypeSpec.annotationBuilder(type)).apply(builderAction).build()

/**
 * Builds new annotation [TypeSpec] from [ClassName],
 * by populating newly created [TypeSpecBuilder] using provided [builderAction].
 */
inline fun buildAnnotationTypeSpec(
    type: ClassName,
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec = TypeSpecBuilder(TypeSpec.annotationBuilder(type)).apply(builderAction).build()

/** Modify existing [TypeSpec.Builder] using provided [builderAction]. */
inline fun TypeSpec.Builder.edit(
    builderAction: TypeSpecBuilder.() -> Unit
): TypeSpec.Builder = TypeSpecBuilder(this).apply(builderAction).nativeBuilder

/**
 * Wrapper of [TypeSpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@SpecDslMarker
class TypeSpecBuilder(val nativeBuilder: TypeSpec.Builder) {

    /** Enum constants of this type. */
    val enumConstants: MutableMap<String, TypeSpec> get() = nativeBuilder.enumConstants

    /** Modifiers of this type. */
    val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers

    /** Originating elements of this type. */
    val originatingElements: MutableList<Element> get() = nativeBuilder.originatingElements

    /** Always qualified names of this type. */
    val alwaysQualifiedNames: MutableSet<String> get() = nativeBuilder.alwaysQualifiedNames

    /** Javadoc of this type. */
    val javadoc: JavadocContainer = object : JavadocContainer() {
        override fun append(format: String, vararg args: Any): Unit =
            format.internalFormat(args) { s, array -> nativeBuilder.addJavadoc(s, *array) }

        override fun append(code: CodeBlock) {
            nativeBuilder.addJavadoc(code)
        }
    }

    /** Configures javadoc of this type. */
    inline fun javadoc(builderAction: JavadocContainerScope.() -> Unit): Unit =
        JavadocContainerScope(javadoc).builderAction()

    /** Annotations of this type. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configures annotations of this type. */
    inline fun annotations(builderAction: AnnotationSpecListScope.() -> Unit): Unit =
        AnnotationSpecListScope(annotations).builderAction()

    /** Add type modifiers. */
    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Type variables of this type. */
    val typeVariables: TypeVariableNameList = TypeVariableNameList(nativeBuilder.typeVariables)

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

    /** Add enum constant with name. */
    fun addEnumConstant(name: String) {
        nativeBuilder.addEnumConstant(name)
    }

    /** Add enum constant with name and specified [TypeSpec]. */
    fun addEnumConstant(name: String, spec: TypeSpec) {
        nativeBuilder.addEnumConstant(name, spec)
    }

    /** Fields of this type. */
    val fields: FieldSpecList = FieldSpecList(nativeBuilder.fieldSpecs)

    /** Configures fields of this type. */
    inline fun fields(builderAction: FieldSpecListScope.() -> Unit): Unit =
        FieldSpecListScope(fields).builderAction()

    /** Add static block containing [code]. */
    fun addStaticBlock(code: CodeBlock) {
        nativeBuilder.addStaticBlock(code)
    }

    /** Add static block containing code with custom initialization [builderAction]. */
    inline fun addStaticBlock(builderAction: CodeBlockBuilder.() -> Unit): Unit =
        addStaticBlock(buildCodeBlock(builderAction))

    /** Add initializer block containing [code]. */
    fun addInitializerBlock(code: CodeBlock) {
        nativeBuilder.addInitializerBlock(code)
    }

    /** Add initializer block containing code with custom initialization [builderAction]. */
    inline fun addInitializerBlock(builderAction: CodeBlockBuilder.() -> Unit): Unit =
        addInitializerBlock(buildCodeBlock(builderAction))

    /** Methods of this type. */
    val methods: MethodSpecList = MethodSpecList(nativeBuilder.methodSpecs)

    /** Configures methods of this type. */
    inline fun methods(builderAction: MethodSpecListScope.() -> Unit): Unit =
        MethodSpecListScope(methods).builderAction()

    /** Types of this type. */
    val types: TypeSpecList = TypeSpecList(nativeBuilder.typeSpecs)

    /** Configures types of this type. */
    inline fun types(builderAction: TypeSpecListScope.() -> Unit): Unit =
        TypeSpecListScope(types).builderAction()

    /** Add originating element. */
    fun addOriginatingElement(originatingElement: Element) {
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

    inline fun <reified T> avoidClashesWithNestedClasses(): Unit = avoidClashesWithNestedClasses(T::class)

    /** Returns native spec. */
    fun build(): TypeSpec = nativeBuilder.build()
}