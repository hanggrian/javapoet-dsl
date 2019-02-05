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
        nativeBuilder.addAnnotation(createAnnotation(type, builder))
    }

    override fun annotation(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(createAnnotation(type, builder))
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
        nativeBuilder.addEnumConstant(name, createType(name2, builder))
    }

    override fun field(type: TypeName, name: String, builder: (FieldSpecBuilder.() -> Unit)?) {
        nativeBuilder.addField(createField(type, name, builder))
    }

    override fun field(type: Type, name: String, builder: (FieldSpecBuilder.() -> Unit)?) {
        nativeBuilder.addField(createField(type, name, builder))
    }

    fun staticBlock(block: CodeBlock) {
        nativeBuilder.addStaticBlock(block)
    }

    fun initializerBlock(block: CodeBlock) {
        nativeBuilder.addInitializerBlock(block)
    }

    override fun method(name: String, builder: (MethodSpecBuilder.() -> Unit)?) {
        nativeBuilder.addMethod(createMethod(name, builder))
    }

    override fun constructor(builder: (MethodSpecBuilder.() -> Unit)?) {
        nativeBuilder.addMethod(createConstructor(builder))
    }

    override fun type(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(createType(name, builder))
    }

    override fun type(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(createType(className, builder))
    }

    override fun interfaceType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(createInterfaceType(name, builder))
    }

    override fun interfaceType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(createInterfaceType(className, builder))
    }

    override fun enumType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(createEnumType(name, builder))
    }

    override fun enumType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(createEnumType(className, builder))
    }

    override fun anonymousType(typeArgumentsFormat: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(createAnonymousType(typeArgumentsFormat, *args, builder = builder))
    }

    override fun anonymousType(typeArguments: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(createAnonymousType(typeArguments, builder))
    }

    override fun annotationType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(createAnnotationType(name, builder))
    }

    override fun annotationType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(createAnnotationType(className, builder))
    }

    fun originatingElement(originatingElement: Element) {
        nativeBuilder.addOriginatingElement(originatingElement)
    }
}

internal class TypeSpecBuilderImpl(override val nativeBuilder: TypeSpec.Builder) : TypeSpecBuilder