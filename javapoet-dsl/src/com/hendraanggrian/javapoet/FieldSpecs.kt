package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.AnnotationManager
import com.hendraanggrian.javapoet.internal.JavadocManager
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type

fun buildFieldSpec(type: TypeName, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null): FieldSpecBuilder =
    FieldSpecBuilderImpl(FieldSpec.builder(type, name)).also { builder?.invoke(it) }

fun buildFieldSpec(type: Type, name: String, builder: (FieldSpecBuilder.() -> Unit)? = null): FieldSpecBuilder =
    FieldSpecBuilderImpl(FieldSpec.builder(type, name)).also { builder?.invoke(it) }

interface FieldSpecBuilder : JavadocManager, AnnotationManager, SpecBuilder<FieldSpec> {

    val nativeBuilder: FieldSpec.Builder

    override fun javadoc(format: String, vararg args: Any) {
        nativeBuilder.addJavadoc(format, *args)
    }

    override var javadoc: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.addJavadoc(value)
        }

    override fun annotation(type: ClassName, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(buildAnnotationSpec(type, builder).build())
    }

    override fun annotation(type: Class<*>, builder: (AnnotationSpecBuilder.() -> Unit)?) {
        nativeBuilder.addAnnotation(buildAnnotationSpec(type, builder).build())
    }

    fun initializer(format: String, vararg args: Any) {
        nativeBuilder.initializer(format, *args)
    }

    var initializer: CodeBlock
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.initializer(value)
        }

    override fun build(): FieldSpec = nativeBuilder.build()
}

internal class FieldSpecBuilderImpl(override val nativeBuilder: FieldSpec.Builder) : FieldSpecBuilder