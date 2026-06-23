# TokoOnline

An e-commerce (online store) application built with **Kotlin Multiplatform** and **Compose Multiplatform** UI, targeting **Android** and **iOS** from a single codebase.

## Tech Stack

| Concern            | Library                                       |
|--------------------|-----------------------------------------------|
| UI                 | Compose Multiplatform                         |
| Navigation         | PreCompose                                    |
| Networking         | Ktor (OkHttp on Android, Darwin on iOS)       |
| Serialization      | kotlinx.serialization                         |
| Local database     | Realm Kotlin                                  |
| Paging             | AndroidX Paging & Cash App Multiplatform Paging |
| Key-value storage  | Multiplatform Settings                        |
| Image loading      | Image Loader                                  |
| Build config       | BuildKonfig                                   |

> Full versions are listed in [`gradle/libs.versions.toml`](gradle/libs.versions.toml).

## Module Structure

The project is split into multiple Gradle modules to keep features and layers cleanly separated:

- **`/composeApp`** — application entry point and shared Compose code across all targets.
  - `commonMain` — code shared by all platforms.
  - `androidMain` / `iosMain` — platform-specific code.
- **`/iosApp`** — iOS application entry point (place SwiftUI code here when needed).
- **`/features`** — per-feature modules: `home`, `productlist`, `productdetail`, `cart`, `favorite`, `login`.
- **`/apis`** — API contracts & implementations: `product`, `authentication`.
- **`/libraries`** — shared modules across features: `core`, `component`.
- **`/products`** — product domain/data module.

## Running the Project

### Android
Run the `composeApp` configuration from Android Studio, or from the terminal:

```bash
./gradlew :composeApp:installDebug
```

### iOS
Open `iosApp/iosApp.xcodeproj` in Xcode and run, or use the Kotlin Multiplatform Mobile plugin in Android Studio.

## Configuration

- Set local credentials/endpoints in `local.properties` (values are generated into code via BuildKonfig).
- Android SDK targets: `compileSdk 34`, `minSdk 24`.

## References

- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
</content>
</invoke>
