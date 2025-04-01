# 📻 VibeStation

VibeStation is a modern, feature-rich Android radio streaming application built with Jetpack Compose and Clean Architecture principles. The app allows users to discover, play, and favorite radio stations from around the world.

## ✨ Features

- 🔍 **Browse Radio Stations**: Explore a curated list of radio stations from Turkey
- 🔎 **Search Functionality**: Easily find stations by name or genre
- ❤️ **Favorites System**: Save your favorite stations for quick access
- 🎵 **Live Streaming**: High-quality audio streaming with playback controls
- 🎨 **Beautiful UI**: Modern, intuitive interface built with Material 3 Design
- 🌓 **Dark Mode**: Full support for light/dark theme
- 📱 **Offline Support**: Access your favorite stations even without an internet connection

## 🏛️ Architecture

VibeStation follows Clean Architecture principles with a clear separation of concerns:

- 🖼️ **Presentation Layer**: UI components built with Jetpack Compose, ViewModels
- 🧠 **Domain Layer**: Business logic, use cases, and repository interfaces
- 💾 **Data Layer**: Repository implementations, data sources (local & remote)

### 🛠️ Technology Stack

- <img src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/kotlin/kotlin.png" width="18"/> **Kotlin**: 100% Kotlin codebase
- <img src="https://developer.android.com/images/jetpack/compose/compose-logo.svg" width="18"/> **Jetpack Compose**: Modern UI toolkit for building native Android UI
- <img src="https://developer.android.com/images/kotlin/coroutines-agent.svg" width="18"/> **Coroutines & Flow**: Asynchronous programming with reactive streams
- <img src="https://dagger.dev/images/hilt.svg" width="18"/> **Hilt**: Dependency injection
- <img src="https://developer.android.com/topic/libraries/architecture/images/room_icon.svg" width="18"/> **Room**: Local database for storing favorite stations
- <img src="https://avatars.githubusercontent.com/u/82592?s=48&v=4" width="18"/> **Retrofit**: Network requests to the Radio Browser API
- <img src="https://developer.android.com/static/images/icons/icons_192px.png" width="18"/> **Media3 ExoPlayer**: Audio playback
- <img src="https://coil-kt.github.io/coil/logo.svg" width="18"/> **Coil**: Image loading
- <img src="https://m3.material.io/static/assets/m3-logo.svg" width="18"/> **Material 3**: Modern design system

## 📂 Project Structure

```
com.tamersarioglu.vibestation/
├── data/
│   ├── local/         # 💾 Room database, DAOs, entities
│   ├── mapper/        # 🔄 Data mapping between layers
│   ├── model/         # 📋 Data transfer objects
│   ├── remote/        # 🌐 API services
│   └── repository/    # 📦 Repository implementations
├── di/                # 💉 Dependency injection modules
├── domain/
│   ├── model/         # 📝 Domain models
│   ├── repository/    # 📚 Repository interfaces
│   └── usecase/       # ⚙️ Business logic use cases
├── presentation/
│   ├── common/        # 🧰 Common UI components and utilities
│   ├── components/    # 🧩 Reusable Compose components
│   ├── navigation/    # 🧭 Navigation graph and screen destinations
│   ├── screens/       # 📱 App screens (radio list, favorites, settings)
│   └── theme/         # 🎨 UI theme, colors, typography
└── ui/                # 💅 UI themes and styling
```

## 🚀 Getting Started

### Prerequisites

- ⚡ Android Studio Giraffe or newer
- 📱 Android SDK 24+
- 💻 Kotlin 2.1+

### Installation

1. Clone the repository:
```bash
git clone https://github.com/tamersarioglu/vibestation.git
```

2. Open the project in Android Studio

3. Build and run the app on an emulator or physical device

## 🌐 API

VibeStation uses the [Radio Browser API](https://api.radio-browser.info/) to fetch station data. The app currently filters stations by country (Turkey) but can be easily modified to include stations from around the world.

## 👥 Contributing

Contributions are welcome! Feel free to open issues or submit pull requests.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- 📻 [Radio Browser API](https://api.radio-browser.info/) for providing free radio station data
- 🎨 [Material 3 Design](https://m3.material.io/) for the visual design system
- 🌟 All the awesome open-source libraries that made this project possible