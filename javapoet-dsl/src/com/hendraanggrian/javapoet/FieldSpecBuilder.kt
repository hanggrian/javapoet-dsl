package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationContainer
import com.hendraanggrian.javapoet.dsl.CodeContainer
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

@JavapoetDslMarker
class FieldSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: FieldSpec.Builder) :
    ModifieredSpecBuilder<FieldSpec>() {

    @PublishedApi
    @Suppress("NOTHING_TO_INLINE")
    internal companion object {

        inline fun of(
            type: TypeName,
            name: String,
            noinline builder: (FieldSpecBuilder.() -> Unit)? = null
        ): FieldSpec = FieldSpecBuilder(FieldSpec.builder(type, name))
            .also { builder?.invoke(it) }
            .build()

        inline fun of(
            type: KClass<*>,
            name: String,
            noinline builder: (FieldSpecBuilder.() -> Unit)? = null
        ): FieldSpec = FieldSpecBuilder(FieldSpec.builder(type.java, name))
            .also { builder?.invoke(it) }
            .build()
    }

    val javadocs: CodeContainer = object : CodeContainer() {
        override fun add(format: String, vararg args: Any) {
            nativeBuilder.addJavadoc(format, *args)
        }

        override fun add(block: CodeBlock): CodeBlock = block.also { nativeBuilder.addJavadoc(it) }
    }

    val annotations: AnnotationContainer = object : AnnotationContainer() {
        override fun add(spec: AnnotationSpec): AnnotationSpec = spec.also { nativeBuilder.addAnnotation(it) }
    }

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

    inline fun initializer(builder: CodeBlockBuilder.() -> Unit) = initializer(CodeBlockBuilder.of(builder))

    override fun build(): FieldSpec = nativeBuilder.build()
}