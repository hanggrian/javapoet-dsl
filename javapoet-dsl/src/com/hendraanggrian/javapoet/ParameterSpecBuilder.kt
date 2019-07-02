package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.dsl.AnnotationContainer
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

class ParameterSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: ParameterSpec.Builder) :
    SpecBuilder<ParameterSpec>(), ModifierAccessor {

    @PublishedApi
    @Suppress("NOTHING_TO_INLINE")
    internal companion object {
        inline fun of(
            type: TypeName,
            name: String,
            noinline builder: (ParameterSpecBuilder.() -> Unit)? = null
        ): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(type, name))
            .also { builder?.invoke(it) }
            .build()

        inline fun of(
            type: KClass<*>,
            name: String,
            noinline builder: (ParameterSpecBuilder.() -> Unit)? = null
        ): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(type.java, name))
            .also { builder?.invoke(it) }
            .build()
    }

    val annotations: AnnotationContainer = object : AnnotationContainer() {
        override fun add(spec: AnnotationSpec): AnnotationSpec = spec.also { nativeBuilder.addAnnotation(it) }
    }

    override var modifiers: Collection<Modifier>
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) {
            nativeBuilder.addModifiers(*value.toTypedArray())
        }

    override fun build(): ParameterSpec = nativeBuilder.build()
}