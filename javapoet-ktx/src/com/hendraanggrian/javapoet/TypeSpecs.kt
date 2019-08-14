@file:JvmMultifileClass
@file:JvmName("SpecBuildersKt")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationContainer
import com.hendraanggrian.javapoet.dsl.FieldContainer
import com.hendraanggrian.javapoet.dsl.JavadocContainer
import com.hendraanggrian.javapoet.dsl.MethodContainer
import com.hendraanggrian.javapoet.dsl.TypeContainer
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

object TypeSpecs {

    fun classOf(type: String): TypeSpec =
        TypeSpec.classBuilder(type).build()

    inline fun classOf(type: String, builderAction: Builder.() -> Unit): TypeSpec =
        Builder(TypeSpec.classBuilder(type)).apply(builderAction).build()

    fun classOf(type: ClassName): TypeSpec =
        TypeSpec.classBuilder(type).build()

    inline fun classOf(type: ClassName, builderAction: Builder.() -> Unit): TypeSpec =
        Builder(TypeSpec.classBuilder(type)).apply(builderAction).build()

    fun interfaceOf(type: String): TypeSpec =
        TypeSpec.interfaceBuilder(type).build()

    inline fun interfaceOf(type: String, builderAction: Builder.() -> Unit): TypeSpec =
        Builder(TypeSpec.interfaceBuilder(type)).apply(builderAction).build()

    fun interfaceOf(type: ClassName): TypeSpec =
        TypeSpec.interfaceBuilder(type).build()

    inline fun interfaceOf(type: ClassName, builderAction: Builder.() -> Unit): TypeSpec =
        Builder(TypeSpec.interfaceBuilder(type)).apply(builderAction).build()

    fun enumOf(type: String): TypeSpec =
        TypeSpec.enumBuilder(type).build()

    inline fun enumOf(type: String, builderAction: Builder.() -> Unit): TypeSpec =
        Builder(TypeSpec.enumBuilder(type)).apply(builderAction).build()

    fun enumOf(type: ClassName): TypeSpec =
        TypeSpec.enumBuilder(type).build()

    inline fun enumOf(type: ClassName, builderAction: Builder.() -> Unit): TypeSpec =
        Builder(TypeSpec.enumBuilder(type)).apply(builderAction).build()

    fun anonymousOf(format: String, vararg args: Any): TypeSpec =
        format(format, args) { s, array ->
            TypeSpec.anonymousClassBuilder(s, *array).build()
        }

    inline fun anonymousOf(format: String, vararg args: Any, builderAction: Builder.() -> Unit): TypeSpec =
        format(format, args) { s, array ->
            Builder(TypeSpec.anonymousClassBuilder(s, *array)).apply(builderAction).build()
        }

    fun anonymousOf(block: CodeBlock): TypeSpec =
        TypeSpec.anonymousClassBuilder(block).build()

    inline fun anonymousOf(block: CodeBlock, builderAction: Builder.() -> Unit): TypeSpec =
        Builder(TypeSpec.anonymousClassBuilder(block)).apply(builderAction).build()

    fun annotationOf(type: String): TypeSpec =
        TypeSpec.annotationBuilder(type).build()

    inline fun annotationOf(type: String, builderAction: Builder.() -> Unit): TypeSpec =
        Builder(TypeSpec.annotationBuilder(type)).apply(builderAction).build()

    fun annotationOf(type: ClassName): TypeSpec =
        TypeSpec.annotationBuilder(type).build()

    inline fun annotationOf(type: ClassName, builderAction: Builder.() -> Unit): TypeSpec =
        Builder(TypeSpec.annotationBuilder(type)).apply(builderAction).build()

    /** Wrapper of [TypeSpec.Builder], providing DSL support as a replacement to Java builder. */
    @JavapoetDslMarker
    class Builder @PublishedApi internal constructor(private val nativeBuilder: TypeSpec.Builder) {

        val javadoc: JavadocContainer = object : JavadocContainer() {
            override fun append(format: String, vararg args: Any) {
                format(format, args) { s, array ->
                    nativeBuilder.addJavadoc(s, *array)
                }
            }

            override fun append(block: CodeBlock): CodeBlock = block.also { nativeBuilder.addJavadoc(it) }
        }

        val annotations: AnnotationContainer = object : AnnotationContainer() {
            override fun add(spec: AnnotationSpec): AnnotationSpec = spec.also { nativeBuilder.addAnnotation(it) }
        }

        fun addModifiers(vararg modifiers: Modifier) {
            nativeBuilder.addModifiers(*modifiers)
        }

        fun addTypeVariable(typeVariable: TypeVariableName) {
            nativeBuilder.addTypeVariable(typeVariable)
        }

        fun addTypeVariables(typeVariables: Iterable<TypeVariableName>) {
            nativeBuilder.addTypeVariables(typeVariables)
        }

        var superClass: TypeName
            @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
            set(value) {
                nativeBuilder.superclass(value)
            }

        fun superClass(type: KClass<*>) {
            nativeBuilder.superclass(type.java)
        }

        inline fun <reified T> superClass() = superClass(T::class)

        fun addSuperInterface(type: TypeName) {
            nativeBuilder.addSuperinterface(type)
        }

        fun addSuperInterface(type: KClass<*>) {
            nativeBuilder.addSuperinterface(type.java)
        }

        inline fun <reified T> addSuperInterface() = addSuperInterface(T::class)

        fun addEnumConstant(name: String) {
            nativeBuilder.addEnumConstant(name)
        }

        fun addEnumConstant(name: String, spec: TypeSpec) {
            nativeBuilder.addEnumConstant(name, spec)
        }

        val fields: FieldContainer = object : FieldContainer() {
            override fun add(spec: FieldSpec): FieldSpec = spec.also { nativeBuilder.addField(it) }
        }

        fun addStaticBlock(block: CodeBlock) {
            nativeBuilder.addStaticBlock(block)
        }

        inline fun addStaticBlock(builderAction: CodeBlocks.Builder.() -> Unit) =
            addStaticBlock(CodeBlocks.of(builderAction))

        fun addInitializerBlock(block: CodeBlock) {
            nativeBuilder.addInitializerBlock(block)
        }

        inline fun addInitializerBlock(builderAction: CodeBlocks.Builder.() -> Unit) =
            addInitializerBlock(CodeBlocks.of(builderAction))

        val methods: MethodContainer = object : MethodContainer() {
            override fun add(spec: MethodSpec): MethodSpec = spec.also { nativeBuilder.addMethod(it) }
        }

        val types: TypeContainer = object : TypeContainer() {
            override fun add(spec: TypeSpec): TypeSpec = spec.also { nativeBuilder.addType(it) }
        }

        fun addOriginatingElement(originatingElement: Element) {
            nativeBuilder.addOriginatingElement(originatingElement)
        }

        fun build(): TypeSpec = nativeBuilder.build()
    }
}
