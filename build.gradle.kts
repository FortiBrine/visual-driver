plugins {
    `java-library`
}

allprojects {

    group = "me.fortibrine"
    version = "1.0"

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://repo.dmulloy2.net/repository/public/")
    }
}

subprojects {
    val libs = rootProject.libs

    apply {
        plugin("java-library")
    }

    tasks {
        withType<JavaCompile>().configureEach {
            options.encoding = "UTF-8"
            targetCompatibility = "1.8"
            sourceCompatibility = "1.8"
        }

    }

    dependencies {
        compileOnly(libs.lombok)
        annotationProcessor(libs.lombok)
    }
}