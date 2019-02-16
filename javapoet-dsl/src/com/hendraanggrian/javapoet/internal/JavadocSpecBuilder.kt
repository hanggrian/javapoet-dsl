package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.CodeBlockBuilder
import com.hendraanggrian.javapoet.NO_GETTER
import com.hendraanggrian.javapoet.noGetter

internal interface JavadocSpecBuilder {

    /** Add javadoc to this spec builder. */
    fun javadoc(format: String, vararg args: Any)

    /** Add javadoc to this spec builder. */
    var javadoc: String
        @Deprecated(NO_GETTER, level = DeprecationLevel.ERROR) get() = noGetter()
        set(value) = javadoc(value)

    /** Build javadoc and add it to this spec builder. */
    fun javadoc(builder: CodeBlockBuilder.() -> Unit)
}