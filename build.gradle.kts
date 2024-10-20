import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import kotlin.io.path.Path

plugins {
    id("java")
    id("maven-publish")
    id("io.freefair.lombok") version "8.10"
    id("com.gradleup.shadow") version "8.3.0"
}

object Project {
    const val NAME = "CommandAPI"
    const val GROUP = "net.j4c0b3y"
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
    apply(plugin = "maven-publish")

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        withSourcesJar()
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

        named<JavaCompile>("compileJava") {
            options.encoding = "UTF-8"
        }
    }

    publishing {
        repositories {
            maven {
                name = "j4c0b3y-public"
                url = uri("https://repo.j4c0b3y.net/public")
                credentials(PasswordCredentials::class)
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }

        publications {
            create<MavenPublication>("maven") {
                artifactId = this@subprojects.name
                groupId = "${Project.GROUP}.${Project.NAME}"
                version = Project.VERSION

                artifact(tasks.named<ShadowJar>("shadowJar").get().archiveFile)

                artifact(tasks.named<Jar>("sourcesJar").get().archiveFile) {
                    classifier = "sources"
                }
            }
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

    register("install") {
        depend(this, "publishReleasePublicationToMavenLocal")
    }
}

fun depend(task: Task, name: String = task.name) {
    task.dependsOn(subprojects.map { it.tasks.named(name) })
}