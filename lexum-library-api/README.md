# Lexum Library

Cette application Spring Boot permet de gérer le catalogue d’une librairie en ligne. Elle propose une API RESTful complète pour consulter, rechercher, ajouter, modifier et supprimer des livres.

## Fonctionnalités

- **Rechercher des livres** par titre ou auteur
- **Afficher les détails d’un livre**
- **Ajouter** un nouveau livre
- **Modifier** un livre existant
- **Supprimer** un livre


## Technologies utilisées

- Java 17
- Spring Boot 3+
- Spring Web / Spring Data JPA
- Hibernate + H2 (in-memory)
- Validation avec `jakarta.validation`
- JUnit 5 + Mockito + MockMvc


## Configuration

### `application.properties`

```properties
server.port=8080
spring.datasource.url=jdbc:h2:mem:librairie-db
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=lexum_admin
spring.datasource.password=lexum_password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```


## Endpoints REST

| Méthode | Endpoint              | Description                      |
|---------|-----------------------|----------------------------------|
| GET     | `/api/books`          | Lister les livres (paginés)      |
| GET     | `/api/books/{id}`     | Obtenir un livre par son ID      |
| POST    | `/api/books`          | Ajouter un nouveau livre         |
| PUT     | `/api/books/{id}`     | Modifier un livre existant       |
| DELETE  | `/api/books/{id}`     | Supprimer un livre               |

## Tests

- **Tests unitaires** avec `@ExtendWith(MockitoExtension.class)`
- **Tests d’intégration** via `@SpringBootTest` + `@AutoConfigureMockMvc`


## Accès à la console H2

```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:librairie-db
```

## Pistes d'amélioration

- **Documentation de l’API avec Swagger / OpenAPI**
    - Pour la génération des endpoints ou pour exposer automatiquement la documentation des endpoints.

- **Ajout d’un Linter / Formateur de code**
    - Utiliser [SonarLint](https://www.sonarsource.com/products/sonarlint/).

- **Tests plus poussés**
    - Ajouter des tests pour la validation (400), les cas limites, les erreurs 404, etc.
    - Utiliser Testcontainers pour faire des tests plus réalistes avec une vraie base PostgreSQL ou MySQL.

- **Mesure de couverture de tests**
    - Intégrer [JaCoCo](https://www.eclemma.org/jacoco/) pour analyser la couverture des tests unitaires.

- **Validation plus stricte**
    - Ajouter des règles personnalisées de validation

- **Sécurité de l’API**
    - Ajouter Spring Security pour sécuriser certaines routes.

- **Déploiement**
    - Dockeriser l'application pour faciliter le déploiement.
    - Ajouter un fichier `Dockerfile` + `docker-compose.yml`.
