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

object MethodSpecs {

    fun of(name: String): MethodSpec =
        MethodSpec.methodBuilder(name).build()

    inline fun of(name: String, builderAction: Builder.() -> Unit): MethodSpec =
        Builder(MethodSpec.methodBuilder(name)).apply(builderAction).build()

    fun constructor(): MethodSpec =
        MethodSpec.constructorBuilder().build()

    inline fun constructor(builderAction: Builder.() -> Unit): MethodSpec =
        Builder(MethodSpec.constructorBuilder()).apply(builderAction).build()

    /** Wrapper of [MethodSpec.Builder], providing DSL support as a replacement to Java builder. */
    @JavapoetDslMarker
    class Builder @PublishedApi internal constructor(private val nativeBuilder: MethodSpec.Builder) {

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

        inline fun <reified T> returns() =
            returns(T::class)

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

        inline fun <reified T> addException() =
            addException(T::class)

        val codes: CodeContainer = object : CodeContainer() {
            override fun append(format: String, vararg args: Any) {
                format(format, args) { s, array ->
                    nativeBuilder.addCode(s, *array)
                }
            }

            override fun append(block: CodeBlock): CodeBlock = block.also { nativeBuilder.addCode(it) }

            override fun beginControlFlow(flow: String, vararg args: Any) {
                format(flow, args) { s, array ->
                    nativeBuilder.beginControlFlow(s, *array)
                }
            }

            override fun nextControlFlow(flow: String, vararg args: Any) {
                format(flow, args) { s, array ->
                    nativeBuilder.nextControlFlow(s, *array)
                }
            }

            override fun endControlFlow() {
                nativeBuilder.endControlFlow()
            }

            override fun endControlFlow(flow: String, vararg args: Any) {
                format(flow, args) { s, array ->
                    nativeBuilder.endControlFlow(s, *array)
                }
            }

            override fun appendln(format: String, vararg args: Any) {
                format(format, args) { s, array ->
                    nativeBuilder.addStatement(s, *array)
                }
            }

            override fun appendln(block: CodeBlock): CodeBlock = block.also { nativeBuilder.addStatement(it) }
        }

        fun addNamedCode(format: String, args: Map<String, *>) {
            format(format, args) { s, map ->
                nativeBuilder.addNamedCode(s, map)
            }
        }

        fun addComment(format: String, vararg args: Any) {
            format(format, args) { s, array ->
                nativeBuilder.addComment(s, *array)
            }
        }

        fun defaultValue(format: String, vararg args: Any) {
            format(format, args) { s, array ->
                nativeBuilder.defaultValue(s, *array)
            }
        }

        inline var defaultValue: String
            @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
            set(value) = defaultValue(value)

        fun defaultValue(block: CodeBlock) {
            nativeBuilder.defaultValue(block)
        }

        inline fun defaultValue(builderAction: CodeBlocks.Builder.() -> Unit) =
            defaultValue(CodeBlocks.of(builderAction))

        fun build(): MethodSpec =
            nativeBuilder.build()
    }
}
