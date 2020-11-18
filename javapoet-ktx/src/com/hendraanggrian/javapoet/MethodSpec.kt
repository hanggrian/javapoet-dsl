package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.collections.AnnotationSpecList
import com.hendraanggrian.javapoet.collections.AnnotationSpecListScope
import com.hendraanggrian.javapoet.collections.CodeBlockContainer
import com.hendraanggrian.javapoet.collections.JavadocContainer
import com.hendraanggrian.javapoet.collections.JavadocContainerScope
import com.hendraanggrian.javapoet.collections.ParameterSpecList
import com.hendraanggrian.javapoet.collections.ParameterSpecListScope
import com.hendraanggrian.javapoet.collections.TypeVariableNameList
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

/** Builds new [MethodSpec] with name. */
fun methodSpecOf(name: String): MethodSpec = MethodSpecBuilder(MethodSpec.methodBuilder(name)).build()

/** Builds new constructor [MethodSpec]. */
fun emptyConstructorMethodSpec(): MethodSpec = MethodSpecBuilder(MethodSpec.constructorBuilder()).build()

/**
 * Builds new [MethodSpec] with name,
 * by populating newly created [MethodSpecBuilder] using provided [builderAction].
 */
inline fun buildMethodSpec(
    name: String,
    builderAction: MethodSpecBuilder.() -> Unit
): MethodSpec = MethodSpecBuilder(MethodSpec.methodBuilder(name)).apply(builderAction).build()

/**
 * Builds new constructor [MethodSpec],
 * by populating newly created [MethodSpecBuilder] using provided [builderAction].
 */
inline fun buildConstructorMethodSpec(
    builderAction: MethodSpecBuilder.() -> Unit
): MethodSpec = MethodSpecBuilder(MethodSpec.constructorBuilder()).apply(builderAction).build()

/** Modify existing [MethodSpec.Builder] using provided [builderAction]. */
inline fun MethodSpec.Builder.edit(
    builderAction: MethodSpecBuilder.() -> Unit
): MethodSpec.Builder = MethodSpecBuilder(this).apply(builderAction).nativeBuilder

/**
 * Wrapper of [MethodSpec.Builder], providing DSL support as a replacement to Java builder.
 * @param nativeBuilder source builder.
 */
@SpecDslMarker
class MethodSpecBuilder(val nativeBuilder: MethodSpec.Builder) : CodeBlockContainer() {

    /** Modifiers of this method. */
    val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers

    /** Name of this method. */
    var name: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.setName(value)
        }

    /** Javadoc of this method. */
    val javadoc: JavadocContainer = object : JavadocContainer() {
        override fun append(format: String, vararg args: Any): Unit =
            format.internalFormat(args) { s, array -> nativeBuilder.addJavadoc(s, *array) }

        override fun append(code: CodeBlock) {
            nativeBuilder.addJavadoc(code)
        }
    }

    /** Configures javadoc of this method. */
    inline fun javadoc(builderAction: JavadocContainerScope.() -> Unit): Unit =
        JavadocContainerScope(javadoc).builderAction()

    /** Annotations of this method. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configures annotations of this method. */
    inline fun annotations(builderAction: AnnotationSpecListScope.() -> Unit): Unit =
        AnnotationSpecListScope(annotations).builderAction()

    /** Add field modifiers. */
    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    /** Add method modifiers. */
    fun addModifiers(modifiers: Iterable<Modifier>) {
        nativeBuilder.addModifiers(modifiers)
    }

    /** Type variables of this method. */
    val typeVariables: TypeVariableNameList = TypeVariableNameList(nativeBuilder.typeVariables)

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
    val parameters: ParameterSpecList = ParameterSpecList(nativeBuilder.parameters)

    /** Configures parameters of this method. */
    inline fun parameters(builderAction: ParameterSpecListScope.() -> Unit): Unit =
        ParameterSpecListScope(parameters).builderAction()

    /** Add vararg keyword to array type parameter. */
    var isVarargs: Boolean
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
    fun addException(type: Type) {
        nativeBuilder.addException(type)
    }

    /** Add exception throwing keyword. */
    fun addException(type: KClass<*>) {
        nativeBuilder.addException(type.java)
    }

    /** Add exception throwing keyword with reified function. */
    inline fun <reified T> addException(): Unit = addException(T::class)

    override fun append(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { s, array -> nativeBuilder.addCode(s, *array) }

    override fun appendNamed(format: String, args: Map<String, *>): Unit =
        format.internalFormat(args) { s, map -> nativeBuilder.addNamedCode(s, map) }

    override fun append(code: CodeBlock) {
        nativeBuilder.addCode(code)
    }

    /** Add comment like [String.format]. */
    fun addComment(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { s, array -> nativeBuilder.addComment(s, *array) }

    /** Set default value like [String.format]. */
    fun defaultValue(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { s, array -> nativeBuilder.defaultValue(s, *array) }

    /** Set default value to code. */
    var defaultValue: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.defaultValue(value)
        }

    /** Set default value to code with custom initialization [builderAction]. */
    inline fun defaultValue(builderAction: CodeBlockBuilder.() -> Unit) {
        defaultValue = buildCodeBlock(builderAction)
    }

    override fun beginFlow(flow: String, vararg args: Any): Unit =
        flow.internalFormat(args) { s, array -> nativeBuilder.beginControlFlow(s, *array) }

    fun beginFlow(code: CodeBlock) {
        nativeBuilder.beginControlFlow(code)
    }

    inline fun beginFlow(builderAction: CodeBlockBuilder.() -> Unit): Unit =
        beginFlow(buildCodeBlock(builderAction))

    override fun nextFlow(flow: String, vararg args: Any): Unit =
        flow.internalFormat(args) { s, array -> nativeBuilder.nextControlFlow(s, *array) }

    fun nextFlow(code: CodeBlock) {
        nativeBuilder.nextControlFlow(code)
    }

    inline fun nextFlow(builderAction: CodeBlockBuilder.() -> Unit): Unit =
        nextFlow(buildCodeBlock(builderAction))

    override fun endFlow() {
        nativeBuilder.endControlFlow()
    }

    override fun endFlow(flow: String, vararg args: Any): Unit =
        flow.internalFormat(args) { s, array -> nativeBuilder.endControlFlow(s, *array) }

    fun endFlow(code: CodeBlock) {
        nativeBuilder.endControlFlow(code)
    }

    inline fun endFlow(builderAction: CodeBlockBuilder.() -> Unit): Unit =
        endFlow(buildCodeBlock(builderAction))

    override fun appendLine(format: String, vararg args: Any): Unit =
        format.internalFormat(args) { s, array -> nativeBuilder.addStatement(s, *array) }

    override fun appendLine(code: CodeBlock) {
        nativeBuilder.addStatement(code)
    }

    /** Returns native spec. */
    fun build(): MethodSpec = nativeBuilder.build()
}
