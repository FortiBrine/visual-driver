dependencies {
    compileOnly(libs.spigot)
    compileOnly(project(":bukkit"))
}

tasks {
    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from (
            configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
        )
    }
}