package com.hendraanggrian.javapoet

import com.hendraanggrian.javapoet.container.AnnotationContainer
import com.hendraanggrian.javapoet.internal.SpecBuilder
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier

/** Returns a parameter with custom initialization block. */
fun buildParameterSpec(
    type: TypeName,
    name: String,
    builder: (ParameterSpecBuilder.() -> Unit)? = null
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(type, name))
    .also { builder?.invoke(it) }
    .build()

/** Returns a parameter with custom initialization block. */
fun buildParameterSpec(
    type: Type,
    name: String,
    builder: (ParameterSpecBuilder.() -> Unit)? = null
): ParameterSpec = ParameterSpecBuilder(ParameterSpec.builder(type, name))
    .also { builder?.invoke(it) }
    .build()

/** Returns a parameter with custom initialization block. */
inline fun <reified T> buildParameterSpec(
    name: String,
    noinline builder: (ParameterSpecBuilder.() -> Unit)? = null
): ParameterSpec = buildParameterSpec(T::class.java, name, builder)

@JavapoetDslMarker
class ParameterSpecBuilder @PublishedApi internal constructor(private val nativeBuilder: ParameterSpec.Builder) :
    SpecBuilder<ParameterSpec>(),
    AnnotatedSpecBuilder,
    ModifieredSpecBuilder {

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

    override fun build(): ParameterSpec = nativeBuilder.build()
}