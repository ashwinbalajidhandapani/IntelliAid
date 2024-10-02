# IntelliAid

Run ChatGPT like chatbot in your machine!

## Current Features:
    - Chat with Ollama (version 0.3.6)
    - Create Profiles
    - Create Seperate Conversations
    - Model works in two modes
        - Casual - For conversations
        - knowledgeable - For questions

## Work in Progress
    - Model Fine tuning
    - Front-end features

## Installation Prerequesites:
- Java 8 or above
- Maven (Latest version)
- Ollama (Latest version) - up and running
- Docker Latest version - up and running

## Installation Steps:
- git clone https://github.com/ashwinbalajidhandapani/IntelliAid.git
- mvn clean package -DSkipTests (In current version)
- cd to project folder (Intelliaid-Backend folder), then java -jar target/intelliaid-backend-0.0.1-SNAPSHOT.jar
- cd to project folder (Intelliaid-frontend folder), npm start
    
## API Doc (Backend):
    [API Docs](https://github.com/ashwinbalajidhandapani/IntelliAid/blob/main/api-docs.json)

