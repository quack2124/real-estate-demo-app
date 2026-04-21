# Real Estate Demo app

Android application for viewing real estate properties and bookmarking them. Built with modern
development practices and 100% Kotlin.

## Architecture

This application follows Google's recommended app architecture, ensuring scalability,
maintainability, and testability.

- **Pattern**: **MVVM** (Model-View-ViewModel) with usage of **Repository** pattern
- **Dependency **Injection**: Dagger Hilt**
- **Layered Architecture**:
    - **Presentation Layer**: Handles UI and user interactions using **Jetpack Compose** and
      **ViewModels** with state management via **Kotlin StateFlows**.
    - **Domain Layer**: Contains core models, repository interfaces, and use cases.
    - **Data Layer**: Manages data sources including a remote API (**Retrofit**) and a local
      database (Room) for offline access and bookmark persistence. Handles data transformation via
      mappers and local (**Room**) pagination using **Paging 3**.

## Setup & Installation

### Prerequisites

- Android Studio Ladybug or newer (AGP version 9.1+).
- JDK 17.
- Android device requirements: Min SDK Android 7.0 (Api Level 24)

### Building the App

1. Clone the repository:
   ```
   git clone https://github.com/quack2124/real-estate-demo-app.git
   ```
2. Open the project in Android Studio.
3. Sync Gradle files to ensure all dependencies are downloaded.
4. Build the project using the `Build > Assemble Project` menu option, or simply click the green
   **Run (Play)** button in your toolbar to build and install it on an emulator or physical device.
   For more info check [android documentation](https://developer.android.com/studio/run)

### Running Tests

- **Unit Tests**: Run `./gradlew test` in the terminal or right-click the `test` directory in
  Android Studio.
- **UI Tests**: Run `./gradlew connectedAndroidTest` or right-click the `androidTest` directory in
  Android Studio.