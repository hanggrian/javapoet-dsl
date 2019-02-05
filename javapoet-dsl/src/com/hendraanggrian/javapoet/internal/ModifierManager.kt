package com.hendraanggrian.javapoet.internal

import javax.lang.model.element.Modifier

interface ModifierManager {

    fun modifiers(vararg modifiers: Modifier)
}