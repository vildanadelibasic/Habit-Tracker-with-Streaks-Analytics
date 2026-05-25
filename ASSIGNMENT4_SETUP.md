# Assignment 4 – Setup

## Retrofit (mandatory)

1. Run the course REST API locally (see professor repo **Lab 11** under `MobileProgramming-2026`).
2. Default base URL in the app: `http://10.0.2.2:8000/` (Android emulator → host machine `localhost:8000`).
3. On a **physical device**, change `API_BASE_URL` in `app/build.gradle.kts` to your PC’s LAN IP, e.g. `http://192.168.1.10:8000/`.
4. On **Home**, tap the refresh icon to sync habits from the API into Room.

## Firebase (bonus)

1. Create a Firebase project and add an Android app with package `com.example.mobileprogrammingarchitecture`.
2. Download **`google-services.json`** from Firebase Console and replace `app/google-services.json` (the committed file is a build placeholder only).
3. Enable **Email/Password** authentication and create a **Firestore** database.
4. Firestore collection used by the app: `habits` (realtime listener on Habits screen when signed in).

## What the app demonstrates

| Requirement | Location |
|-------------|----------|
| DTOs | `data/model/datasource/network/dto/HabitDto.kt` |
| Retrofit service (GET/POST/PUT/DELETE) | `HabitApiService.kt` |
| Hilt network module | `data/model/di/NetworkModule.kt` |
| Remote repository | `HabitRemoteRepository` + `impl` |
| ViewModel → network | `HomeViewModel.refreshHabits()` |
| Auth flow | `LoginScreen`, `RegisterScreen`, `AuthRepository` |
| Persistent session | Firebase Auth `observeIsLoggedIn()` in `MainActivity` |
| Firestore realtime | `HabitCloudRepository`, `HabitsViewModel` |
| Theme | Settings screen + `ThemePreference` |
| Animation | `HomeProgressSection` animated progress bar |
