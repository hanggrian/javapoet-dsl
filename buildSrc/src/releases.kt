const val RELEASE_USER = "hendraanggrian"
const val RELEASE_GROUP = "com.$RELEASE_USER"
const val RELEASE_ARTIFACT = "javapoet-ktx"
const val RELEASE_VERSION = "0.1"
const val RELEASE_DESC = "Write Java files in Kotlin DSL"
const val RELEASE_WEBSITE = "https://github.com/$RELEASE_USER/$RELEASE_ARTIFACT"

val BINTRAY_USER get() = System.getenv("BINTRAY_USER")
val BINTRAY_KEY get() = System.getenv("BINTRAY_KEY")
