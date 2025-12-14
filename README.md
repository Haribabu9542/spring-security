# spring-security

[![Build](https://img.shields.io/badge/build-pending-lightgrey)](https://github.com/Haribabu9542/spring-security/actions)
[![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Spring](https://img.shields.io/badge/Spring-Boot%202.x%20%7C%203.x-green)](https://spring.io/projects/spring-boot)

A collection of practical Spring Security examples and patterns for securing Spring Boot applications. This repository demonstrates common authentication and authorization approaches: form login, HTTP Basic, JWT, OAuth2 / OIDC client, method-level security, CSRF handling, password hashing, and testing security configurations.

This README provides quickstart instructions, configuration snippets, recommended secure defaults, and pointers for extending the examples.

Table of contents
- Project overview
- Features
- Prerequisites
- Quickstart
- Example configuration (application.yml)
- Common security snippets
  - Password encoder
  - SecurityFilterChain (Spring Security 5.7+)
  - In-memory user for demos
  - JWT basics
  - OAuth2 client (login with Google)
  - Method security
- Testing tips
- Docker (optional)
- Production considerations
- Contributing
- License & contact

Project overview
----------------
This repository is designed as a hands-on sandbox for developers learning to secure Spring Boot services. Each example is small and focused so you can pick the pattern you need and adapt it to real projects.

Features
--------
- Secure REST endpoints (role-based access)
- Form login and HTTP Basic examples
- JWT authentication example with signing and validation
- OAuth2/OIDC client example (third-party login)
- Method-level security with annotations
- CSRF configuration examples for web apps and APIs
- Password hashing with BCryptPasswordEncoder
- Unit and integration testing examples for security

Prerequisites
-------------
- Java 11+ (17 recommended)
- Maven or Gradle
- Docker & Docker Compose (optional, for running OAuth2 test providers or infrastructure)

Quickstart
----------
1. Clone the repository:
   ```
   git clone https://github.com/Haribabu9542/spring-security.git
   cd spring-security
   ```

2. Build:
   - Maven:
     ```
     mvn clean package
     ```
   - Gradle:
     ```
     ./gradlew build
     ```

3. Run:
   ```
   java -jar target/spring-security-demo-0.0.1-SNAPSHOT.jar
   ```
   The app starts on port 8080 by default.

Example configuration (application.yml)
--------------------------------------
A compact example showing common properties:

```yaml
server:
  port: 8080

spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: YOUR_GOOGLE_CLIENT_ID
            client-secret: YOUR_GOOGLE_CLIENT_SECRET
            scope:
              - openid
              - profile
              - email

app:
  jwt:
    secret: replace_with_secure_random_secret_32+
    expiration-ms: 900000
```

Common security snippets
-----------------------

Password encoder (recommended)
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12); // 12 is a reasonable work factor
}
```

SecurityFilterChain (Spring Security 5.7+, functional bean style)
```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable()) // only disable for stateless APIs; prefer CSRF protection for browser-based apps
      .authorizeHttpRequests(authorize -> authorize
          .requestMatchers("/public/**", "/actuator/health").permitAll()
          .requestMatchers("/admin/**").hasRole("ADMIN")
          .anyRequest().authenticated()
      )
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .httpBasic(Customizer.withDefaults()); // or .oauth2Login() / .formLogin()
    return http.build();
}
```

In-memory user (demo only)
```java
@Bean
public UserDetailsService users(PasswordEncoder passwordEncoder) {
    UserDetails user = User.builder()
        .username("user")
        .password(passwordEncoder.encode("password"))
        .roles("USER")
        .build();
    UserDetails admin = User.builder()
        .username("admin")
        .password(passwordEncoder.encode("admin"))
        .roles("ADMIN")
        .build();
    return new InMemoryUserDetailsManager(user, admin);
}
```

JWT basics (very small utility overview)
- Use a secure secret or preferably an asymmetric key pair (RS256) for production.
- Typical flow:
  1. Authenticate username/password
  2. Issue signed JWT containing subject/roles/iat/exp
  3. Client sends Authorization: Bearer <token>
  4. Filter validates signature and expiry, sets Authentication in SecurityContext

Minimal validation pseudo-code with jjwt:
```java
Claims claims = Jwts.parserBuilder()
    .setSigningKey(secretKey)
    .build()
    .parseClaimsJws(token)
    .getBody();
String username = claims.getSubject();
List<String> roles = claims.get("roles", List.class);
```

OAuth2 client (login with external provider)
- Add dependency: spring-boot-starter-oauth2-client
- Configure registration and rely on .oauth2Login() in SecurityFilterChain to enable the flow.

Method security
- Enable annotation-based method security:
```java
@Configuration
@EnableMethodSecurity // or @EnableGlobalMethodSecurity(prePostEnabled = true) on older versions
public class MethodSecurityConfig {}
```
- Protect service methods:
```java
@PreAuthorize("hasRole('ADMIN')")
public void adminOnlyOperation() { ... }
```

Testing tips
------------
- Use @WebMvcTest + @AutoConfigureMockMvc for controller tests and mock security components.
- Use @SpringBootTest with TestRestTemplate or WebTestClient for integration tests.
- Use WithMockUser or @WithUserDetails to simulate authenticated users in tests.
- For JWT-protected endpoints, create valid tokens for tests or mock the authentication filter.

Docker (optional)
-----------------
Example Dockerfile:
```dockerfile
FROM eclipse-temurin:17-jre
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

Production considerations
-------------------------
- Use HTTPS/TLS (terminate in front of app if behind load balancer).
- Prefer asymmetric signing (RSA/ECDSA) and store private keys securely (Vault, KMS).
- Use short-lived access tokens and optional refresh tokens with rotation.
- Protect secrets (do not store JWT secret in VCS).
- Use proper session management and CSRF protection for browser apps.
- Log security-relevant events (failed logins, token rejections) and monitor them.
- Reduce token claim cardinality to avoid large tokens.

Contributing
------------
Contributions, issues, and feature requests are welcome. Please open an issue or a pull request. Suggested workflow:
1. Fork the repo
2. Create a branch feature/your-change
3. Add tests for new functionality
4. Open a pull request describing your changes

License & Contact
-----------------
This project is provided under the MIT License — see LICENSE for details.

Maintainer: Haribabu9542
Repo: https://github.com/Haribabu9542/spring-security
