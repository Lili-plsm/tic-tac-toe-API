plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    id("java")
}

group = "ru.site"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")


    implementation("org.postgresql:postgresql:42.7.3")

    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.4.0")
	implementation("org.mindrot:jbcrypt:0.4")

    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("com.jayway.jsonpath:json-path:2.9.0")
	testImplementation("com.h2database:h2")

}


tasks.test {
    useJUnitPlatform()
}