# REST API NOTE APP
A simple backend service for managing notes. Has JUnit and IT tests.

## Features
- **RestTestClient**: for JUnit and Integration tests.
- **Virtual Threads**: uses virtual threads for enchanced performance. 
- **Testcontainers**: enables better Integration tests.

## Tech Stack
- **Java 25**
- **Spring Boot 4**
- **MongoDB**
- **MapStruct**
- **OpenAPI / Swagger**

### Prerequisites
- Docker
- Java 25

### Running with Docker
1. Clone the repository:
```bash
git clone https://github.com/MykiDevs/timer-app.git
cd timer-app
```
2. Copy .env.example to .env and customize environment variables if needed:
```bash
cp .env.example .env
```
3. **Start the application**
```bash
docker compose up --build
```
## API Reference
http://localhost:8080/swagger-ui/index.html`

## License
[MIT](https://github.com/MykiDevs/note-app/blob/main/LICENSE)
