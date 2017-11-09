# SPDB

## Requirements
- Java 8
- Docker
- git, maven


## DB installation
1. Run postgres container:

        docker run --name postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres
    
    The application runs with default credentials.
    
## Testing
1. Run tests with:

        mvn test