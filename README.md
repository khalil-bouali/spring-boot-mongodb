# Spring boot with MongoDB
This demo application uses spring boot 3 with mongoDB exposing an API
endpoint to fetch all student documents from a collection.

### Steps to follow:
1. Load maven dependencies.
2. Launch the services defined in the docker-compose file.
3. Access mongo express on the path "http://localhost:8081" using the username "rootuser" and password "rootpass".
4. Create a collection named "kbouali" or update the database name in the application.yml file and create a collection
with that name.
5. Launch the application.
6. Navigate to "http://localhost:8080/api/v1/students" to see the results.