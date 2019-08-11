@file:JvmMultifileClass
@file:JvmName("SpecBuildersKt")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationContainer
import com.hendraanggrian.javapoet.dsl.CodeContainer
import com.hendraanggrian.javapoet.dsl.JavadocContainer
import com.hendraanggrian.javapoet.dsl.ParameterContainer
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

/** Configure [this] spec with DSL. */
inline operator fun MethodSpec.invoke(builder: MethodSpecBuilder.() -> Unit): MethodSpec =
    toBuilder()(builder)

/** Configure [this] builder with DSL. */
inline operator fun MethodSpec.Builder.invoke(builder: MethodSpecBuilder.() -> Unit): MethodSpec =
    MethodSpecBuilder(this).apply(builder).build()

/** Wrapper of [MethodSpec.Builder], providing DSL support as a replacement to Java builder. */
class MethodSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: MethodSpec.Builder) {

    val javadoc: JavadocContainer = object : JavadocContainer() {
        override fun add(format: String, vararg args: Any) {
            nativeBuilder.addJavadoc(format, *args)
        }

        override fun add(block: CodeBlock): CodeBlock = block.also { nativeBuilder.addJavadoc(it) }
    }

    val annotations: AnnotationContainer = object : AnnotationContainer() {
        override fun add(spec: AnnotationSpec): AnnotationSpec = spec.also { nativeBuilder.addAnnotation(it) }
    }

    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    fun addModifiers(modifiers: Iterable<Modifier>) {
        nativeBuilder.addModifiers(modifiers)
    }

    fun addTypeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    fun addTypeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    var returns: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.returns(value)
        }

    fun returns(type: KClass<*>) {
        nativeBuilder.returns(type.java)
    }

    inline fun <reified T> returns() = returns(T::class)

    val parameters: ParameterContainer = object : ParameterContainer() {
        override fun add(spec: ParameterSpec): ParameterSpec = spec.also { nativeBuilder.addParameter(it) }
    }

    var varargs: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.varargs(value)
        }

    fun addExceptions(types: Iterable<TypeName>) {
        nativeBuilder.addExceptions(types)
    }

    fun addException(type: TypeName) {
        nativeBuilder.addException(type)
    }

    fun addException(type: KClass<*>) {
        nativeBuilder.addException(type.java)
    }

    inline fun <reified T> addException() = addException(T::class)

    val codes: CodeContainer = object : CodeContainer() {
        override fun add(format: String, vararg args: Any) {
            nativeBuilder.addCode(format, *args)
        }

        override fun add(block: CodeBlock): CodeBlock = block.also { nativeBuilder.addCode(it) }

        override fun beginControlFlow(flow: String, vararg args: Any) {
            nativeBuilder.beginControlFlow(flow, *args)
        }

        override fun nextControlFlow(flow: String, vararg args: Any) {
            nativeBuilder.nextControlFlow(flow, *args)
        }

        override fun endControlFlow() {
            nativeBuilder.endControlFlow()
        }

        override fun endControlFlow(flow: String, vararg args: Any) {
            nativeBuilder.endControlFlow(flow, *args)
        }

        override fun addStatement(format: String, vararg args: Any) {
            nativeBuilder.addStatement(format, *args)
        }

        override fun addStatement(block: CodeBlock): CodeBlock = block.also { nativeBuilder.addStatement(it) }
    }

    fun addNamedCode(format: String, args: Map<String, *>) {
        nativeBuilder.addNamedCode(format, args)
    }

    fun addComment(format: String, vararg args: Any) {
        nativeBuilder.addComment(format, *args)
    }

    fun defaultValue(format: String, vararg args: Any) {
        nativeBuilder.defaultValue(format, *args)
    }

    inline var defaultValue: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) = defaultValue(value)

    fun defaultValue(block: CodeBlock) {
        nativeBuilder.defaultValue(block)
    }

    inline fun defaultValue(builder: CodeBlockBuilder.() -> Unit) = defaultValue(CodeBlock.builder()(builder))

    fun build(): MethodSpec = nativeBuilder.build()
}
