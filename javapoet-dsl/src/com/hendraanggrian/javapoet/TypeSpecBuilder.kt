package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.Annotable
import com.hendraanggrian.javapoet.internal.Javadocable
import com.hendraanggrian.javapoet.internal.Modifierable
import com.hendraanggrian.javapoet.internal.Typable
import com.hendraanggrian.javapoet.internal.TypeVariable
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeVariableName
import java.lang.reflect.Type
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier

/** Returns a class builder with custom initialization block. */
fun buildTypeSpec(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns a class builder with custom initialization block. */
fun buildTypeSpec(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(className))
        .also { builder?.invoke(it) }
        .build()

/** Returns an interface builder with custom initialization block. */
fun buildInterfaceTypeSpec(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns an interface builder with custom initialization block. */
fun buildInterfaceTypeSpec(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(className))
        .also { builder?.invoke(it) }
        .build()

/** Returns an enum builder with custom initialization block. */
fun buildEnumTypeSpec(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns an enum builder with custom initialization block. */
fun buildEnumTypeSpec(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(className))
        .also { builder?.invoke(it) }
        .build()

/** Returns an anonymous class builder with custom initialization block. */
fun buildAnonymousTypeSpec(
    typeArgumentsFormat: String,
    vararg args: Any,
    builder: (TypeSpecBuilder.() -> Unit)? = null
): TypeSpec = TypeSpecBuilder(TypeSpec.anonymousClassBuilder(typeArgumentsFormat, *args))
    .also { builder?.invoke(it) }
    .build()

/** Returns an anonymous class builder with custom initialization block. */
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

class TypeSpecBuilder(@PublishedApi internal val nativeBuilder: TypeSpec.Builder) :
    Javadocable,
    Annotable,
    Modifierable,
    TypeVariable,
    Typable {

    override fun javadoc(format: String, vararg args: Any) {
        nativeBuilder.addJavadoc(format, *args)
    }

    override fun javadoc(block: CodeBlock) {
        nativeBuilder.addJavadoc(block)
    }

    override fun annotation(type: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(buildAnnotationSpec(type, builder))
    }

    override fun annotation(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(buildAnnotationSpec(type, builder))
    }

    inline fun <reified T> annotation(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        annotation(T::class.java, builder)

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

    fun superclass(type: Type) {
        nativeBuilder.superclass(type)
    }

    inline fun <reified T> superclass() = superclass(T::class.java)

    fun superiface(superiface: TypeName) {
        nativeBuilder.addSuperinterface(superiface)
    }

    fun superiface(type: Type) {
        nativeBuilder.addSuperinterface(type)
    }

    inline fun <reified T> superiface() = superiface(T::class.java)

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
        nativeBuilder.addStaticBlock(buildCodeBlock(builder))
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

    override fun type(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildTypeSpec(className, builder))
    }

    override fun interfaceType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildInterfaceTypeSpec(name, builder))
    }

    override fun interfaceType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildInterfaceTypeSpec(className, builder))
    }

    override fun enumType(name: String, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildEnumTypeSpec(name, builder))
    }

    override fun enumType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildEnumTypeSpec(className, builder))
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

    override fun annotationType(className: ClassName, builder: (TypeSpecBuilder.() -> Unit)?) {
        nativeBuilder.addType(buildAnnotationTypeSpec(className, builder))
    }

    fun originatingElement(originatingElement: Element) {
        nativeBuilder.addOriginatingElement(originatingElement)
    }

    fun build(): TypeSpec = nativeBuilder.build()
}