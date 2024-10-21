dependencies {
    compileOnly(libs.spigot)
    compileOnly(libs.protocollib)
    compileOnly(libs.netty)
}

tasks {
    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from (
            configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
        )
    }
}