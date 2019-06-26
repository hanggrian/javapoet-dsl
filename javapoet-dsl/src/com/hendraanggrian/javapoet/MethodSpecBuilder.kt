@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.container.AnnotationContainer
import com.hendraanggrian.javapoet.container.JavadocContainer
import com.hendraanggrian.javapoet.internal.SpecBuilder
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier

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

@SpecBuilderDslMarker
class MethodSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: MethodSpec.Builder) :
    SpecBuilder<MethodSpec>(),
    JavadocedSpecBuilder,
    AnnotatedSpecBuilder,
    ModifieredSpecBuilder,
    TypeVariabledSpecBuilder,
    ControlFlowedSpecBuilder,
    CodedSpecBuilder {

    override val javadocs: JavadocContainer = object : JavadocContainer() {
        override fun plusAssign(block: CodeBlock) {
            nativeBuilder.addJavadoc(block)
        }

        override fun add(format: String, vararg args: Any) {
            nativeBuilder.addJavadoc(format, *args)
        }
    }

    override val annotations: AnnotationContainer = object : AnnotationContainer() {
        override fun plusAssign(spec: AnnotationSpec) {
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

    fun returns(type: Type) {
        nativeBuilder.returns(type)
    }

    inline fun <reified T> returns() = returns(T::class.java)

    fun parameter(spec: ParameterSpec) {
        nativeBuilder.addParameter(spec)
    }

    inline fun parameter(type: TypeName, name: String, noinline builder: (ParameterSpecBuilder.() -> Unit)? = null) =
        parameter(buildParameterSpec(type, name, builder))

    inline fun parameter(type: Type, name: String, noinline builder: (ParameterSpecBuilder.() -> Unit)? = null) =
        parameter(buildParameterSpec(type, name, builder))

    inline fun <reified T> parameter(name: String, noinline builder: (ParameterSpecBuilder.() -> Unit)? = null) =
        parameter(T::class.java, name, builder)

    var varargs: Boolean
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
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

    inline fun <reified T> exception() = exception(T::class.java)

    override fun addCode(format: String, vararg args: Any) {
        nativeBuilder.addCode(format, *args)
    }

    override fun addCode(block: CodeBlock) {
        nativeBuilder.addCode(block)
    }

    fun comment(format: String, vararg args: Any) {
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

    override fun addStatement(format: String, vararg args: Any) {
        nativeBuilder.addStatement(format, *args)
    }

    override fun addStatement(block: CodeBlock) {
        nativeBuilder.addStatement(block)
    }

    override fun build(): MethodSpec = nativeBuilder.build()
}