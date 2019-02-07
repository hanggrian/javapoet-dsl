package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.Annotable
import com.hendraanggrian.javapoet.internal.Javadocable
import com.hendraanggrian.javapoet.internal.Modifierable
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier

/** Returns a field with custom initialization block. */
fun buildFieldSpec(type: TypeName, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null): FieldSpec =
    FieldSpecBuilder(FieldSpec.builder(type, name))
        .also { builder?.invoke(it) }
        .build()

/** Returns a field with custom initialization block. */
fun buildFieldSpec(type: Type, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null): FieldSpec =
    FieldSpecBuilder(FieldSpec.builder(type, name))
        .also { builder?.invoke(it) }
        .build()

class FieldSpecBuilder(@PublishedApi internal val nativeBuilder: FieldSpec.Builder) :
    Javadocable,
    Annotable,
    Modifierable {

    override fun javadoc(format: String, vararg args: Any) {
        nativeBuilder.addJavadoc(format, *args)
    }

    override fun javadoc(block: CodeBlock) {
        nativeBuilder.addJavadoc(block)
    }

    override fun annotation(type: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(buildAnnotationSpec(type, builder))
    }

    override fun annotation(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(buildAnnotationSpec(type, builder))
    }

    inline fun <reified T> annotation(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        annotation(T::class.java, builder)

    override fun modifiers(vararg modifiers: Modifier) {
        nativeBuilder.addModifiers(*modifiers)
    }

    fun initializer(format: String, vararg args: Any) {
        nativeBuilder.initializer(format, *args)
    }

    fun initializer(block: CodeBlock) {
        nativeBuilder.initializer(block)
    }

    @Suppress("NOTHING_TO_INLINE")
    inline fun initializer(noinline builder: CodeBlockBuilder.() -> Unit) = buildCodeBlock(builder)

    fun build(): FieldSpec = nativeBuilder.build()
}