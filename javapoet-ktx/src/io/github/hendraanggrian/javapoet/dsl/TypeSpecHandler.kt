package io.github.hendraanggrian.javapoet.dsl

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeSpec
import io.github.hendraanggrian.javapoet.SpecDslMarker
import io.github.hendraanggrian.javapoet.TypeSpecBuilder
import io.github.hendraanggrian.javapoet.annotationTypeSpecOf
import io.github.hendraanggrian.javapoet.anonymousTypeSpecOf
import io.github.hendraanggrian.javapoet.buildAnnotationTypeSpec
import io.github.hendraanggrian.javapoet.buildAnonymousTypeSpec
import io.github.hendraanggrian.javapoet.buildClassTypeSpec
import io.github.hendraanggrian.javapoet.buildEnumTypeSpec
import io.github.hendraanggrian.javapoet.buildInterfaceTypeSpec
import io.github.hendraanggrian.javapoet.classTypeSpecOf
import io.github.hendraanggrian.javapoet.enumTypeSpecOf
import io.github.hendraanggrian.javapoet.interfaceTypeSpecOf

/** A [TypeSpecHandler] is responsible for managing a set of type instances. */
open class TypeSpecHandler internal constructor(actualList: MutableList<TypeSpec>) :
    MutableList<TypeSpec> by actualList {

    /** Add class type from name. */
    fun addClass(type: String): Boolean = add(classTypeSpecOf(type))

    /** Add class type from [ClassName]. */
    fun addClass(type: ClassName): Boolean = add(classTypeSpecOf(type))

    /** Add interface type from name. */
    fun addInterface(type: String): Boolean = add(interfaceTypeSpecOf(type))

    /** Add interface type from [ClassName]. */
    fun addInterface(type: ClassName): Boolean = add(interfaceTypeSpecOf(type))

    /** Add enum type from name. */
    fun addEnum(type: String): Boolean = add(enumTypeSpecOf(type))

    /** Add enum type from [ClassName]. */
    fun addEnum(type: ClassName): Boolean = add(enumTypeSpecOf(type))

    /** Add anonymous type from formatting. */
    fun addAnonymous(format: String, vararg args: Any): Boolean = add(anonymousTypeSpecOf(format, *args))

    /** Add anonymous type from [CodeBlock]. */
    fun addAnonymous(code: CodeBlock): Boolean = add(anonymousTypeSpecOf(code))

    /** Add annotation type from name. */
    fun addAnnotation(type: String): Boolean = add(annotationTypeSpecOf(type))

    /** Add annotation type from [ClassName]. */
    fun addAnnotation(type: ClassName): Boolean = add(annotationTypeSpecOf(type))

    /** Add class type from name with custom initialization [configuration]. */
    inline fun addClass(
        type: String,
        configuration: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildClassTypeSpec(type, configuration))

    /** Add class type from [ClassName] with custom initialization [configuration]. */
    inline fun addClass(
        type: ClassName,
        configuration: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildClassTypeSpec(type, configuration))

    /** Add interface type from name with custom initialization [configuration]. */
    inline fun addInterface(
        type: String,
        configuration: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildInterfaceTypeSpec(type, configuration))

    /** Add interface type from [ClassName] with custom initialization [configuration]. */
    inline fun addInterface(
        type: ClassName,
        configuration: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildInterfaceTypeSpec(type, configuration))

    /** Add enum type from name with custom initialization [configuration]. */
    inline fun addEnum(
        type: String,
        configuration: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildEnumTypeSpec(type, configuration))

    /** Add enum type from [ClassName] with custom initialization [configuration]. */
    inline fun addEnum(
        type: ClassName,
        configuration: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildEnumTypeSpec(type, configuration))

    /**
     * Add anonymous type from formatting with custom initialization [configuration].
     * Not inlining this function since `buildAnonymousType` is not inlined.
     */
    fun addAnonymous(
        format: String,
        vararg args: Any,
        configuration: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildAnonymousTypeSpec(format, *args, configuration = configuration))

    /** Add anonymous type from [CodeBlock] with custom initialization [configuration]. */
    inline fun addAnonymous(
        code: CodeBlock,
        configuration: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildAnonymousTypeSpec(code, configuration = configuration))

    /** Add annotation type from name with custom initialization [configuration]. */
    inline fun addAnnotation(
        type: String,
        configuration: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildAnnotationTypeSpec(type, configuration))

    /** Add annotation type from [ClassName] with custom initialization [configuration]. */
    inline fun addAnnotation(
        type: ClassName,
        configuration: TypeSpecBuilder.() -> Unit
    ): Boolean = add(buildAnnotationTypeSpec(type, configuration))
}

/** Receiver for the `types` function type providing an extended set of operators for the configuration. */
@SpecDslMarker
class TypeSpecHandlerScope(actualList: TypeSpecHandler) : TypeSpecHandler(actualList) {

    /** Convenient method to add class with receiver type. */
    inline operator fun String.invoke(
        configuration: TypeSpecBuilder.() -> Unit
    ): Boolean = addClass(this, configuration)

    /** Convenient method to add class with receiver type. */
    inline operator fun ClassName.invoke(
        configuration: TypeSpecBuilder.() -> Unit
    ): Boolean = addClass(this, configuration)
}
