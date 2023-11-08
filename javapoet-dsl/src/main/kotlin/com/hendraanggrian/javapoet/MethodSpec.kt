@file:OptIn(ExperimentalContracts::class)

package com.hendraanggrian.javapoet

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import java.lang.reflect.Type
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
 * Creates new constructor [MethodSpec] by populating newly created [MethodSpecBuilder] using
 * provided [configuration].
 */
inline fun buildConstructorMethodSpec(configuration: MethodSpecBuilder.() -> Unit): MethodSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return MethodSpecBuilder(MethodSpec.constructorBuilder()).apply(configuration).build()
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
    return buildMethodSpec(name, configuration).also(methods::add)
}

/**
 * Inserts new constructor [MethodSpec] by populating newly created [MethodSpecBuilder] using
 * provided [configuration].
 */
inline fun MethodSpecHandler.constructorMethod(
    configuration: MethodSpecBuilder.() -> Unit,
): MethodSpec {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return buildConstructorMethodSpec(configuration).also(methods::add)
}

/**
 * Property delegate for inserting new [MethodSpec] by populating newly created [MethodSpecBuilder]
 * using provided [configuration].
 */
fun MethodSpecHandler.methoding(
    configuration: MethodSpecBuilder.() -> Unit,
): SpecDelegateProvider<MethodSpec> {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    return SpecDelegateProvider { buildMethodSpec(it, configuration).also(methods::add) }
}

/** Invokes DSL to configure [MethodSpec] collection. */
fun MethodSpecHandler.methods(configuration: MethodSpecHandlerScope.() -> Unit) {
    contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
    MethodSpecHandlerScope(methods).configuration()
}

/** Responsible for managing a set of [MethodSpec] instances. */
sealed interface MethodSpecHandler {
    val methods: MutableList<MethodSpec>

    fun method(name: String): MethodSpec = MethodSpec.methodBuilder(name).build().also(methods::add)

    fun methoding(): SpecDelegateProvider<MethodSpec> =
        SpecDelegateProvider { MethodSpec.methodBuilder(it).build().also(methods::add) }

    fun constructorMethod(): MethodSpec = MethodSpec.constructorBuilder().build().also(methods::add)
}

/**
 * Receiver for the `methods` block providing an extended set of operators for the
 * configuration.
 */
@JavapoetSpecDsl
class MethodSpecHandlerScope internal constructor(
    actualList: MutableList<MethodSpec>,
) : MutableList<MethodSpec> by actualList {
    /** @see method */
    operator fun String.invoke(configuration: MethodSpecBuilder.() -> Unit): MethodSpec =
        buildMethodSpec(this, configuration).also(::add)
}

/** Wrapper of [MethodSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetSpecDsl
class MethodSpecBuilder(
    private val nativeBuilder: MethodSpec.Builder,
) : AnnotationSpecHandler, ParameterSpecHandler {
    override val annotations: MutableList<AnnotationSpec> = nativeBuilder.annotations
    val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers
    val typeVariables: MutableList<TypeVariableName> = nativeBuilder.typeVariables
    override val parameters: MutableList<ParameterSpec> = nativeBuilder.parameters

    var name: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.setName(value)
        }

    val javadoc: JavadocContainer =
        object : JavadocContainer {
            override fun append(format: String, vararg args: Any): Unit =
                format.internalFormat(args) { format2, args2 ->
                    nativeBuilder.addJavadoc(format2, *args2)
                }

            override fun append(code: CodeBlock) {
                nativeBuilder.addJavadoc(code)
            }
        }

    fun modifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    var returns: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR)
        get() = noGetter()
        set(value) {
            nativeBuilder.returns(value)
        }

    fun returns(type: Type) {
        nativeBuilder.returns(type)
    }

    fun returns(type: KClass<*>) {
        nativeBuilder.returns(type.java)
    }

    inline fun <reified T> returns(): Unit = returns(T::class)

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

    fun exception(exception: Type) {
        nativeBuilder.addException(exception)
    }

    fun exception(exception: KClass<*>) {
        nativeBuilder.addException(exception.java)
    }

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
