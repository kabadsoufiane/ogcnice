# OGC Nice API – Guide d’Installation et Choix Techniques
## 1. Prérequis

- **Java 17** ou supérieur
- **Maven 3.8+**
- **PostgreSQL** (ou H2 pour les tests rapides)
- **Git** pour cloner le projet
## 2. Installation et Lancement

### a. Cloner le projet
git clone https://github.com/kabadsoufiane/ogcnice
cd ogcnice
### b. Configurer la base de données
- lancez PostgreSQL via Docker Compose :
    ```
    version: '3.8'
    services:
      postgres:
        image: postgres:15-alpine
        environment:
          POSTGRES_USER: nice
          POSTGRES_PASSWORD: nice
          POSTGRES_DB: nice_fc
        ports:
          - "5432:5432"
        volumes:
          - postgres_data:/var/lib/postgresql/data
    volumes:
      postgres_data:
    ```
### c. Accéder à la documentation et tester l’API

- Swagger UI : [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)
## 2. Choix Techniques

| Composant         | Choix               | Justification |
|-------------------|---------------------|---------------|
| **Framework**     | Spring Boot         | Démarrage rapide, gestion native des dépendances, intégration facile de JPA, validation, logs et documentation automatique. |
| **ORM**           | Hibernate (JPA)     | Persistance robuste, gestion des relations, pagination et tri natifs, support des transactions. |
| **Base de données** | PostgreSQL        | PostgreSQL pour la robustesse et la portabilité[1]. |
| **Mapping**       | MapStruct           | Génération automatique et performante de code de mapping DTO/Entity, favorise la séparation des couches et la maintenabilité. |
| **Validation**    | Bean Validation (Jakarta) | Sécurité et robustesse des données via annotations sur les DTOs, gestion centralisée des erreurs. |
| **Documentation** | Swagger/OpenAPI (springdoc-openapi) | Génération automatique et interactive de la documentation API, facilitant les tests et l’intégration. |
| **Tests**         | JUnit 5, Mockito, Testcontainers | Qualité logicielle, robustesse, tests unitaires et d’intégration réalistes (Testcontainers pour PostgreSQL). |
| **Logs**          | SLF4J + Logback     | Traçabilité des actions métier et facilité de debug. |

### Architecture

- **Séparation en couches** : Controller, Service, Repository, DTO, Mapper pour la maintenabilité et la testabilité.
- **Gestion centralisée des erreurs** : `@ControllerAdvice` pour des réponses d’erreur uniformes et adaptées à l’API REST.
- **Pagination et tri** : natifs grâce à Spring Data et au type `Pageable` dans les endpoints.
