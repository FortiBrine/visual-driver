plugins {
    alias(libs.plugins.fabric.loom)
}

repositories {
    maven("https://maven.parchmentmc.org")
}

dependencies {
    minecraft(libs.minecraft)
    mappings(libs.yarn)

    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)
    include(project(":common"))
    implementation(project(":common"))
}

tasks {
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        targetCompatibility = "21"
        sourceCompatibility = "21"
    }

    loom {
        accessWidenerPath = file("src/main/resources/visual-driver.accesswidener")
//        parameterMappings = true
    }
}