package com.hendraanggrian.javapoet.internal

import com.hendraanggrian.javapoet.CodeBlockBuilder

interface JavadocManager {

    fun javadoc(format: String, vararg args: Any)

    fun javadoc(builder: CodeBlockBuilder.() -> Unit)
}