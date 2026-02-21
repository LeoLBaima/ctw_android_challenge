# News

Small Android (Compose) app that consumes **NewsAPI** and ships as two flavors:
- `bbc` (source: `bbc-news`)
- `cnn` (source: `cnn`)

## Requirements

- Android Studio (recent)
- JDK toolchain supported by your Android Studio/AGP

## Setup (API key)

This project expects the API key as a **Gradle property** named `NEWS_API_KEY`.

1) Add the property in one of these locations:

- **Project file (recommended for this repo):** `<project-root>/gradle.properties` *(don’t commit it)*
- **User-level file:** `~/.gradle/gradle.properties`

Add:

```properties
NEWS_API_KEY=YOUR_NEWSAPI_KEY_HERE
```

Notes:
- The key is read in `app/build.gradle.kts` via `providers.gradleProperty("NEWS_API_KEY").get()`.
- If the property is missing, the build will fail. If it’s present but blank, requests are made without an `apiKey` query param.

## Run

### Using Android Studio

- Open the project.
- Pick a run configuration for a flavor, e.g. `app` with variant `bbcDebug` or `cnnDebug`.
- Run on an emulator/device.

### Using Gradle

```bash
./gradlew :app:installBbcDebug
# or
./gradlew :app:installCnnDebug
```

## Architecture (high level)

The code is organized by **feature** under `app/src/main/java/com/leob/news/features/*`:
- `data/` – Retrofit services, repository implementations, DTOs/mappers
- `domain/` – repository interfaces + domain models/use-cases (when needed)
- `presentation/` – Activities/Compose UI and ViewModels

Cross-cutting app setup lives under `app/src/main/java/com/leob/news/config/*`.

## Dependency Injection (Hilt)

Hilt is enabled via:
- `@HiltAndroidApp` on `MyNewsWhitelabelApp` (`.config.di.MyNewsWhitelabelApp`)
- `@AndroidEntryPoint` on Activities (e.g. `AuthActivity`, `HomeActivity`)
- `@HiltViewModel` for ViewModels

Modules:
- `NetworkModule` (`config/di/NetworkModule.kt`)
  - provides `Moshi`, `OkHttpClient` (adds the `apiKey` query param), and `Retrofit`
- `HomeModule` (`features/home/di/HomeModule.kt`)
  - binds `HomeRepositoryImpl` to `HomeRepository`
  - provides `HomeService` from Retrofit

## Troubleshooting

- **Build fails with** `Cannot query the value of this provider because it has no value available`:
  add `NEWS_API_KEY` to `gradle.properties` as shown above.
