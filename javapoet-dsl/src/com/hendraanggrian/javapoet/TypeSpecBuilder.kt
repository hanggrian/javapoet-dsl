@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.container.AnnotationContainer
import com.hendraanggrian.javapoet.container.FieldContainer
import com.hendraanggrian.javapoet.container.JavadocContainer
import com.hendraanggrian.javapoet.container.MethodContainer
import com.hendraanggrian.javapoet.dsl.FieldBuilder
import com.hendraanggrian.javapoet.dsl.MethodBuilder
import com.hendraanggrian.javapoet.internal.SpecBuilder
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeVariableName
import java.lang.reflect.Type
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier

/** Returns a class type builder with custom initialization block. */
fun buildClassTypeSpec(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns a class type builder with custom initialization block. */
fun buildClassTypeSpec(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns an interface type builder with custom initialization block. */
fun buildInterfaceTypeSpec(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns an interface type builder with custom initialization block. */
fun buildInterfaceTypeSpec(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns an enum type builder with custom initialization block. */
fun buildEnumTypeSpec(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns an enum type builder with custom initialization block. */
fun buildEnumTypeSpec(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns an anonymous type builder with custom initialization block. */
fun buildAnonymousTypeSpec(format: String, vararg args: Any, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.anonymousClassBuilder(format, *args))
        .also { builder?.invoke(it) }
        .build()

/** Returns an anonymous type builder with custom initialization block. */
fun buildAnonymousTypeSpec(block: CodeBlock, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.anonymousClassBuilder(block))
        .also { builder?.invoke(it) }
        .build()

/** Returns an annotation type builder with custom initialization block. */
fun buildAnnotationTypeSpec(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.annotationBuilder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns an annotation type builder with custom initialization block. */
fun buildAnnotationTypeSpec(name: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.annotationBuilder(name))
        .also { builder?.invoke(it) }
        .build()

@SpecBuilderDslMarker
class TypeSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: TypeSpec.Builder) :
    SpecBuilder<TypeSpec>(),
    JavadocedSpecBuilder,
    AnnotatedSpecBuilder,
    ModifieredSpecBuilder,
    TypeVariabledSpecBuilder,
    TypedSpecBuilder {

    override val javadocs: JavadocContainer = object : JavadocContainer() {
        override fun plusAssign(block: CodeBlock) {
            nativeBuilder.addJavadoc(block)
        }

        override fun add(format: String, vararg args: Any) {
            nativeBuilder.addJavadoc(format, *args)
        }
    }

    override val annotations: AnnotationContainer = object : AnnotationContainer() {
        override fun plusAssign(spec: AnnotationSpec) {
            nativeBuilder.addAnnotation(spec)
        }
    }

    override var modifiers: Collection<Modifier>
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.addModifiers(*value.toTypedArray())
        }

    override fun addTypeVariable(name: TypeVariableName) {
        nativeBuilder.addTypeVariable(name)
    }

    override fun addTypeVariables(names: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(names)
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

    fun superInterface(name: TypeName) {
        nativeBuilder.addSuperinterface(name)
    }

    fun superInterface(type: Type) {
        nativeBuilder.addSuperinterface(type)
    }

    inline fun <reified T> superInterface() = superInterface(T::class.java)

    fun enumConstant(name: String) {
        nativeBuilder.addEnumConstant(name)
    }

    fun enumConstant(name: String, name2: String, builder: (TypeSpecBuilder.() -> Unit)? = null) {
        nativeBuilder.addEnumConstant(name, buildClassTypeSpec(name2, builder))
    }

    val fields: FieldContainer = object : FieldContainer() {
        override fun plusAssign(spec: FieldSpec) {
            nativeBuilder.addField(spec)
        }
    }

    operator fun FieldContainer.invoke(builder: FieldBuilder.() -> Unit) {
        object : FieldBuilder() {
            override fun add(spec: FieldSpec) = plusAssign(spec)
        }.apply(builder)
    }

    fun staticBlock(block: CodeBlock) {
        nativeBuilder.addStaticBlock(block)
    }

    inline fun staticBlock(builder: CodeBlockBuilder.() -> Unit) = staticBlock(buildCodeBlock(builder))

    fun initializerBlock(block: CodeBlock) {
        nativeBuilder.addInitializerBlock(block)
    }

    inline fun initializerBlock(builder: CodeBlockBuilder.() -> Unit) = initializerBlock(buildCodeBlock(builder))

    val methods: MethodContainer = object : MethodContainer() {
        override fun plusAssign(spec: MethodSpec) {
            nativeBuilder.addMethod(spec)
        }
    }

    operator fun MethodContainer.invoke(builder: MethodBuilder.() -> Unit) {
        object : MethodBuilder() {
            override fun add(spec: MethodSpec) = plusAssign(spec)
        }.apply(builder)
    }

    override fun addType(spec: TypeSpec) {
        nativeBuilder.addType(spec)
    }

    fun originatingElement(originatingElement: Element) {
        nativeBuilder.addOriginatingElement(originatingElement)
    }

    override fun build(): TypeSpec = nativeBuilder.build()
}