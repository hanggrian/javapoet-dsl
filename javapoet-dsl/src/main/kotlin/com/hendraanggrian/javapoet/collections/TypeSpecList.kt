package com.hendraanggrian.javapoet.collections

import com.hendraanggrian.javapoet.JavapoetSpecDsl
import com.hendraanggrian.javapoet.SpecLoader
import com.hendraanggrian.javapoet.TypeSpecBuilder
import com.hendraanggrian.javapoet.buildAnnotationTypeSpec
import com.hendraanggrian.javapoet.buildAnonymousTypeSpec
import com.hendraanggrian.javapoet.buildClassTypeSpec
import com.hendraanggrian.javapoet.buildEnumTypeSpec
import com.hendraanggrian.javapoet.buildInterfaceTypeSpec
import com.hendraanggrian.javapoet.createSpecLoader
import com.hendraanggrian.javapoet.internalFormat
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.TypeSpec
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/** A [TypeSpecList] is responsible for managing a set of type instances. */
@OptIn(ExperimentalContracts::class)
open class TypeSpecList internal constructor(actualList: MutableList<TypeSpec>) :
    MutableList<TypeSpec> by actualList {

    /** Add class type from name. */
    fun addClass(type: String): TypeSpec = TypeSpec.classBuilder(type).build().also(::add)

    /** Add class type from name with custom initialization [configuration]. */
    fun addClass(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildClassTypeSpec(type, configuration).also(::add)
    }

    /** Add class type from [ClassName]. */
    fun addClass(type: ClassName): TypeSpec = TypeSpec.classBuilder(type).build().also(::add)

    /** Add class type from [ClassName] with custom initialization [configuration]. */
    fun addClass(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildClassTypeSpec(type, configuration).also(::add)
    }

    /** Add interface type from name. */
    fun addInterface(type: String): TypeSpec = TypeSpec.interfaceBuilder(type).build().also(::add)

    /** Add interface type from name with custom initialization [configuration]. */
    fun addInterface(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildInterfaceTypeSpec(type, configuration).also(::add)
    }

    /** Add interface type from [ClassName]. */
    fun addInterface(type: ClassName): TypeSpec =
        TypeSpec.interfaceBuilder(type).build().also(::add)

    /** Add interface type from [ClassName] with custom initialization [configuration]. */
    fun addInterface(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildInterfaceTypeSpec(type, configuration).also(::add)
    }

    /**
     * Add enum type from name with custom initialization [configuration].
     * When creating an enum class, [TypeSpec.enumConstants] has to be configured.
     */
    fun addEnum(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildEnumTypeSpec(type, configuration).also(::add)
    }

    /**
     * Add enum type from [ClassName] with custom initialization [configuration].
     * When creating an enum class, [TypeSpec.enumConstants] has to be configured.
     */
    fun addEnum(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildEnumTypeSpec(type, configuration).also(::add)
    }

    /** Add anonymous type from formatting. */
    fun addAnonymous(format: String, vararg args: Any): TypeSpec =
        format.internalFormat(args) { format2, args2 ->
            TypeSpec.anonymousClassBuilder(format2, *args2).build()
        }.also(::add)

    /**
     * Add anonymous type from formatting with custom initialization [configuration].
     * Not inlining this function since `buildAnonymousType` is not inlined.
     */
    fun addAnonymous(
        format: String,
        vararg args: Any,
        configuration: TypeSpecBuilder.() -> Unit
    ): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildAnonymousTypeSpec(format, *args, configuration = configuration).also(::add)
    }

    /** Add anonymous type from [CodeBlock]. */
    fun addAnonymous(code: CodeBlock): TypeSpec =
        TypeSpec.anonymousClassBuilder(code).build().also(::add)

    /** Add anonymous type from [CodeBlock] with custom initialization [configuration]. */
    fun addAnonymous(code: CodeBlock, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildAnonymousTypeSpec(code, configuration = configuration).also(::add)
    }

    /** Add annotation type from name. */
    fun addAnnotation(type: String): TypeSpec = TypeSpec.annotationBuilder(type).build().also(::add)

    /** Add annotation type from name with custom initialization [configuration]. */
    fun addAnnotation(type: String, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildAnnotationTypeSpec(type, configuration).also(::add)
    }

    /** Add annotation type from [ClassName]. */
    fun addAnnotation(type: ClassName): TypeSpec =
        TypeSpec.annotationBuilder(type).build().also(::add)

    /** Add annotation type from [ClassName] with custom initialization [configuration]. */
    fun addAnnotation(type: ClassName, configuration: TypeSpecBuilder.() -> Unit): TypeSpec {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return buildAnnotationTypeSpec(type, configuration).also(::add)
    }

    /** Property delegate for adding class type from name. */
    val addingClass: SpecLoader<TypeSpec> get() = createSpecLoader(::addClass)

    /** Property delegate for adding class type from name with initialization [configuration]. */
    fun addingClass(configuration: TypeSpecBuilder.() -> Unit): SpecLoader<TypeSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { addClass(it, configuration) }
    }

    /** Property delegate for adding interface type from name. */
    val addingInterface: SpecLoader<TypeSpec> get() = createSpecLoader(::addInterface)

    /**
     * Property delegate for adding interface type from name with
     * initialization [configuration].
     */
    fun addingInterface(configuration: TypeSpecBuilder.() -> Unit): SpecLoader<TypeSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { addInterface(it, configuration) }
    }

    /**
     * Property delegate for adding enum type from name with initialization [configuration].
     * When creating an enum class, [TypeSpec.enumConstants] has to be configured.
     */
    fun addingEnum(configuration: TypeSpecBuilder.() -> Unit): SpecLoader<TypeSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { addEnum(it, configuration) }
    }

    /** Property delegate for adding anonymous type from name. */
    val addingAnonymous: SpecLoader<TypeSpec> get() = createSpecLoader(::addAnonymous)

    /**
     * Property delegate for adding anonymous type from name with
     * initialization [configuration].
     */
    fun addingAnonymous(
        vararg args: Any,
        configuration: TypeSpecBuilder.() -> Unit
    ): SpecLoader<TypeSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { addAnonymous(it, *args, configuration = configuration) }
    }

    /** Property delegate for adding annotation type from name. */
    val addingAnnotation: SpecLoader<TypeSpec> get() = createSpecLoader(::addAnnotation)

    /**
     * Property delegate for adding annotation type from name with
     * initialization [configuration].
     */
    fun addingAnnotation(configuration: TypeSpecBuilder.() -> Unit): SpecLoader<TypeSpec> {
        contract { callsInPlace(configuration, InvocationKind.EXACTLY_ONCE) }
        return createSpecLoader { addAnnotation(it, configuration) }
    }
}

/** Receiver for the `types` block providing an extended set of operators for the configuration. */
@JavapoetSpecDsl
class TypeSpecListScope internal constructor(actualList: MutableList<TypeSpec>) :
    TypeSpecList(actualList)
