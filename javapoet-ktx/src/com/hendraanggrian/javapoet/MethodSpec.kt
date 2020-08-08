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

/** Builds new [MethodSpec] with [name]. */
fun methodSpecOf(name: String): MethodSpec = MethodSpecBuilder(MethodSpec.methodBuilder(name)).build()

/** Builds new constructor [MethodSpec]. */
fun emptyConstructorMethodSpec(): MethodSpec = MethodSpecBuilder(MethodSpec.constructorBuilder()).build()

/**
 * Builds new [MethodSpec] with [name],
 * by populating newly created [MethodSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildMethodSpec(
    name: String,
    builderAction: MethodSpecBuilder.() -> Unit
): MethodSpec = MethodSpec.methodBuilder(name).build(builderAction)

/**
 * Builds new constructor [MethodSpec],
 * by populating newly created [MethodSpecBuilder] using provided [builderAction] and then building it.
 */
inline fun buildConstructorMethodSpec(
    builderAction: MethodSpecBuilder.() -> Unit
): MethodSpec = MethodSpec.constructorBuilder().build(builderAction)

/** Modify existing [MethodSpec.Builder] using provided [builderAction] and then building it. */
inline fun MethodSpec.Builder.build(
    builderAction: MethodSpecBuilder.() -> Unit
): MethodSpec = MethodSpecBuilder(this).apply(builderAction).build()

/** Wrapper of [MethodSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDslMarker
class MethodSpecBuilder(private val nativeBuilder: MethodSpec.Builder) : CodeBlockContainer() {

    /** Modifiers of this method. */
    val modifiers: MutableList<Modifier> get() = nativeBuilder.modifiers

    /** Javadoc of this method. */
    val javadoc: JavadocContainer = object : JavadocContainer() {
        override fun append(format: String, vararg args: Any): Unit =
            format.formatWith(args) { s, array -> nativeBuilder.addJavadoc(s, *array) }

        override fun append(code: CodeBlock) {
            nativeBuilder.addJavadoc(code)
        }
    }

    /** Configures javadoc of this method. */
    inline fun javadoc(configuration: JavadocContainerScope.() -> Unit) =
        JavadocContainerScope(javadoc).configuration()

    /** Annotations of this method. */
    val annotations: AnnotationSpecList = AnnotationSpecList(nativeBuilder.annotations)

    /** Configures annotations of this method. */
    inline fun annotations(configuration: AnnotationSpecListScope.() -> Unit) =
        AnnotationSpecListScope(annotations).configuration()

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
    fun returns(type: KClass<*>) = returns(type.java)

    /** Add return line to [T]. */
    inline fun <reified T> returns() = returns(T::class)

    /** Parameters of this method. */
    val parameters: ParameterSpecList = ParameterSpecList(nativeBuilder.parameters)

    /** Configures parameters of this method. */
    inline fun parameters(configuration: ParameterSpecListScope.() -> Unit) =
        ParameterSpecListScope(parameters).configuration()

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
    fun addException(type: Type) {
        nativeBuilder.addException(type)
    }

    /** Add exception throwing keyword. */
    fun addException(type: KClass<*>) = addException(type.java)

    /** Add exception throwing keyword with reified function. */
    inline fun <reified T> addException() = addException(T::class)

    /** Add named code. */
    fun addNamedCode(format: String, args: Map<String, *>): Unit =
        format.formatWith(args) { s, map -> nativeBuilder.addNamedCode(s, map) }

    override fun appendNamed(format: String, args: Map<String, *>): Unit =
        format.formatWith(args) { s, map -> nativeBuilder.addNamedCode(s, map) }

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
        buildCodeBlock(builderAction).also { defaultValue = it }

    /** Returns native spec. */
    fun build(): MethodSpec = nativeBuilder.build()
}
