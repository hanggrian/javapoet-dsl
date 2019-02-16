package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.AnnotatedSpecBuilder
import com.hendraanggrian.javapoet.internal.JavadocSpecBuilder
import com.hendraanggrian.javapoet.internal.ModifieredSpecBuilder
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

/** Returns a field with custom initialization block. */
inline fun <reified T> buildFieldSpec(
    name: String,
    noinline builder: (FieldSpecBuilder.() -> Unit)? = null
): FieldSpec = buildFieldSpec(T::class.java, name, builder)

class FieldSpecBuilder(@PublishedApi internal val nativeBuilder: FieldSpec.Builder) : SpecBuilder<FieldSpec>(),
    JavadocSpecBuilder,
    AnnotatedSpecBuilder,
    ModifieredSpecBuilder {

    override fun javadoc(format: String, vararg args: Any) {
        nativeBuilder.addJavadoc(format, *args)
    }

    override fun javadoc(builder: CodeBlockBuilder.() -> Unit) {
        nativeBuilder.addJavadoc(buildCodeBlock(builder))
    }

    override fun annotation(type: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(buildAnnotationSpec(type, builder))
    }

    override fun annotation(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(buildAnnotationSpec(type, builder))
    }

    inline fun <reified T> annotation(noinline builder: (AnnotationSpecBuilder.() -> Unit)? = null) =
        annotation(T::class.java, builder)

    override var modifiers: Collection<Modifier>
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.addModifiers(*value.toTypedArray())
        }

    fun initializer(format: String, vararg args: Any) {
        nativeBuilder.initializer(format, *args)
    }

    fun initializer(block: CodeBlock) {
        nativeBuilder.initializer(block)
    }

    @Suppress("NOTHING_TO_INLINE")
    inline fun initializer(noinline builder: CodeBlockBuilder.() -> Unit) = buildCodeBlock(builder)

    override fun build(): FieldSpec = nativeBuilder.build()
}