@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import javax.lang.model.element.Modifier
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/**
 * Creates new [MethodSpec] by populating newly created [MethodSpecBuilder] using provided
 * [configuration].
 */
inline fun buildMethodSpec(name: String, configuration: MethodSpecBuilder.() -> Unit): MethodSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return MethodSpecBuilder(MethodSpec.methodBuilder(name)).apply(configuration).build()
}

/**
 * Inserts new [MethodSpec] by populating newly created [MethodSpecBuilder] using provided
 * [configuration].
 */
inline fun MethodSpecHandler.method(
    name: String,
    configuration: MethodSpecBuilder.() -> Unit,
): MethodSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildMethodSpec(name, configuration).also(::method)
}

/**
 * Inserts new constructor [MethodSpec] by populating newly created [MethodSpecBuilder] using
 * provided [configuration].
 */
inline fun MethodSpecHandler.constructorMethod(
    configuration: MethodSpecBuilder.() -> Unit,
): MethodSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return MethodSpecBuilder(MethodSpec.constructorBuilder())
        .apply(configuration).build()
        .also(::method)
}

/**
 * Property delegate for inserting new [MethodSpec] by populating newly created [MethodSpecBuilder]
 * using provided [configuration].
 */
fun MethodSpecHandler.methoding(
    configuration: MethodSpecBuilder.() -> Unit,
): SpecDelegateProvider<MethodSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildMethodSpec(it, configuration).also(::method) }
}

/** Invokes DSL to configure [MethodSpec] collection. */
fun MethodSpecHandler.methods(configuration: MethodSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    MethodSpecHandlerScope.of(this).configuration()
}

/** Responsible for managing a set of [MethodSpec] instances. */
interface MethodSpecHandler {
    fun method(method: MethodSpec)

    fun method(name: String): MethodSpec = MethodSpec.methodBuilder(name).build().also(::method)

    fun methoding(): SpecDelegateProvider<MethodSpec> =
        SpecDelegateProvider { MethodSpec.methodBuilder(it).build().also(::method) }

    fun constructorMethod(): MethodSpec = MethodSpec.constructorBuilder().build().also(::method)
}

/**
 * Receiver for the `methods` block providing an extended set of operators for the
 * configuration.
 */
@JavapoetDsl
open class MethodSpecHandlerScope private constructor(
    handler: MethodSpecHandler,
) : MethodSpecHandler by handler {
    companion object {
        fun of(handler: MethodSpecHandler): MethodSpecHandlerScope = MethodSpecHandlerScope(handler)
    }

    /** @see method */
    operator fun String.invoke(configuration: MethodSpecBuilder.() -> Unit): MethodSpec =
        buildMethodSpec(this, configuration).also(::method)
}

/** Wrapper of [MethodSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDsl
class MethodSpecBuilder(
    private val nativeBuilder: MethodSpec.Builder,
) : AnnotationSpecHandler, ParameterSpecHandler {
    val annotations: MutableList<AnnotationSpec> get() = nativeBuilder.annotations
    val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers
    val typeVariables: MutableList<TypeVariableName> get() = nativeBuilder.typeVariables
    val parameters: MutableList<ParameterSpec> get() = nativeBuilder.parameters

    var name: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.setName(value)
        }

    fun javadoc(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.addJavadoc(format2, *args2)
        }

    fun javadoc(block: CodeBlock) {
        nativeBuilder.addJavadoc(block)
    }

    override fun annotation(annotation: AnnotationSpec) {
        nativeBuilder.addAnnotation(annotation)
    }

    fun modifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    fun modifiers(modifiers: Iterable<Modifier>) {
        nativeBuilder.addModifiers(modifiers)
    }

    fun typeVariables(typeVariables: Iterable<TypeVariableName>) {
        nativeBuilder.addTypeVariables(typeVariables)
    }

    fun typeVariable(typeVariable: TypeVariableName) {
        nativeBuilder.addTypeVariable(typeVariable)
    }

    var returns: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.returns(value)
        }

    fun returns(type: Class<*>) {
        nativeBuilder.returns(type)
    }

    fun returns(type: KClass<*>) {
        nativeBuilder.returns(type.java)
    }

    inline fun <reified T> returns(): Unit = returns(T::class)

    override fun parameter(parameter: ParameterSpec) {
        nativeBuilder.addParameter(parameter)
    }

    var isVarargs: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.varargs(value)
        }

    fun exceptions(exceptions: Iterable<TypeName>) {
        nativeBuilder.addExceptions(exceptions)
    }

    fun exception(exception: TypeName) {
        nativeBuilder.addException(exception)
    }

    fun exception(exception: Class<*>) {
        nativeBuilder.addException(exception)
    }

    fun exception(exception: KClass<*>) {
        nativeBuilder.addException(exception.java)
    }

    inline fun <reified T> exception(): Unit = exception(T::class)

    fun append(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.addCode(format2, *args2) }

    fun appendNamed(format: String, args: Map<String, *>): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.addNamedCode(format2, args2) }

    fun append(code: CodeBlock) {
        nativeBuilder.addCode(code)
    }

    fun comment(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.addComment(format2, *args2) }

    fun defaultValue(format: String, vararg args: Any) {
        defaultValue = codeBlockOf(format, *args)
    }

    var defaultValue: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.defaultValue(value)
        }

    fun beginControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.beginControlFlow(format2, *args2)
        }

    fun beginControlFlow(code: CodeBlock) {
        nativeBuilder.beginControlFlow(code)
    }

    fun nextControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.nextControlFlow(format2, *args2)
        }

    fun nextControlFlow(code: CodeBlock) {
        nativeBuilder.nextControlFlow(code)
    }

    fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    fun endControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.endControlFlow(format2, *args2)
        }

    fun endControlFlow(code: CodeBlock) {
        nativeBuilder.endControlFlow(code)
    }

    fun appendLine(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 ->
            nativeBuilder.addStatement(format2, *args2)
        }

    fun appendLine(code: CodeBlock) {
        nativeBuilder.addStatement(code)
    }

    fun build(): MethodSpec = nativeBuilder.build()
}
