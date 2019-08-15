package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationContainer
import com.hendraanggrian.javapoet.dsl.JavadocContainer
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

fun TypeName.toFieldSpec(name: String, vararg modifiers: Modifier): FieldSpec =
    FieldSpec.builder(this, name, *modifiers).build()

inline fun buildFieldSpec(
    type: TypeName,
    name: String,
    vararg modifiers: Modifier,
    builderAction: FieldSpecBuilder.() -> Unit
): FieldSpec =
    FieldSpecBuilder(FieldSpec.builder(type, name, *modifiers)).apply(builderAction).build()

fun KClass<*>.toFieldSpec(name: String, vararg modifiers: Modifier): FieldSpec =
    FieldSpec.builder(java, name, *modifiers).build()

inline fun buildFieldSpec(
    type: KClass<*>,
    name: String,
    vararg modifiers: Modifier,
    builderAction: FieldSpecBuilder.() -> Unit
): FieldSpec =
    FieldSpecBuilder(FieldSpec.builder(type.java, name, *modifiers)).apply(builderAction).build()

inline fun <reified T> buildFieldSpec(
    name: String,
    vararg modifiers: Modifier,
    builderAction: FieldSpecBuilder.() -> Unit
): FieldSpec =
    buildFieldSpec(T::class, name, *modifiers, builderAction = builderAction)

/** Wrapper of [FieldSpec.Builder], providing DSL support as a replacement to Java builder. */
@JavapoetDslMarker
class FieldSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: FieldSpec.Builder) {

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

    fun initializer(format: String, vararg args: Any) {
        format(format, args) { s, array ->
            nativeBuilder.initializer(s, *array)
        }
    }

    inline var initializer: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) = initializer(value)

    fun initializer(block: CodeBlock) {
        nativeBuilder.initializer(block)
    }

    inline fun initializer(builderAction: CodeBlockBuilder.() -> Unit) =
        initializer(buildCodeBlock(builderAction))

    fun build(): FieldSpec =
        nativeBuilder.build()
}
