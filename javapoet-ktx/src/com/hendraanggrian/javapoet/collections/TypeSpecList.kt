package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.SpecMarker
import com.hendraanggrian.javapoet.TypeSpecBuilder
import com.hendraanggrian.javapoet.buildAnnotationTypeSpec
import com.hendraanggrian.javapoet.buildAnonymousTypeSpec
import com.hendraanggrian.javapoet.buildClassTypeSpec
import com.hendraanggrian.javapoet.buildEnumTypeSpec
import com.hendraanggrian.javapoet.buildInterfaceTypeSpec
import com.hendraanggrian.javapoet.internalFormat
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeSpec

/** A [TypeSpecList] is responsible for managing a set of type instances. */
open class TypeSpecList internal constructor(actualList: MutableList<TypeSpec>) : MutableList<TypeSpec> by actualList {

    /** Add class type from name. */
    fun addClass(type: String): TypeSpec = TypeSpec.classBuilder(type).build().also(::add)

    /** Add class type from name with custom initialization [configuration]. */
    fun addClass(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildClassTypeSpec(type, configuration).also(::add)

    /** Add class type from [ClassName]. */
    fun addClass(type: ClassName): TypeSpec = TypeSpec.classBuilder(type).build().also(::add)

    /** Add class type from [ClassName] with custom initialization [configuration]. */
    fun addClass(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildClassTypeSpec(type, configuration).also(::add)

    /** Add interface type from name. */
    fun addInterface(type: String): TypeSpec = TypeSpec.interfaceBuilder(type).build().also(::add)

    /** Add interface type from name with custom initialization [configuration]. */
    fun addInterface(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildInterfaceTypeSpec(type, configuration).also(::add)

    /** Add interface type from [ClassName]. */
    fun addInterface(type: ClassName): TypeSpec = TypeSpec.interfaceBuilder(type).build().also(::add)

    /** Add interface type from [ClassName] with custom initialization [configuration]. */
    fun addInterface(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildInterfaceTypeSpec(type, configuration).also(::add)

    /** Add enum type from name. */
    fun addEnum(type: String): TypeSpec = TypeSpec.enumBuilder(type).build().also(::add)

    /** Add enum type from name with custom initialization [configuration]. */
    fun addEnum(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildEnumTypeSpec(type, configuration).also(::add)

    /** Add enum type from [ClassName]. */
    fun addEnum(type: ClassName): TypeSpec = TypeSpec.enumBuilder(type).build().also(::add)

    /** Add enum type from [ClassName] with custom initialization [configuration]. */
    fun addEnum(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildEnumTypeSpec(type, configuration).also(::add)

    /** Add anonymous type from formatting. */
    fun addAnonymous(format: String, vararg args: Any): TypeSpec =
        format.internalFormat(args) { format2, args2 -> TypeSpec.anonymousClassBuilder(format2, *args2).build() }
            .also(::add)

    /**
     * Add anonymous type from formatting with custom initialization [configuration].
     * Not inlining this function since `buildAnonymousType` is not inlined.
     */
    fun addAnonymous(format: String, vararg args: Any, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnonymousTypeSpec(format, *args, configuration = configuration).also(::add)

    /** Add anonymous type from [CodeBlock]. */
    fun addAnonymous(code: CodeBlock): TypeSpec = TypeSpec.anonymousClassBuilder(code).build().also(::add)

    /** Add anonymous type from [CodeBlock] with custom initialization [configuration]. */
    fun addAnonymous(code: CodeBlock, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnonymousTypeSpec(code, configuration = configuration).also(::add)

    /** Add annotation type from name. */
    fun addAnnotation(type: String): TypeSpec = TypeSpec.annotationBuilder(type).build().also(::add)

    /** Add annotation type from name with custom initialization [configuration]. */
    fun addAnnotation(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnnotationTypeSpec(type, configuration).also(::add)

    /** Add annotation type from [ClassName]. */
    fun addAnnotation(type: ClassName): TypeSpec = TypeSpec.annotationBuilder(type).build().also(::add)

    /** Add annotation type from [ClassName] with custom initialization [configuration]. */
    fun addAnnotation(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec =
        buildAnnotationTypeSpec(type, configuration).also(::add)
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@SpecMarker
class TypeSpecListScope internal constructor(actualList: MutableList<TypeSpec>) : TypeSpecList(actualList)
