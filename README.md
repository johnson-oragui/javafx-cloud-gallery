### JavaFX Cloud Gallery

A responsive desktop photo gallery application built with JavaFX, leveraging cloud storage (Cloudinary) for images and a PostgreSQL database for user and metadata management.

## Features

* User Authentication (Login & Registration)
* Secure Password Handling (using jbcrypt)
* Browse Photo Gallery
* Upload Images to Cloud Storage (Cloudinary)
* Responsive User Interface (adapts to different window sizes)
* Database Integration (PostgreSQL with HikariCP connection pooling)
* Environment variable loading for configuration (`.env`)

## Technologies Used

* **JavaFX:** For building the desktop user interface.
* **Gradle:** Build automation tool.
* **PostgreSQL:** Relational database for storing user information and image metadata.
* **HikariCP:** High-performance JDBC connection pool for database access.
* **jbcrypt:** For securely hashing user passwords.
* **dotenv-java:** For loading configuration from a `.env` file.
* **Cloudinary HTTP 4.4:** API client for interacting with the Cloudinary cloud storage service.
* **FXML:** Declarative UI definition for JavaFX.
* **CSS:** Styling the JavaFX UI.
* **Java 17:** The primary programming language version used.
* **JavaFX 20:** The specific JavaFX version used by the application.

## Project Structure

The project uses a multi-module Gradle build structure:

* `app`: Contains the main JavaFX application code, including the UI (FXML, CSS), controllers, and the main `App` class. This is the executable module.
* `utilities`: Contains shared utility classes, such as password hashing (`jbcrypt`) and environment variable loading (`dotenv-java`).
* `database`: Contains the database connection setup (`HikariCP`) and data access logic for interacting with the PostgreSQL database.

```

.
├── app
│   ├── src
│   │   ├── main
│   │   │   ├── java/org/javafxCloudGallery/app/...  (JavaFX App, Controllers)
│   │   │   └── resources/...                     (FXML, CSS, Images)
│   └── build.gradle
├── database
│   ├── src
│   │   └── main/java/org/javafxCloudGallery/database/... (Database Logic)
│   └── build.gradle
├── utilities
│   ├── src
│   │   └── main/java/org/javafxCloudGallery/utilities/... (Utility Classes)
│   └── build.gradle
├── build.gradle              (Root build file)
└── settings.gradle           (Defines subprojects)

````

## Prerequisites

* **Java Development Kit (JDK) 17 or later:** Ensure JDK 17 or a newer version is installed and configured correctly on your system.
* **Gradle:** While a Gradle wrapper is included, having Gradle installed globally can be helpful.
* **PostgreSQL Database:** Access to a running PostgreSQL instance.
* **Cloudinary Account:** A Cloudinary account is required for image storage. You'll need your Cloud Name, API Key, and API Secret.

## Setup

1.  **Clone the repository:**
    ```bash
    git clone <https://github.com/johnson-oragui/javafx-cloud-gallery.git>
    cd javafx-cloud-gallery
    ```

2.  **Set up Environment Variables:**
    Create a file named `.env` in the root directory of the project. This file will store sensitive configuration details. Check the `.env.sample`:
    Replace the placeholder values with your actual database and Cloudinary credentials.

3.  **Database Setup:**
    Create the necessary tables in your PostgreSQL database. The database module contains migration scripts (though not explicitly shown in the `build.gradle`).

## Building the Project

Use the Gradle wrapper script included in the repository:

```bash
# On macOS/Linux
./gradlew build

# On Windows
gradlew build
````

This will build all subprojects and generate the application artifacts.

## Running the Application

You can run the application directly using the Gradle `run` task in the `app` module:

```bash
# On macOS/Linux
./gradlew :app:run

# On Windows
gradlew :app:run
```

This will start the JavaFX application.

## Configuration

The application loads configuration from the `.env` file at the project root using `dotenv-java`. Ensure all required variables (`DATABASE_URL`, `DATABASE_USER`, `DATABASE_PASSWORD`, `CLOUDINARY_CLOUD_NAME`, `CLOUDINARY_API_KEY`, `CLOUDINARY_API_SECRET`) are correctly set in this file.

## Responsive Design Notes

The application UI, particularly the navigation bar, is designed to be responsive. It uses a horizontal navigation bar for larger window sizes and switches to a hamburger menu icon that toggles a vertical menu for smaller window sizes. The gallery's `FlowPane` is configured to wrap image thumbnails based on the available horizontal space.

## Contributing

Contributions are welcome\! Please follow standard GitHub practices: fork the repository, create a feature branch, and submit a pull request.

## License

This project is licensed under the [MIT License](https://www.google.com/search?q=LICENSE).

```
