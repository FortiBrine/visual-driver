plugins {
    alias(libs.plugins.forge.gradle)
}

minecraft {
    mappings("snapshot", "20171003-1.12")

    runs.create("client") {
        this.workingDirectory(project.file("run"))
    }
}

dependencies {
    minecraft(libs.minecraft.forge)
}

tasks {
    jar {
        archiveBaseName.set(rootProject.name)
        finalizedBy("reobfJar")
    }
}