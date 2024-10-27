plugins {
    alias(libs.plugins.fabric.loom)
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())

    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)

//    implementation(project(":common"))

//    include(project(":common"))
}

tasks {
    java {
        withSourcesJar()
    }
}