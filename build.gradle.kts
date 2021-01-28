plugins {
    java
    kotlin("jvm") version "1.4.21"
    antlr
    `maven-publish`
}

group = "com.pcholt"
version = "0.01"

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("kot") {
            groupId = "com.pcholt"
            artifactId = "lprojectile"
            version = "0.02"

//            from(components["java"])
            from(components["kotlin"])
        }
    }
    repositories {
        maven {
            name = "pcholt"
            url = uri("https://pcholt.com/maven")
            credentials(PasswordCredentials::class)
        }
    }
}

//sourceSets {
//    main {
//        java.srcDirs.add(file("build/generated-src/antlr/main"))
//    }
//}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    antlr("org.antlr:antlr4:4.9")

}

tasks {
    compileKotlin {
        dependsOn("generateGrammarSource")
    }
    compileTestKotlin {
        dependsOn("generateTestGrammarSource")
    }
}
