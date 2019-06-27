@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationContainer
import com.hendraanggrian.javapoet.dsl.CodeContainer
import com.hendraanggrian.javapoet.dsl.ParameterContainer
import com.hendraanggrian.javapoet.internal.SpecBuilder
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

/** Returns a method with custom initialization block. */
fun buildMethodSpec(name: String, builder: (MethodSpecBuilder.() -> Unit)? = null): MethodSpec =
    MethodSpecBuilder(MethodSpec.methodBuilder(name))
        .also { builder?.invoke(it) }
        .build()

/** Returns a constructor method with custom initialization block. */
fun buildConstructorMethodSpec(builder: (MethodSpecBuilder.() -> Unit)? = null): MethodSpec =
    MethodSpecBuilder(MethodSpec.constructorBuilder())
        .also { builder?.invoke(it) }
        .build()

@JavapoetDslMarker
class MethodSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: MethodSpec.Builder) :
    SpecBuilder<MethodSpec>(),
    JavadocedSpecBuilder,
    AnnotatedSpecBuilder,
    ModifieredSpecBuilder,
    TypeVariabledSpecBuilder,
    ControlFlowedSpecBuilder,
    CodedSpecBuilder {

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
        override fun add(spec: ParameterSpec) {
            nativeBuilder.addParameter(spec)
        }
    }

    var varargs: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.varargs(value)
        }

    fun addExceptions(names: Iterable<TypeName>) {
        nativeBuilder.addExceptions(names)
    }

    fun addException(name: TypeName) {
        nativeBuilder.addException(name)
    }

    fun addException(type: KClass<*>) {
        nativeBuilder.addException(type.java)
    }

    inline fun <reified T> addException() = addException(T::class)

    override val codes: CodeContainer = object : CodeContainer() {
        override fun add(format: String, vararg args: Any) {
            nativeBuilder.addCode(format, *args)
        }

        override fun add(block: CodeBlock) {
            nativeBuilder.addCode(block)
        }
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

    override fun beginControlFlow(format: String, vararg args: Any) {
        nativeBuilder.beginControlFlow(format, *args)
    }

    override fun nextControlFlow(format: String, vararg args: Any) {
        nativeBuilder.nextControlFlow(format, *args)
    }

    override fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    override fun endControlFlow(format: String, vararg args: Any) {
        nativeBuilder.endControlFlow(format, *args)
    }

    override val statements: CodeContainer = object : CodeContainer() {
        override fun add(format: String, vararg args: Any) {
            nativeBuilder.addStatement(format, *args)
        }

        override fun add(block: CodeBlock) {
            nativeBuilder.addStatement(block)
        }
    }

    override fun build(): MethodSpec = nativeBuilder.build()
}