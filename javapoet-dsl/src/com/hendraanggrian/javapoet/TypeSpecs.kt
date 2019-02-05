package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.AnnotationManager
import com.hendraanggrian.javapoet.internal.FieldSpecManager
import com.hendraanggrian.javapoet.internal.JavadocManager
import com.hendraanggrian.javapoet.internal.MethodSpecManager
import com.hendraanggrian.javapoet.internal.ModifierManager
import com.hendraanggrian.javapoet.internal.TypeSpecManager
import com.hendraanggrian.javapoet.internal.TypeVariableManager
import com.squareup.javapoet.AnnotationSpec
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

    override fun javadoc(codeBlock: CodeBlock) {
        nativeBuilder.addJavadoc(codeBlock)
    }

    override fun annotations(annotationSpecs: Iterable<AnnotationSpec>) {
        nativeBuilder.addAnnotations(annotationSpecs)
    }

    override fun annotation(annotationSpec: AnnotationSpec) {
        nativeBuilder.addAnnotation(annotationSpec)
    }

    override fun annotation(annotation: ClassName) {
        nativeBuilder.addAnnotation(annotation)
    }

    override fun annotation(annotation: Class<*>) {
        nativeBuilder.addAnnotation(annotation)
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

    fun superclass(superclass: TypeName) {
        nativeBuilder.superclass(superclass)
    }

    fun superclass(superclass: Type) {
        nativeBuilder.superclass(superclass)
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

    override fun field(type: TypeName, name: String, builder: FieldSpecBuilder.() -> Unit) {
        nativeBuilder.addField(createField(type, name, builder))
    }

    override fun field(type: Type, name: String, builder: FieldSpecBuilder.() -> Unit) {
        nativeBuilder.addField(createField(type, name, builder))
    }

    fun staticBlock(block: CodeBlock) {
        nativeBuilder.addStaticBlock(block)
    }

    fun initializerBlock(block: CodeBlock) {
        nativeBuilder.addInitializerBlock(block)
    }

    override fun method(name: String, builder: MethodSpecBuilder.() -> Unit) {
        nativeBuilder.addMethod(createMethod(name, builder))
    }

    override fun constructor(builder: MethodSpecBuilder.() -> Unit) {
        nativeBuilder.addMethod(createConstructor(builder))
    }

    override fun type(name: String, builder: TypeSpecBuilder.() -> Unit) {
        nativeBuilder.addType(createType(name, builder))
    }

    override fun type(className: ClassName, builder: TypeSpecBuilder.() -> Unit) {
        nativeBuilder.addType(createType(className, builder))
    }

    override fun iface(name: String, builder: TypeSpecBuilder.() -> Unit) {
        nativeBuilder.addType(createInterface(name, builder))
    }

    override fun iface(className: ClassName, builder: TypeSpecBuilder.() -> Unit) {
        nativeBuilder.addType(createInterface(className, builder))
    }

    override fun enum(name: String, builder: TypeSpecBuilder.() -> Unit) {
        nativeBuilder.addType(createEnum(name, builder))
    }

    override fun enum(className: ClassName, builder: TypeSpecBuilder.() -> Unit) {
        nativeBuilder.addType(createEnum(className, builder))
    }

    override fun anonymous(typeArgumentsFormat: String, vararg args: Any, builder: TypeSpecBuilder.() -> Unit) {
        nativeBuilder.addType(createAnonymous(typeArgumentsFormat, *args, builder = builder))
    }

    override fun anonymous(typeArguments: CodeBlock, builder: TypeSpecBuilder.() -> Unit) {
        nativeBuilder.addType(createAnonymous(typeArguments, builder))
    }

    override fun annotation(name: String, builder: TypeSpecBuilder.() -> Unit) {
        nativeBuilder.addType(createAnnotation(name, builder))
    }

    override fun annotation(className: ClassName, builder: TypeSpecBuilder.() -> Unit) {
        nativeBuilder.addType(createAnnotation(className, builder))
    }

    fun originatingElement(originatingElement: Element) {
        nativeBuilder.addOriginatingElement(originatingElement)
    }
}

class TypeSpecBuilderImpl(override val nativeBuilder: TypeSpec.Builder) : TypeSpecBuilder