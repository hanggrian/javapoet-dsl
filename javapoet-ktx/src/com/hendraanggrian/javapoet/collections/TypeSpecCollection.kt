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

/** A [TypeSpecCollection] is responsible for managing a set of type instances. */
open class TypeSpecCollection internal constructor(actualList: MutableList<TypeSpec>) :
    MutableList<TypeSpec> by actualList {

    /** Add class type from name. */
    fun addClass(type: String): Boolean = add(TypeSpec.classBuilder(type).build())

    /** Add class type from name with custom initialization [configuration]. */
    fun addClass(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildClassTypeSpec(type, configuration))

    /** Add class type from [ClassName]. */
    fun addClass(type: ClassName): Boolean = add(TypeSpec.classBuilder(type).build())

    /** Add class type from [ClassName] with custom initialization [configuration]. */
    fun addClass(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildClassTypeSpec(type, configuration))

    /** Add interface type from name. */
    fun addInterface(type: String): Boolean = add(TypeSpec.interfaceBuilder(type).build())

    /** Add interface type from name with custom initialization [configuration]. */
    fun addInterface(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildInterfaceTypeSpec(type, configuration))

    /** Add interface type from [ClassName]. */
    fun addInterface(type: ClassName): Boolean = add(TypeSpec.interfaceBuilder(type).build())

    /** Add interface type from [ClassName] with custom initialization [configuration]. */
    fun addInterface(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildInterfaceTypeSpec(type, configuration))

    /** Add enum type from name. */
    fun addEnum(type: String): Boolean = add(TypeSpec.enumBuilder(type).build())

    /** Add enum type from name with custom initialization [configuration]. */
    fun addEnum(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildEnumTypeSpec(type, configuration))

    /** Add enum type from [ClassName]. */
    fun addEnum(type: ClassName): Boolean = add(TypeSpec.enumBuilder(type).build())

    /** Add enum type from [ClassName] with custom initialization [configuration]. */
    fun addEnum(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildEnumTypeSpec(type, configuration))

    /** Add anonymous type from formatting. */
    fun addAnonymous(format: String, vararg args: Any): Boolean =
        add(format.internalFormat(args) { format2, args2 -> TypeSpec.anonymousClassBuilder(format2, *args2).build() })

    /**
     * Add anonymous type from formatting with custom initialization [configuration].
     * Not inlining this function since `buildAnonymousType` is not inlined.
     */
    fun addAnonymous(format: String, vararg args: Any, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildAnonymousTypeSpec(format, *args, configuration = configuration))

    /** Add anonymous type from [CodeBlock]. */
    fun addAnonymous(code: CodeBlock): Boolean = add(TypeSpec.anonymousClassBuilder(code).build())

    /** Add anonymous type from [CodeBlock] with custom initialization [configuration]. */
    fun addAnonymous(code: CodeBlock, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildAnonymousTypeSpec(code, configuration = configuration))

    /** Add annotation type from name. */
    fun addAnnotation(type: String): Boolean = add(TypeSpec.annotationBuilder(type).build())

    /** Add annotation type from name with custom initialization [configuration]. */
    fun addAnnotation(type: String, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationTypeSpec(type, configuration))

    /** Add annotation type from [ClassName]. */
    fun addAnnotation(type: ClassName): Boolean = add(TypeSpec.annotationBuilder(type).build())

    /** Add annotation type from [ClassName] with custom initialization [configuration]. */
    fun addAnnotation(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): Boolean =
        add(buildAnnotationTypeSpec(type, configuration))
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@SpecMarker
class TypeSpecCollectionScope internal constructor(actualList: MutableList<TypeSpec>) : TypeSpecCollection(actualList)
