package com.hendraanggrian.javapoet.internal

import com.squareup.javapoet.CodeBlock

interface JavadocManager {

    fun javadoc(format: String, vararg args: Any)

    fun javadoc(codeBlock: CodeBlock)
}