package com.hendraanggrian.javapoet

import javax.lang.model.element.Modifier

/** Public modifier. */
inline val public: Modifier get() = Modifier.PUBLIC

/** Protected modifier. */
inline val protected: Modifier get() = Modifier.PROTECTED

/** Private modifier. */
inline val private: Modifier get() = Modifier.PRIVATE

/** Abstract modifier. */
inline val abstract: Modifier get() = Modifier.ABSTRACT

/** Default modifier. */
inline val default: Modifier get() = Modifier.DEFAULT

/** Static modifier. */
inline val static: Modifier get() = Modifier.STATIC

/** Final modifier. */
inline val final: Modifier get() = Modifier.FINAL

/** Transient modifier. */
inline val transient: Modifier get() = Modifier.TRANSIENT

/** Volatile modifier. */
inline val volatile: Modifier get() = Modifier.VOLATILE

/** Synchronized modifier. */
inline val synchronized: Modifier get() = Modifier.SYNCHRONIZED

/** Native modifier. */
inline val native: Modifier get() = Modifier.NATIVE

/** Strictfp modifier. */
inline val strictfp: Modifier get() = Modifier.STRICTFP
