internal typealias Dependencies = org.gradle.api.artifacts.dsl.DependencyHandler
internal typealias Plugins = org.gradle.plugin.use.PluginDependenciesSpec

const val VERSION_KOTLIN = "1.6.10"
const val VERSION_DOKKA = "1.6.0"
const val VERSION_COROUTINES = "1.6.0"
val Dependencies.dokka get() = "org.jetbrains.dokka:dokka-gradle-plugin:$VERSION_DOKKA"
val Plugins.dokka get() = id("org.jetbrains.dokka")
fun Dependencies.kotlinx(module: String, version: String? = null) =
    "org.jetbrains.kotlinx:kotlinx-$module${version?.let { ":$it" }.orEmpty()}"

val Dependencies.`git-publish` get() = "org.ajoberstar:gradle-git-publish:3.0.0"
val Plugins.`git-publish` get() = id("org.ajoberstar.git-publish")

const val VERSION_JAVAPOET = "1.13.0"
fun Dependencies.squareup(module: String, version: String) = "com.squareup:$module:$version"

const val VERSION_TRUTH = "1.1.3"
fun Dependencies.google(repo: String? = null, module: String, version: String) =
    "com.google${repo?.let { ".$it" }.orEmpty()}:$module:$version"