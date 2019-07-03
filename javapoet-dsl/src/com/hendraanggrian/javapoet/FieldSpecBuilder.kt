@file:JvmMultifileClass
@file:JvmName("SpecBuildersKt")

package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationContainer
import com.hendraanggrian.javapoet.dsl.JavadocContainer
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import javax.lang.model.element.Modifier

/** Configure [this] spec with DSL. */
inline operator fun FieldSpec.invoke(builder: FieldSpecBuilder.() -> Unit): FieldSpec =
    toBuilder()(builder)

/** Configure [this] builder with DSL. */
inline operator fun FieldSpec.Builder.invoke(builder: FieldSpecBuilder.() -> Unit): FieldSpec =
    FieldSpecBuilder(this).apply(builder).build()

/** Wrapper of [FieldSpec.Builder], providing DSL support as a replacement to Java builder. */
class FieldSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: FieldSpec.Builder) {

    val javadoc: JavadocContainer = object : JavadocContainer() {
        override fun add(format: String, vararg args: Any) {
            nativeBuilder.addJavadoc(format, *args)
        }

        override fun add(block: CodeBlock): CodeBlock = block.also { nativeBuilder.addJavadoc(it) }
    }

    val annotations: AnnotationContainer = object : AnnotationContainer() {
        override fun add(spec: AnnotationSpec): AnnotationSpec = spec.also { nativeBuilder.addAnnotation(it) }
    }

    fun addModifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    fun initializer(format: String, vararg args: Any) {
        nativeBuilder.initializer(format, *args)
    }

    inline var initializer: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) = initializer(value)

    fun initializer(block: CodeBlock) {
        nativeBuilder.initializer(block)
    }

    inline fun initializer(builder: CodeBlockBuilder.() -> Unit) = initializer(CodeBlock.builder()(builder))

    fun build(): FieldSpec = nativeBuilder.build()
}