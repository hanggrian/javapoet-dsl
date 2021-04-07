package com.hendraanggrian.javapoet.dsl

import com.hendraanggrian.javapoet.SpecDslMarker
import com.hendraanggrian.javapoet.TypeSpecBuilder
import com.hendraanggrian.javapoet.annotationTypeSpecOf
import com.hendraanggrian.javapoet.anonymousTypeSpecOf
import com.hendraanggrian.javapoet.buildAnnotationTypeSpec
import com.hendraanggrian.javapoet.buildAnonymousTypeSpec
import com.hendraanggrian.javapoet.buildClassTypeSpec
import com.hendraanggrian.javapoet.buildEnumTypeSpec
import com.hendraanggrian.javapoet.buildInterfaceTypeSpec
import com.hendraanggrian.javapoet.classTypeSpecOf
import com.hendraanggrian.javapoet.enumTypeSpecOf
import com.hendraanggrian.javapoet.interfaceTypeSpecOf
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeSpec

/** A [TypeSpecHandler] is responsible for managing a set of type instances. */
open class TypeSpecHandler(actualList: MutableList<TypeSpec>) : MutableList<TypeSpec> by actualList {

    /** Add class type from name. */
    fun addClass(type: String): Boolean = add(classTypeSpecOf(type))

    /** Add class type from name with custom initialization [configuration]. */
    fun addClass(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildClassTypeSpec(type, configuration))

    /** Add class type from [ClassName]. */
    fun addClass(type: ClassName): Boolean = add(classTypeSpecOf(type))

    /** Add class type from [ClassName] with custom initialization [configuration]. */
    fun addClass(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildClassTypeSpec(type, configuration))

    /** Add interface type from name. */
    fun addInterface(type: String): Boolean = add(interfaceTypeSpecOf(type))

    /** Add interface type from name with custom initialization [configuration]. */
    fun addInterface(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildInterfaceTypeSpec(type, configuration))

    /** Add interface type from [ClassName]. */
    fun addInterface(type: ClassName): Boolean = add(interfaceTypeSpecOf(type))

    /** Add interface type from [ClassName] with custom initialization [configuration]. */
    fun addInterface(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildInterfaceTypeSpec(type, configuration))

    /** Add enum type from name. */
    fun addEnum(type: String): Boolean = add(enumTypeSpecOf(type))

    /** Add enum type from name with custom initialization [configuration]. */
    fun addEnum(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildEnumTypeSpec(type, configuration))

    /** Add enum type from [ClassName]. */
    fun addEnum(type: ClassName): Boolean = add(enumTypeSpecOf(type))

    /** Add enum type from [ClassName] with custom initialization [configuration]. */
    fun addEnum(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildEnumTypeSpec(type, configuration))

    /** Add anonymous type from formatting. */
    fun addAnonymous(format: String, vararg args: Any): Boolean = add(anonymousTypeSpecOf(format, *args))

    /**
     * Add anonymous type from formatting with custom initialization [configuration].
     * Not inlining this function since `buildAnonymousType` is not inlined.
     */
    fun addAnonymous(format: String, vararg args: Any, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildAnonymousTypeSpec(format, *args, configuration = configuration))

    /** Add anonymous type from [CodeBlock]. */
    fun addAnonymous(code: CodeBlock): Boolean = add(anonymousTypeSpecOf(code))

    /** Add anonymous type from [CodeBlock] with custom initialization [configuration]. */
    fun addAnonymous(code: CodeBlock, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildAnonymousTypeSpec(code, configuration = configuration))

    /** Add annotation type from name. */
    fun addAnnotation(type: String): Boolean = add(annotationTypeSpecOf(type))

    /** Add annotation type from name with custom initialization [configuration]. */
    fun addAnnotation(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationTypeSpec(type, configuration))

    /** Add annotation type from [ClassName]. */
    fun addAnnotation(type: ClassName): Boolean = add(annotationTypeSpecOf(type))

    /** Add annotation type from [ClassName] with custom initialization [configuration]. */
    fun addAnnotation(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationTypeSpec(type, configuration))
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@SpecDslMarker
class TypeSpecHandlerScope(actualList: TypeSpecHandler) : TypeSpecHandler(actualList) {

    /** @see TypeSpecHandler.addClass */
    fun `class`(type: String): Boolean = addClass(type)

    /** @see TypeSpecHandler.addClass */
    fun `class`(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean = addClass(type, configuration)

    /** @see TypeSpecHandler.addClass */
    fun `class`(type: ClassName): Boolean = addClass(type)

    /** @see TypeSpecHandler.addClass */
    fun `class`(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean = addClass(type, configuration)

    /** @see TypeSpecHandler.addInterface */
    fun `interface`(type: String): Boolean = addInterface(type)

    /** @see TypeSpecHandler.addInterface */
    fun `interface`(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        addInterface(type, configuration)

    /** @see TypeSpecHandler.addInterface */
    fun `interface`(type: ClassName): Boolean = addInterface(type)

    /** @see TypeSpecHandler.addInterface */
    fun `interface`(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        addInterface(type, configuration)

    /** @see TypeSpecHandler.addEnum */
    fun enum(type: String): Boolean = addEnum(type)

    /** @see TypeSpecHandler.addEnum */
    fun enum(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean = addEnum(type, configuration)

    /** @see TypeSpecHandler.addEnum */
    fun enum(type: ClassName): Boolean = addEnum(type)

    /** @see TypeSpecHandler.addEnum */
    fun enum(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean = addEnum(type, configuration)

    /** @see TypeSpecHandler.addAnonymous */
    fun anonymous(format: String, vararg args: Any): Boolean = addAnonymous(format, *args)

    /** @see TypeSpecHandler.addAnonymous */
    fun anonymous(format: String, vararg args: Any, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        addAnonymous(format, *args, configuration = configuration)

    /** @see TypeSpecHandler.addAnonymous */
    fun anonymous(code: CodeBlock): Boolean = addAnonymous(code)

    /** @see TypeSpecHandler.addAnonymous */
    fun anonymous(code: CodeBlock, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        addAnonymous(code, configuration = configuration)

    /** @see TypeSpecHandler.addAnnotation */
    fun annotation(type: String): Boolean = add(annotationTypeSpecOf(type))

    /** @see TypeSpecHandler.addAnnotation */
    fun annotation(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationTypeSpec(type, configuration))

    /** @see TypeSpecHandler.addAnnotation */
    fun annotation(type: ClassName): Boolean = add(annotationTypeSpecOf(type))

    /** @see TypeSpecHandler.addAnnotation */
    fun annotation(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationTypeSpec(type, configuration))
}
