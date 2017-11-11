# SPDB

## Requirements
- Java 8
- Docker
- Git, Maven
- Node (`v4.1.x`+) and NPM (`2.14.x`+)

## DB installation
1. Run postgres container:

        docker run --name postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres
    
    The application runs with default credentials.
    
## App
#### Build front-end files
*  `npm install` install all dependencies with npm
*  `npm run build` saves all static files in src/main/resources/static 

#### BE app
*  `mvn spring-boot:run` runs app

    
## Testing
1. Run tests with:

        mvn test