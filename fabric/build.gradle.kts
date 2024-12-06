plugins {
    alias(libs.plugins.fabric.loom)
}

repositories {
    maven("https://maven.parchmentmc.org")
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-1.16.5:2022.03.06@zip")
    })

    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)
    include(project(":common"))
    implementation(project(":common"))
}

tasks {
    java {
        withSourcesJar()
    }
}