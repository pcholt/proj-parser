plugins {
    java
    kotlin("jvm") version "1.4.21"
    antlr
    `maven-publish`
}

group = "com.pcholt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("kot") {
            groupId = "com.pcholt"
            artifactId = "lprojectile"
            version = "1.0"

//            from(components["java"])
            from(components["kotlin"])
        }
    }
    repositories {
        maven {
            name = "pcholt"
            url = uri("sftp://pcholt.com/repository/$buildDir/repo/release")

            
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
