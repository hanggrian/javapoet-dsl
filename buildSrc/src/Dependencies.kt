internal typealias Dependencies = org.gradle.api.artifacts.dsl.DependencyHandler

const val VERSION_JAVAPOET = "1.13.0"
fun Dependencies.squareup(module: String, version: String) = "com.squareup:$module:$version"

const val VERSION_TRUTH = "1.1.3"
fun Dependencies.google(module: String, version: String) = "com.google.$module:$module:$version"