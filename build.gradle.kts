import org.hidetake.groovy.ssh.connection.AllowAnyHosts
import org.hidetake.groovy.ssh.core.Remote
import org.hidetake.groovy.ssh.core.RunHandler
import org.hidetake.groovy.ssh.session.SessionHandler

plugins {
    `java-library`

    id("org.hidetake.ssh") version "2.11.2"
}

allprojects {

    group = "me.fortibrine"
    version = "1.0"

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://repo.dmulloy2.net/repository/public/")
        maven("https://repo.codemc.io/repository/maven-releases/")
        maven("https://maven.fabricmc.net/")
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


tasks.register("deployAllPlugins") {
    group = "deployment"
    description = "Deploy Bukkit and Test plugins to server"

    dependsOn(
        ":bukkit:build",
        ":test-plugin:build"
    )


    val minecraftServer = Remote(
        mapOf(
            "host" to (System.getenv("MC_SERVER_HOST") ?: ""),
            "port" to 22,
            "user" to (System.getenv("MC_SERVER_USER") ?: ""),
            "identity" to System.getenv("SSH_KEY_PATH")?.let { file(it) },
//            "password" to (System.getenv("MC_SERVER_PASSWORD") ?: ""),
            "knownHosts" to AllowAnyHosts.getInstance(),
        )

    )

    doLast {
        ssh.run(delegateClosureOf<RunHandler> {
            logger.lifecycle("⚡ Deploying plugins...")

            session(minecraftServer, delegateClosureOf<SessionHandler> {
                put(
                    hashMapOf(
                        "from" to project(":bukkit").buildDir.resolve("libs/bukkit-${project.version}.jar"),
                        "into" to (System.getenv("MC_PLUGINS_PATH") ?: "~/SERVER/plugins/")
                    )
                )

                put(
                    hashMapOf(
                        "from" to project(":test-plugin").buildDir.resolve("libs/test-plugin-${project.version}.jar"),
                        "into" to (System.getenv("MC_PLUGINS_PATH") ?: "~/SERVER/plugins/")
                    )
                )

            })
        })
    }
}