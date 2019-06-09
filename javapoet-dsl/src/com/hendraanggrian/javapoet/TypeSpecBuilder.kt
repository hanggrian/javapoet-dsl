package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.SpecBuilder
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeVariableName
import java.lang.reflect.Type
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier

/** Returns a type builder with custom initialization block. */
fun buildTypeSpec(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns a type builder with custom initialization block. */
fun buildTypeSpec(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(className))
        .also { builder?.invoke(it) }
        .build()

/** Returns an interfaceType builder with custom initialization block. */
fun buildInterfaceTypeSpec(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns an interfaceType builder with custom initialization block. */
fun buildInterfaceTypeSpec(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(className))
        .also { builder?.invoke(it) }
        .build()

/** Returns an enumType builder with custom initialization block. */
fun buildEnumTypeSpec(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns an enumType builder with custom initialization block. */
fun buildEnumTypeSpec(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(className))
        .also { builder?.invoke(it) }
        .build()

/** Returns an anonymousType type builder with custom initialization block. */
fun buildAnonymousTypeSpec(
    typeArgumentsFormat: String,
    vararg args: Any,
    builder: (TypeSpecBuilder.() -> Unit)? = null
): TypeSpec = TypeSpecBuilder(TypeSpec.anonymousClassBuilder(typeArgumentsFormat, *args))
    .also { builder?.invoke(it) }
    .build()

/** Returns an anonymousType type builder with custom initialization block. */
fun buildAnonymousTypeSpec(typeArguments: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.anonymousClassBuilder(typeArguments))
        .also { builder?.invoke(it) }
        .build()

/** Returns an annotation builder with custom initialization block. */
fun buildAnnotationTypeSpec(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.annotationBuilder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns an annotation builder with custom initialization block. */
fun buildAnnotationTypeSpec(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.annotationBuilder(className))
        .also { builder?.invoke(it) }
        .build()

@SpecBuilderDslMarker
class TypeSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: TypeSpec.Builder) :
    SpecBuilder<TypeSpec>(),
    JavadocableSpecBuilder,
    AnnotableSpecBuilder,
    ModifierableSpecBuilder,
    TypeVariableSpecBuilder,
    TypableSpecBuilder {

    override fun javadoc(format: String, vararg args: Any) {
        nativeBuilder.addJavadoc(format, *args)
    }

    override fun javadoc(builder: CodeBlockBuilder.() -> Unit) {
        nativeBuilder.addJavadoc(buildCodeBlock(builder))
    }

    override fun annotation(name: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(buildAnnotationSpec(name, builder))
    }

    override fun annotation(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(buildAnnotationSpec(type, builder))
    }

    inline fun <reified T> annotation(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        annotation(T::class.java, builder)

    override var modifiers: Collection<Modifier>
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.addModifiers(*value.toTypedArray())
        }

    override var typeVariable: TypeVariableName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.addTypeVariable(value)
        }

    override var typeVariables: Iterable<TypeVariableName>
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.addTypeVariables(value)
        }

    var superClass: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.superclass(value)
        }

    fun superClass(type: Type) {
        nativeBuilder.superclass(type)
    }

    inline fun <reified T> superClass() = superClass(T::class.java)

    var superInterface: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.addSuperinterface(value)
        }

    fun superInterface(type: Type) {
        nativeBuilder.addSuperinterface(type)
    }

    inline fun <reified T> superInterface() = superInterface(T::class.java)

    fun enumConstant(name: String) {
        nativeBuilder.addEnumConstant(name)
    }

    fun enumConstant(name: String, name2: String, builder: (TypeSpecBuilder.() -> Unit)? = null) {
        nativeBuilder.addEnumConstant(name, buildTypeSpec(name2, builder))
    }

    fun field(type: TypeName, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null) {
        nativeBuilder.addField(buildFieldSpec(type, name, builder))
    }

    fun field(type: Type, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null) {
        nativeBuilder.addField(buildFieldSpec(type, name, builder))
    }

    inline fun <reified T> field(name: String, noinline builder: (FieldSpecBuilder.() -> Unit)? = null) =
        field(T::class.java, name, builder)

    fun staticBlock(builder: CodeBlockBuilder.() -> Unit) {
        nativeBuilder.addStaticBlock(buildCodeBlock(builder))
    }

    fun initializerBlock(builder: CodeBlockBuilder.() -> Unit) {
        nativeBuilder.addInitializerBlock(buildCodeBlock(builder))
    }

    fun method(name: String, builder: (MethodSpecBuilder.() -> Unit)? = null) {
        nativeBuilder.addMethod(buildMethodSpec(name, builder))
    }

    fun constructor(builder: (MethodSpecBuilder.() -> Unit)? = null) {
        nativeBuilder.addMethod(buildConstructorMethodSpec(builder))
    }

    override fun type(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildTypeSpec(name, builder))
    }

    override fun type(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildTypeSpec(name, builder))
    }

    override fun interfaceType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildInterfaceTypeSpec(name, builder))
    }

    override fun interfaceType(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildInterfaceTypeSpec(name, builder))
    }

    override fun enumType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildEnumTypeSpec(name, builder))
    }

    override fun enumType(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildEnumTypeSpec(name, builder))
    }

    override fun anonymousType(typeArgumentsFormat: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildAnonymousTypeSpec(typeArgumentsFormat, *args, builder = builder))
    }

    override fun anonymousType(typeArguments: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildAnonymousTypeSpec(typeArguments, builder))
    }

    override fun annotationType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildAnnotationTypeSpec(name, builder))
    }

    override fun annotationType(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildAnnotationTypeSpec(name, builder))
    }

    fun originatingElement(originatingElement: Element) {
        nativeBuilder.addOriginatingElement(originatingElement)
    }

    override fun build(): TypeSpec = nativeBuilder.build()
}