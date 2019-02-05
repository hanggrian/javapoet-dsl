package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.internal.AnnotationManager
import com.hendraanggrian.javapoet.internal.JavadocManager
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec

interface FieldSpecBuilder : JavadocManager, AnnotationManager {

    val nativeBuilder: FieldSpec.Builder

    override fun javadoc(format: String, vararg args: Any) {
        nativeBuilder.addJavadoc(format, *args)
    }

    override fun javadoc(codeBlock: CodeBlock) {
        nativeBuilder.addJavadoc(codeBlock)
    }

    override fun annotations(annotationSpecs: Iterable<AnnotationSpec>) {
        nativeBuilder.addAnnotations(annotationSpecs)
    }

    override fun annotation(annotationSpec: AnnotationSpec) {
        nativeBuilder.addAnnotation(annotationSpec)
    }

    override fun annotation(annotation: ClassName) {
        nativeBuilder.addAnnotation(annotation)
    }

    override fun annotation(annotation: Class<*>) {
        nativeBuilder.addAnnotation(annotation)
    }

    fun initializer(format: String, vararg args: Any) {
        nativeBuilder.initializer(format, *args)
    }

    fun initializer(codeBlock: CodeBlock) {
        nativeBuilder.initializer(codeBlock)
    }
}

class FieldSpecBuilderImpl(override val nativeBuilder: FieldSpec.Builder) : FieldSpecBuilder