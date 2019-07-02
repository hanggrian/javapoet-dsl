package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationContainer
import com.hendraanggrian.javapoet.dsl.CodeContainer
import com.hendraanggrian.javapoet.dsl.ParameterContainer
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

class MethodSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: MethodSpec.Builder) {

    @PublishedApi
    @Suppress("NOTHING_TO_INLINE")
    internal companion object {
        inline fun of(name: String, noinline builder: (MethodSpecBuilder.() -> Unit)? = null): MethodSpec =
            MethodSpecBuilder(MethodSpec.methodBuilder(name))
                .also { builder?.invoke(it) }
                .build()

        inline fun ofConstructor(noinline builder: (MethodSpecBuilder.() -> Unit)? = null): MethodSpec =
            MethodSpecBuilder(MethodSpec.constructorBuilder())
                .also { builder?.invoke(it) }
                .build()
    }

    val javadoc: CodeContainer = object : CodeContainer() {
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

    fun addTypeVariable(name: TypeVariableName) {
        nativeBuilder.addTypeVariable(name)
    }

    fun addTypeVariables(names: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(names)
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

    fun beginControlFlow(format: String, vararg args: Any) {
        nativeBuilder.beginControlFlow(format, *args)
    }

    fun nextControlFlow(format: String, vararg args: Any) {
        nativeBuilder.nextControlFlow(format, *args)
    }

    fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    fun endControlFlow(format: String, vararg args: Any) {
        nativeBuilder.endControlFlow(format, *args)
    }

    val statements: CodeContainer = object : CodeContainer() {
        override fun add(format: String, vararg args: Any) {
            nativeBuilder.addStatement(format, *args)
        }

        override fun add(block: CodeBlock): CodeBlock = block.also { nativeBuilder.addStatement(it) }
    }

    @PublishedApi
    internal fun build(): MethodSpec = nativeBuilder.build()
}