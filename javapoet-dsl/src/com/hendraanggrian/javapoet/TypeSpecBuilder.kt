package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.AnnotationManager
import com.hendraanggrian.javapoet.internal.FieldSpecManager
import com.hendraanggrian.javapoet.internal.JavadocManager
import com.hendraanggrian.javapoet.internal.MethodSpecManager
import com.hendraanggrian.javapoet.internal.ModifierManager
import com.hendraanggrian.javapoet.internal.TypeSpecManager
import com.hendraanggrian.javapoet.internal.TypeVariableManager
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeVariableName
import java.lang.reflect.Type
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier

/** Returns a class builder with custom initialization block. */
fun buildTypeSpec(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpecBuilder =
    TypeSpecBuilderImpl(TypeSpec.classBuilder(name)).also { builder?.invoke(it) }

/** Returns a class builder with custom initialization block. */
fun buildTypeSpec(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpecBuilder =
    TypeSpecBuilderImpl(TypeSpec.classBuilder(className)).also { builder?.invoke(it) }

/** Returns an interface builder with custom initialization block. */
fun buildInterfaceTypeSpec(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpecBuilder =
    TypeSpecBuilderImpl(TypeSpec.interfaceBuilder(name)).also { builder?.invoke(it) }

/** Returns an interface builder with custom initialization block. */
fun buildInterfaceTypeSpec(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpecBuilder =
    TypeSpecBuilderImpl(TypeSpec.interfaceBuilder(className)).also { builder?.invoke(it) }

/** Returns an enum builder with custom initialization block. */
fun buildEnumTypeSpec(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpecBuilder =
    TypeSpecBuilderImpl(TypeSpec.enumBuilder(name)).also { builder?.invoke(it) }

/** Returns an enum builder with custom initialization block. */
fun buildEnumTypeSpec(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpecBuilder =
    TypeSpecBuilderImpl(TypeSpec.enumBuilder(className)).also { builder?.invoke(it) }

/** Returns an anonymous class builder with custom initialization block. */
fun buildAnonymousTypeSpec(
    typeArgumentsFormat: String,
    vararg args: Any,
    builder: (TypeSpecBuilder.() -> Unit)? = null
): TypeSpecBuilder = TypeSpecBuilderImpl(TypeSpec.anonymousClassBuilder(typeArgumentsFormat, *args))
    .also { builder?.invoke(it) }

/** Returns an anonymous class builder with custom initialization block. */
fun buildAnonymousTypeSpec(typeArguments: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpecBuilder =
    TypeSpecBuilderImpl(TypeSpec.anonymousClassBuilder(typeArguments)).also { builder?.invoke(it) }

/** Returns an annotation builder with custom initialization block. */
fun buildAnnotationTypeSpec(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpecBuilder =
    TypeSpecBuilderImpl(TypeSpec.annotationBuilder(name)).also { builder?.invoke(it) }

/** Returns an annotation builder with custom initialization block. */
fun buildAnnotationTypeSpec(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpecBuilder =
    TypeSpecBuilderImpl(TypeSpec.annotationBuilder(className)).also { builder?.invoke(it) }

interface TypeSpecBuilder : JavadocManager,
    AnnotationManager,
    ModifierManager,
    TypeVariableManager,
    FieldSpecManager,
    MethodSpecManager,
    TypeSpecManager {

    val nativeBuilder: TypeSpec.Builder

    override fun javadoc(format: String, vararg args: Any) {
        nativeBuilder.addJavadoc(format, *args)
    }

    override var javadoc: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.addJavadoc(value)
        }

    override fun annotation(type: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(buildAnnotationSpec(type, builder).build())
    }

    override fun annotation(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(buildAnnotationSpec(type, builder).build())
    }

    override fun modifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    override fun typeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    override fun typeVariable(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    var superclass: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.superclass(value)
        }

    var superclassType: Type
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.superclass(value)
        }

    fun superiface(superiface: TypeName) {
        nativeBuilder.addSuperinterface(superiface)
    }

    fun superiface(superiface: Type) {
        nativeBuilder.addSuperinterface(superiface)
    }

    fun enumConstant(name: String) {
        nativeBuilder.addEnumConstant(name)
    }

    fun enumConstant(name: String, name2: String, builder: TypeSpecBuilder.() -> Unit) {
        nativeBuilder.addEnumConstant(name, buildTypeSpec(name2, builder).build())
    }

    override fun field(type: TypeName, name: String, builder: (FieldSpecBuilder.() -> Unit)?) {
        nativeBuilder.addField(buildFieldSpec(type, name, builder).build())
    }

    override fun field(type: Type, name: String, builder: (FieldSpecBuilder.() -> Unit)?) {
        nativeBuilder.addField(buildFieldSpec(type, name, builder).build())
    }

    fun staticBlock(block: CodeBlock) {
        nativeBuilder.addStaticBlock(block)
    }

    fun initializerBlock(block: CodeBlock) {
        nativeBuilder.addInitializerBlock(block)
    }

    override fun method(name: String, builder: (MethodSpecBuilder.() -> Unit)?) {
        nativeBuilder.addMethod(buildMethodSpec(name, builder).build())
    }

    override fun constructor(builder: (MethodSpecBuilder.() -> Unit)?) {
        nativeBuilder.addMethod(buildConstructorMethodSpec(builder).build())
    }

    override fun type(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildTypeSpec(name, builder).build())
    }

    override fun type(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildTypeSpec(className, builder).build())
    }

    override fun interfaceType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildInterfaceTypeSpec(name, builder).build())
    }

    override fun interfaceType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildInterfaceTypeSpec(className, builder).build())
    }

    override fun enumType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildEnumTypeSpec(name, builder).build())
    }

    override fun enumType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildEnumTypeSpec(className, builder).build())
    }

    override fun anonymousType(typeArgumentsFormat: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildAnonymousTypeSpec(typeArgumentsFormat, *args, builder = builder).build())
    }

    override fun anonymousType(typeArguments: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildAnonymousTypeSpec(typeArguments, builder).build())
    }

    override fun annotationType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildAnnotationTypeSpec(name, builder).build())
    }

    override fun annotationType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildAnnotationTypeSpec(className, builder).build())
    }

    fun originatingElement(originatingElement: Element) {
        nativeBuilder.addOriginatingElement(originatingElement)
    }

    fun build(): TypeSpec = nativeBuilder.build()
}

internal class TypeSpecBuilderImpl(override val nativeBuilder: TypeSpec.Builder) : TypeSpecBuilder