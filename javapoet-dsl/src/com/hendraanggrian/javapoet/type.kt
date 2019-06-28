@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationContainer
import com.hendraanggrian.javapoet.dsl.CodeContainer
import com.hendraanggrian.javapoet.dsl.FieldContainer
import com.hendraanggrian.javapoet.dsl.MethodContainer
import com.hendraanggrian.javapoet.internal.SpecBuilder
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeVariableName
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

/** Returns a class type builder with custom initialization block. */
fun buildClassTypeSpec(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns a class type builder with custom initialization block. */
fun buildClassTypeSpec(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.classBuilder(type))
        .also { builder?.invoke(it) }
        .build()

/** Returns an interface type builder with custom initialization block. */
fun buildInterfaceTypeSpec(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns an interface type builder with custom initialization block. */
fun buildInterfaceTypeSpec(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.interfaceBuilder(type))
        .also { builder?.invoke(it) }
        .build()

/** Returns an enum type builder with custom initialization block. */
fun buildEnumTypeSpec(name: String, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns an enum type builder with custom initialization block. */
fun buildEnumTypeSpec(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.enumBuilder(type))
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
fun buildAnnotationTypeSpec(type: ClassName, builder: (TypeSpecBuilder.() -> Unit)? = null): TypeSpec =
    TypeSpecBuilder(TypeSpec.annotationBuilder(type))
        .also { builder?.invoke(it) }
        .build()

@JavapoetDslMarker
class TypeSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: TypeSpec.Builder) :
    SpecBuilder<TypeSpec>(),
    JavadocedSpecBuilder,
    AnnotatedSpecBuilder,
    ModifieredSpecBuilder,
    TypeVariabledSpecBuilder,
    TypedSpecBuilder {

    override val javadocs: CodeContainer = object : CodeContainer() {
        override fun add(format: String, vararg args: Any) {
            nativeBuilder.addJavadoc(format, *args)
        }

        override fun add(block: CodeBlock) {
            nativeBuilder.addJavadoc(block)
        }
    }

    override val annotations: AnnotationContainer = object : AnnotationContainer() {
        override fun add(spec: AnnotationSpec) {
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

    fun superClass(type: KClass<*>) {
        nativeBuilder.superclass(type.java)
    }

    inline fun <reified T> superClass() =
        superClass(T::class)

    fun addSuperInterface(type: TypeName) {
        nativeBuilder.addSuperinterface(type)
    }

    fun addSuperInterface(type: KClass<*>) {
        nativeBuilder.addSuperinterface(type.java)
    }

    inline fun <reified T> addSuperInterface() =
        addSuperInterface(T::class)

    fun addEnumConstant(name: String) {
        nativeBuilder.addEnumConstant(name)
    }

    fun addEnumConstant(name: String, name2: String, builder: (TypeSpecBuilder.() -> Unit)? = null) {
        nativeBuilder.addEnumConstant(name, buildClassTypeSpec(name2, builder))
    }

    val fields: FieldContainer = object : FieldContainer() {
        override fun add(spec: FieldSpec) {
            nativeBuilder.addField(spec)
        }
    }

    fun addStaticBlock(block: CodeBlock) {
        nativeBuilder.addStaticBlock(block)
    }

    inline fun addStaticBlock(builder: CodeBlockBuilder.() -> Unit) =
        addStaticBlock(buildCodeBlock(builder))

    fun addInitializerBlock(block: CodeBlock) {
        nativeBuilder.addInitializerBlock(block)
    }

    inline fun addInitializerBlock(builder: CodeBlockBuilder.() -> Unit) =
        addInitializerBlock(buildCodeBlock(builder))

    val methods: MethodContainer = object : MethodContainer() {
        override fun add(spec: MethodSpec) {
            nativeBuilder.addMethod(spec)
        }
    }

    override fun addType(spec: TypeSpec) {
        nativeBuilder.addType(spec)
    }

    fun addOriginatingElement(originatingElement: Element) {
        nativeBuilder.addOriginatingElement(originatingElement)
    }

    override fun build(): TypeSpec = nativeBuilder.build()
}