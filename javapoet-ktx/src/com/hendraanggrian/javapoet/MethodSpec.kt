package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.collections.AnnotationSpecCollection
import com.hendraanggrian.javapoet.collections.AnnotationSpecCollectionScope
import com.hendraanggrian.javapoet.collections.CodeBlockCollection
import com.hendraanggrian.javapoet.collections.JavadocCollection
import com.hendraanggrian.javapoet.collections.JavadocCollectionScope
import com.hendraanggrian.javapoet.collections.ParameterSpecCollection
import com.hendraanggrian.javapoet.collections.ParameterSpecCollectionScope
import com.hendraanggrian.javapoet.collections.TypeVariableNameCollection
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

/**
 * Builds new [MethodSpec] with name,
 * by populating newly created [MethodSpecBuilder] using provided [configuration].
 */
fun buildMethodSpec(name: String, configuration: MethodSpecBuilder.() -> Unit): MethodSpec =
    MethodSpecBuilder(MethodSpec.methodBuilder(name)).apply(configuration).build()

/**
 * Builds new constructor [MethodSpec],
 * by populating newly created [MethodSpecBuilder] using provided [configuration].
 */
fun buildConstructorMethodSpec(configuration: MethodSpecBuilder.() -> Unit): MethodSpec =
    MethodSpecBuilder(MethodSpec.constructorBuilder()).apply(configuration).build()

/** Modify existing [MethodSpec.Builder] using provided [configuration]. */
fun MethodSpec.Builder.edit(configuration: MethodSpecBuilder.() -> Unit): MethodSpec.Builder =
    MethodSpecBuilder(this).apply(configuration).nativeBuilder

/**
 * Wrapper of [MethodSpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@SpecMarker
class MethodSpecBuilder internal constructor(val nativeBuilder: MethodSpec.Builder) : CodeBlockCollection() {

    /** Modifiers of this method. */
    val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers

    /** Name of this method. */
    var name: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.setName(value)
        }

    /** Javadoc of this method. */
    val javadoc: JavadocCollection = object : JavadocCollection() {
        override fun append(format: String, vararg args: Any): Unit =
            format.internalFormat(args) { format2, args2 -> nativeBuilder.addJavadoc(format2, *args2) }

        override fun append(code: CodeBlock) {
            nativeBuilder.addJavadoc(code)
        }
    }

    /** Configures javadoc of this method. */
    fun javadoc(configuration: JavadocCollectionScope.() -> Unit): Unit =
        JavadocCollectionScope(javadoc).configuration()

    /** Annotations of this method. */
    val annotations: AnnotationSpecCollection = AnnotationSpecCollection(nativeBuilder.annotations)

    /** Configures annotations of this method. */
    fun annotations(configuration: AnnotationSpecCollectionScope.() -> Unit): Unit =
        AnnotationSpecCollectionScope(annotations).configuration()

    /** Add field modifiers. */
    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Type variables of this method. */
    val typeVariables: TypeVariableNameCollection = TypeVariableNameCollection(nativeBuilder.typeVariables)

    /** Configures type variables of this method. */
    fun typeVariables(configuration: TypeVariableNameCollection.() -> Unit): Unit = typeVariables.configuration()

    /** Add return line to type name. */
    var returns: TypeName
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.returns(value)
        }

    /** Add return line to [type]. */
    fun returns(type: Type) {
        nativeBuilder.returns(type)
    }

    /** Add return line to [type]. */
    fun returns(type: KClass<*>) {
        nativeBuilder.returns(type.java)
    }

    /** Add return line to [T]. */
    inline fun <reified T> returns(): Unit = returns(T::class)

    /** Parameters of this method. */
    val parameters: ParameterSpecCollection = ParameterSpecCollection(nativeBuilder.parameters)

    /** Configures parameters of this method. */
    fun parameters(configuration: ParameterSpecCollectionScope.() -> Unit): Unit =
        ParameterSpecCollectionScope(parameters).configuration()

    /** Add vararg keyword to array type parameter. */
    var isVarargs: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.varargs(value)
        }

    /** Add exceptions using [TypeName]. */
    fun addExceptions(types: Iterable<TypeName>) {
        nativeBuilder.addExceptions(types)
    }

    /** Add exception using [TypeName]. */
    fun addException(type: TypeName) {
        nativeBuilder.addException(type)
    }

    /** Add exception using [Type]. */
    fun addException(type: Type) {
        nativeBuilder.addException(type)
    }

    /** Add exception using [KClass]. */
    fun addException(type: KClass<*>) {
        nativeBuilder.addException(type.java)
    }

    /** Add exception using reified [T]. */
    inline fun <reified T> addException(): Unit = addException(T::class)

    override fun append(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.addCode(format2, *args2) }

    override fun appendNamed(format: String, args: Map<String, *>): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.addNamedCode(format2, args2) }

    override fun append(code: CodeBlock) {
        nativeBuilder.addCode(code)
    }

    /** Add comment like [String.format]. */
    fun addComment(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.addComment(format2, *args2) }

    /** Set default value like [String.format]. */
    fun defaultValue(format: String, vararg args: Any) {
        defaultValue = codeBlockOf(format, *args)
    }

    /** Set default value to code. */
    var defaultValue: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.defaultValue(value)
        }

    override fun beginControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.beginControlFlow(format2, *args2) }

    fun beginControlFlow(code: CodeBlock) {
        nativeBuilder.beginControlFlow(code)
    }

    override fun nextControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.nextControlFlow(format2, *args2) }

    fun nextControlFlow(code: CodeBlock) {
        nativeBuilder.nextControlFlow(code)
    }

    override fun endControlFlow() {
        nativeBuilder.endControlFlow()
    }

    override fun endControlFlow(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.endControlFlow(format2, *args2) }

    fun endControlFlow(code: CodeBlock) {
        nativeBuilder.endControlFlow(code)
    }

    override fun appendLine(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { format2, args2 -> nativeBuilder.addStatement(format2, *args2) }

    override fun appendLine(code: CodeBlock) {
        nativeBuilder.addStatement(code)
    }

    /** Returns native spec. */
    fun build(): MethodSpec = nativeBuilder.build()
}
