import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "net.darkdustry.go"
version = "1.0.0"

plugins {
    kotlin("jvm") version "1.7.10"
    application
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

repositories {
    mavenCentral()
    maven(url = "https://www.jitpack.io")
}

dependencies {
    val mindustryVersion = "138"

    compileOnly("com.github.Anuken.Arc:arc-core:$mindustryVersion")
    compileOnly("com.github.Anuken.Mindustry:core:$mindustryVersion")
    compileOnly("com.github.Anuken.Mindustry:server:$mindustryVersion")

    implementation("com.github.halibobor.leveldb-java:leveldb:1.23.1")
}

tasks.register<Copy>("copy") {
    if (File("test-server").exists()) {
        from("/home/lucin/IdeaProjects/${project.name}/build/libs/${project.name}-${version}.jar")
        into("/home/lucin/IdeaProjects/${project.name}/test-server/config/mods")
    }
}

tasks.jar {
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    finalizedBy(tasks.getByName("copy"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
