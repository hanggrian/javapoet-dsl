import org.gradle.api.tasks.JavaExec
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.gradle.language.base.plugins.LifecycleBasePlugin

const val VERSION_KTLINT = "0.40.0"

fun org.gradle.api.artifacts.dsl.DependencyHandler.ktlint(module: String? = null) =
    when (module) {
        null -> "com.pinterest:ktlint:$VERSION_KTLINT"
        else -> "com.pinterest.ktlint:ktlint-$module:$VERSION_KTLINT"
    }

fun org.gradle.api.Project.ktlint() {
    val configuration = configurations.register("ktlint")
    dependencies {
        configuration {
            invoke(ktlint())
        }
    }
    tasks {
        val ktlint by registering(JavaExec::class) {
            group = LifecycleBasePlugin.VERIFICATION_GROUP
            inputs.dir("src")
            outputs.dir("src")
            description = "Check Kotlin code style."
            classpath(configuration.get())
            main = "com.pinterest.ktlint.Main"
            args("src/**/*.kt")
        }
        "check" {
            dependsOn(ktlint)
        }
        val ktlintFormat by registering(JavaExec::class) {
            group = "formatting"
            inputs.dir("src")
            outputs.dir("src")
            description = "Fix Kotlin code style deviations."
            classpath(configuration.get())
            main = "com.pinterest.ktlint.Main"
            args("-F", "src/**/*.kt")
        }
    }
}