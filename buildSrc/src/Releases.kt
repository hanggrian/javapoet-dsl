import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPom

const val DEVELOPER_ID = "hendraanggrian"
const val DEVELOPER_NAME = "Hendra Anggrian"
const val DEVELOPER_URL = "https://github.com/$DEVELOPER_ID/"

const val RELEASE_GROUP = "com.hendraanggrian"
const val RELEASE_ARTIFACT = "javapoet-dsl"
const val RELEASE_VERSION = "0.2-SNAPSHOT"
const val RELEASE_DESCRIPTION = "Replace JavaPoet builders with Kotlin DSL"
const val RELEASE_URL = "https://github.com/$DEVELOPER_ID/$RELEASE_ARTIFACT/"

fun Project.configurePom(pom: MavenPom) {
    pom.name.set(name)
    pom.description.set(RELEASE_DESCRIPTION)
    pom.url.set(RELEASE_URL)
    pom.licenses {
        license {
            name.set("The Apache License, Version 2.0")
            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
        }
    }
    pom.scm {
        connection.set("scm:git:https://github.com/$DEVELOPER_ID/codestyle-ktlint.git")
        developerConnection.set("scm:git:ssh://git@github.com/$DEVELOPER_ID/codestyle-ktlint.git")
        url.set(RELEASE_URL)
    }
    pom.developers {
        developer {
            id.set(DEVELOPER_ID)
            name.set(DEVELOPER_NAME)
            url.set(DEVELOPER_URL)
        }
    }
}
