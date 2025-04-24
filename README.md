# Zenithra: MangaVerse Reader

![Zenithra](https://raw.githubusercontent.com/yeshuwahane/pupilApp/refs/heads/main/screenshots/pupilMesh.png)

### Download Options:
#### <a href="https://github.com/yeshuwahane/pupilApp/releases/tag/release">Github Release</a>

Zenithra is a modern, production-grade Android app built as part of the Pupilmesh assignment using the MangaVerse API. This application demonstrates clean architecture, offline-first support, camera-based face detection, and Compose UI best practices.

---

## 🚀 Features

### ✉️ Authentication
- Simple sign-in/register using local Room DB
- Persists signed-in state using SessionManager

### 📚 Manga Explorer
- Fetches manga from MangaVerse API
- Fully paginated scroll with shimmer loading
- Caches data using Room for offline support
- Auto-refresh when internet returns
- Detailed manga description screen with offline fallback

### 📷 Face Recognition (MediaPipe)
- Real-time face detection using MediaPipe
- Reference box to validate face alignment
- Permission flow with graceful fallback

---

## 🧬 Best Practices Followed

### ✍️ Clean Architecture
- `data`, `domain`, `presentation` layers
- Single Source of Truth with Room + Repository
- UseCases for business logic encapsulation

### 🌐 Offline-First Approach
- Fetch on first launch
- Serve cached data when offline
- Room DB used as the primary data source when network fails

### 🩺 Dependency Injection
- Powered by Hilt
- All modules (API, DB, UseCases, FaceDetector) managed via DI

### 📉 State Management
- `StateFlow` used for reactive UI
- `DataResource<T>` class for loading/success/error consistency
- UI observes state lifecycle-aware using `collectAsStateWithLifecycle()`

### 🎨 Jetpack Compose UI
- Material3 + Dark Mode Styling
- LazyVerticalGrid for manga list
- Custom shimmer loading states
- Keyboard dismissal logic

---

## 📁 Tech Stack

| Layer        | Technology                             |
|--------------|-----------------------------------------|
| Language     | Kotlin                                  |
| UI           | Jetpack Compose                         |
| DI           | Hilt                                    |
| Network      | Retrofit + OkHttp                       |
| DB           | Room                                     |
| Media        | MediaPipe + CameraX                     |
| Image Loading| Coil 3                                   |
| State        | StateFlow + collectAsStateWithLifecycle |
| Architecture | MVVM + Clean Architecture               |
---


