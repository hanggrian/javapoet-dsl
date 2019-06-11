package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.SpecBuilder
import com.squareup.javapoet.AnnotationSpec
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

@SpecBuilderDslMarker
class FieldSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: FieldSpec.Builder) :
    SpecBuilder<FieldSpec>(),
    JavadocableSpecBuilder,
    AnnotableSpecBuilder,
    ModifierableSpecBuilder {

    override fun javadoc(format: String, vararg args: Any) {
        nativeBuilder.addJavadoc(format, *args)
    }

    override fun javadoc(block: CodeBlock) {
        nativeBuilder.addJavadoc(block)
    }

    override fun annotation(spec: AnnotationSpec) {
        nativeBuilder.addAnnotation(spec)
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

    inline var initializer: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) = initializer(value)

    fun initializer(block: CodeBlock) {
        nativeBuilder.initializer(block)
    }

    inline fun initializer(builder: CodeBlockBuilder.() -> Unit) = initializer(buildCodeBlock(builder))

    override fun build(): FieldSpec = nativeBuilder.build()
}