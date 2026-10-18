# AdMuse

**Open-source AI-powered promotional content generator for artists and small creative businesses.**

AdMuse helps creators and small businesses turn product or service information into promotional content for social media and other communication channels. It uses a locally running AI model to generate customizable promotional copy, helping users create content while maintaining control over the information provided.

## Features

- **AI-powered content generation:** Create promotional content based on a product or service, target audience, channel, style, language, and objective.
- **Customizable content:** Choose between professional, casual, creative, elegant, and fun styles.
- **Multilingual generation:** Generate content in Brazilian Portuguese, English, or Spanish.
- **Structured results:** Receive a title, promotional text, call to action, and optional hashtags.
- **Promotion history:** Save generated promotions and browse previously saved content.
- **Copy-ready output:** Copy the generated promotional content as a formatted block.
- **Watermarking:** Add a watermark to an artwork image using contact information or a logo.
- **Local AI integration:** Use Ollama to run the language model locally.

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Ollama
- HTML, CSS, and JavaScript
- Maven

## Requirements

Before running AdMuse, make sure you have:

- Java Development Kit (JDK) compatible with the project configuration.
- PostgreSQL installed and running.
- Ollama installed.
- The AI model used by the application available in Ollama.

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/ArthurHebertt/AdMuse.git
cd AdMuse
```

### 2. Configure the database

Create a PostgreSQL database named `admuse`.

The application is configured to connect to PostgreSQL using the following settings:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/admuse
spring.datasource.username=postgres
spring.datasource.password=${ADMUSE_DB_PASSWORD}
```

Set the `ADMUSE_DB_PASSWORD` environment variable to your PostgreSQL password before starting the application.

**Windows PowerShell:**

```powershell
$env:ADMUSE_DB_PASSWORD="your_password"
```

Replace `your_password` with your actual database password. This command sets the variable for the current PowerShell session.

### 3. Set up Ollama

Install Ollama and download the model used by the project:

```bash
ollama pull qwen3:4b-q4_K_M
```

Make sure Ollama is running and accessible at:

```text
http://localhost:11434
```

### 4. Run the application

Using the Maven Wrapper:

**Windows:**

```powershell
.\mvnw.cmd spring-boot:run
```

**Linux or macOS:**

```bash
./mvnw spring-boot:run
```

Once the application starts successfully, access the application using the local address configured by Spring Boot, typically:

```text
http://localhost:8080
```

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/promotions/generate` | Generate promotional content without saving it. |
| `POST` | `/api/promotions` | Save a promotion. |
| `GET` | `/api/promotions` | Retrieve saved promotions. |

## Design Principles

AdMuse is designed around a few practical principles:

- **No invented business information:** The AI should not fabricate prices, product details, or other information that the user has not provided.
- **User-controlled persistence:** Generated content is not saved automatically.
- **Clear failure handling:** AI failures and invalid responses should be reported clearly.
- **Simple workflow:** Generate, review, copy, and save promotional content when appropriate.
- **Local-first AI:** The language model runs through Ollama, rather than requiring a hosted AI API for generation.

## Project Status

AdMuse is an open-source project developed as part of a Hacktoberfest side project initiative. Its initial goal is to deliver a functional MVP for artists and small creative businesses.

## Contributing

Contributions, bug reports, and suggestions are welcome. If you would like to contribute, consider opening an issue to discuss a change before submitting a pull request.

---

**Built to help creative businesses promote their work with open-source AI.**
