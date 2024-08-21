plugins {
    id("com.google.devtools.ksp") version "2.0.10-1.0.24"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.4.2"
    id("io.micronaut.test-resources") version "4.4.2"
    id("io.micronaut.aot") version "4.4.2"
    id("org.jetbrains.kotlinx.kover") version "0.8.3"
    kotlin("plugin.allopen") version "2.0.10"
    kotlin("plugin.jpa") version "2.0.10"
    kotlin("jvm") version "2.0.10"
}

version = "0.1"
group = "com.financesbox"

val kotlinVersion = project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

sourceSets {
    val integrationTest by creating {
        kotlin.srcDir("src/integration-test/kotlin")
        resources.srcDir("src/integration-test/resources")
        compileClasspath += sourceSets["main"].output + configurations["testCompileClasspath"]
        runtimeClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
    }
}

dependencies {
    ksp("io.micronaut.data:micronaut-data-processor")
    ksp("io.micronaut:micronaut-http-validation")
    ksp("io.micronaut.openapi:micronaut-openapi")
    ksp("io.micronaut.security:micronaut-security-annotations")
    ksp("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut:micronaut-aop")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
    implementation("io.micronaut.data:micronaut-data-tx-hibernate")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.liquibase:micronaut-liquibase")
    implementation("io.micronaut.rxjava3:micronaut-rxjava3")
    implementation("io.micronaut.rxjava3:micronaut-rxjava3-http-client")
    implementation("io.micronaut.security:micronaut-security-jwt")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.sql:micronaut-hibernate-jpa")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("ch.qos.logback:logback-classic")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("org.slf4j:jul-to-slf4j")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.mindrot:jbcrypt:0.4")
    compileOnly("io.micronaut.openapi:micronaut-openapi-annotations")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.yaml:snakeyaml")
    testImplementation("io.github.serpro69:kotlin-faker:1.14.0")
    testImplementation("org.awaitility:awaitility-kotlin:4.2.1")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:testcontainers")
    aotPlugins(platform("io.micronaut.platform:micronaut-platform:4.5.1"))
    aotPlugins("io.micronaut.security:micronaut-security-aot")
}

application {
    mainClass = "com.financesbox.shared.infrastructure.configuration.micronaut.api.ApplicationKt"
}

java {
    sourceCompatibility = JavaVersion.toVersion("21")
}

graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    testRuntime("kotest5")
    processing {
        incremental(true)
        annotations("com.financesbox.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
        configurationProperties.put("micronaut.security.jwks.enabled", "false")
    }
}

kover {
    reports {
        filters {
            excludes {
                classes("com.financesbox.shared.infrastructure.configuration.micronaut.api.ApplicationKt")
            }
        }
        verify {
            rule {
                minBound(75)
            }
        }
    }
    currentProject {
        sources {
            excludedSourceSets.addAll(listOf("integrationTest"))
        }
    }
}

fun isTestingTask(name: String) = name.contains("test", true)
        || name.contains("report", true)
        || name.contains("verify", true)
        || name.equals("build", true)
        || name.equals("check", true)

val isTesting = gradle.startParameter.taskNames.any(::isTestingTask)

if (isTesting) allOpen {
    annotation("jakarta.inject.Singleton")
    annotation("io.micronaut.context.annotation.Bean")
    annotation("io.micronaut.context.annotation.Factory")
    annotation("io.micronaut.aop.Around")
}

tasks.named<io.micronaut.gradle.docker.NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "21"
}

tasks.register<Test>("integrationTest") {
    description = "Runs the integration tests."
    group = "verification"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    shouldRunAfter("test")
    useJUnitPlatform()
}

tasks.named("check") {
    dependsOn("integrationTest")
}