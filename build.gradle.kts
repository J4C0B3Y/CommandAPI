import kotlin.io.path.Path

plugins {
    id("java")
    id ("maven-publish")
    id("io.freefair.lombok") version "8.10"
    id("com.gradleup.shadow") version "8.3.0"
}

object Project {
    const val NAME = "CommandAPI"
    const val GROUP = "gg.voided"
    const val AUTHOR = "J4C0B3Y"
    const val VERSION = "1.0.0"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.freefair.lombok")
    apply(plugin = "com.gradleup.shadow")

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    dependencies {
        if (name != "core") {
            implementation(project(":core"))
        }
    }

    tasks {
        register<Copy>("copy") {
            from(named("shadowJar"))
            rename("(.*)-all.jar", "${Project.NAME}-${this@subprojects.name}-${Project.VERSION}.jar")
            into(Path(rootDir.path, "jars"))
        }
    }
}

tasks {
    named("classes") {
        depend(this)
    }

    register("shadow") {
        depend(this, "shadowJar")
    }

    register("delete") {
        file("jars").deleteRecursively()
    }

    register("copy") {
        depend(this)
    }

    named("clean") {
        depend(this)
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            artifactId = Project.NAME
            groupId = Project.GROUP
            version = Project.VERSION

            from(components["java"])
        }
    }
}

fun depend(task: Task, name: String = task.name) {
    task.dependsOn(subprojects.map { it.tasks.named(name) })
}