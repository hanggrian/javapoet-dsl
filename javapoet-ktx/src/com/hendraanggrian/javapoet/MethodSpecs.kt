package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationContainer
import com.hendraanggrian.javapoet.dsl.CodeCollection
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

/** Builds a new [MethodSpec] from [name]. */
fun buildMethod(name: String): MethodSpec =
    MethodSpecBuilder(MethodSpec.methodBuilder(name)).build()

/**
 * Builds a new [MethodSpec] from [name],
 * by populating newly created [MethodSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildMethod(name: String, builderAction: MethodSpecBuilder.() -> Unit): MethodSpec =
    MethodSpecBuilder(MethodSpec.methodBuilder(name)).apply(builderAction).build()

/** Builds a new constructor [MethodSpec]. */
fun buildConstructorMethod(): MethodSpec =
    MethodSpecBuilder(MethodSpec.constructorBuilder()).build()

/**
 * Builds a new constructor [MethodSpec],
 * by populating newly created [MethodSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildConstructorMethod(builderAction: MethodSpecBuilder.() -> Unit): MethodSpec =
    MethodSpecBuilder(MethodSpec.constructorBuilder()).apply(builderAction).build()

/** Wrapper of [MethodSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDslMarker
class MethodSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: MethodSpec.Builder) :
    CodeCollection() {

    /** Collection of javadoc, may be configured with Kotlin DSL. */
    val javadoc: JavadocContainer = object : JavadocContainer() {
        override fun append(format: String, vararg args: Any): Unit =
            format.formatWith(args) { s, array -> nativeBuilder.addJavadoc(s, *array) }

        override fun append(code: CodeBlock) {
            nativeBuilder.addJavadoc(code)
        }
    }

    /** Collection of annotations, may be configured with Kotlin DSL. */
    val annotations: AnnotationContainer = object : AnnotationContainer() {
        override fun add(spec: AnnotationSpec) {
            nativeBuilder.addAnnotation(spec)
        }
    }

    /** Add field modifiers. */
    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Add field modifiers. */
    fun addModifiers(modifiers: Iterable<Modifier>) {
        nativeBuilder.addModifiers(modifiers)
    }

    /** Add type variables. */
    fun addTypeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    /** Add type variables. */
    fun addTypeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    /** Add return line to type name. */
    var returns: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.returns(value)
        }

    /** Add return line to [type]. */
    fun returns(type: KClass<*>) {
        nativeBuilder.returns(type.java)
    }

    /** Add return line to [T]. */
    inline fun <reified T> returns() =
        returns(T::class)

    /** Collection of parameters, may be configured with Kotlin DSL. */
    val parameters: ParameterContainer = object : ParameterContainer() {
        override fun add(spec: ParameterSpec) {
            nativeBuilder.addParameter(spec)
        }
    }

    /** Add vararg keyword to array type parameter. */
    var varargs: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.varargs(value)
        }

    /** Add exceptions throwing keyword. */
    fun addExceptions(types: Iterable<TypeName>) {
        nativeBuilder.addExceptions(types)
    }

    /** Add exception throwing keyword. */
    fun addException(type: TypeName) {
        nativeBuilder.addException(type)
    }

    /** Add exception throwing keyword. */
    fun addException(type: KClass<*>) {
        nativeBuilder.addException(type.java)
    }

    /** Add exception throwing keyword with reified function. */
    inline fun <reified T> addException() =
        addException(T::class)

    override fun append(format: String, vararg args: Any): Unit =
        format.formatWith(args) { s, array -> nativeBuilder.addCode(s, *array) }

    override fun append(code: CodeBlock) {
        nativeBuilder.addCode(code)
    }

    override fun beginFlow(flow: String, vararg args: Any): Unit =
        flow.formatWith(args) { s, array -> nativeBuilder.beginControlFlow(s, *array) }

    override fun nextFlow(flow: String, vararg args: Any): Unit =
        flow.formatWith(args) { s, array -> nativeBuilder.nextControlFlow(s, *array) }

    override fun endFlow() {
        nativeBuilder.endControlFlow()
    }

    override fun endFlow(flow: String, vararg args: Any): Unit =
        flow.formatWith(args) { s, array -> nativeBuilder.endControlFlow(s, *array) }

    override fun appendln(format: String, vararg args: Any): Unit =
        format.formatWith(args) { s, array -> nativeBuilder.addStatement(s, *array) }

    override fun appendln(code: CodeBlock) {
        nativeBuilder.addStatement(code)
    }

    /** Add named code. */
    fun addNamedCode(format: String, args: Map<String, *>): Unit =
        format.formatWith(args) { s, map -> nativeBuilder.addNamedCode(s, map) }

    /** Add comment like [String.format]. */
    fun addComment(format: String, vararg args: Any): Unit =
        format.formatWith(args) { s, array -> nativeBuilder.addComment(s, *array) }

    /** Set default value like [String.format]. */
    fun defaultValue(format: String, vararg args: Any): Unit =
        format.formatWith(args) { s, array -> nativeBuilder.defaultValue(s, *array) }

    /** Set default value to code. */
    var defaultValue: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.defaultValue(value)
        }

    /** Set default value to code with custom initialization [builderAction]. */
    inline fun defaultValue(builderAction: CodeBlockBuilder.() -> Unit): CodeBlock =
        buildCode(builderAction).also { defaultValue = it }

    /** Returns native spec. */
    fun build(): MethodSpec =
        nativeBuilder.build()
}
