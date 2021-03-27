# User CRUD api service

### Configuration
Main Class:  com.spring.tutorial.user.UserCRUDApplication

VM Option:  -Dspring.profiles.active=local

### Build and run image
1. docker build -f docker/Dockerfile -t user-crud:latest .
2. docker run -d -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=development" --name user-crud user-crud:latest

### Service list
1. To manage user data

### Public URL for localhost
https://ngrok.com/download
