![Eventers Banner](.github/assets/Eventers_banner.webp)

## Overview

🎉 Eventers is a sophisticated Material You Jetpack Compose + Kotlin Android app for managing events—perfect for creating, viewing, registering and tracking upcoming meetups, parties, and workshops.

## Key Features

- Create & Edit Events
Add title, description, date/time, location, cover image, category, capacity, and ticket pricing.

- Browse & Discover
View upcoming events in a clean, scrollable list or calendar view with category filters and search.

- Event Details
Discover detailed info, RSVP, ticket availability, organizer contacts, and share events seamlessly.

- Notifications (WIP)
Get reminders before an event starts, with customizable preferences.

- Organizer Dashboard (WIP)
View attendee list, edit event info, send updates, or cancel events.

## 🛠️ Built With
- Kotlin & Jetpack Compose – Modern UI toolkit
- ViewModel + LiveData / Flow – MVVM architecture
- Supabase - As Backend database
- Hilt - dependency injection
- WorkManager – Scheduling notifications
- Navigation Component – Screen navigation
- Coil – Image loading
- Material3 – UI theming & components

## 🧰 Tech Stack & Tools

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=white&style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?logo=android&logoColor=white&style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Material--3-6200EE?logo=material-design&logoColor=white&style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Hilt-D0C4DF?logo=dagger&logoColor=white&style=for-the-badge"/>>
  <img src="https://img.shields.io/badge/MVVM-Architecture-26A69A?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Supabase-3ECF8E?logo=supabase&logoColor=white&style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Android%20Studio-3DDC84?logo=android-studio&logoColor=white&style=for-the-badge"/>
</p>

## 📸 Screenshots
<p align="center">
  <img src=".github/assets/sigin.webp" width="200"/>
  <img src=".github/assets/home.webp" width="200"/>
  <img src=".github/assets/details.webp" width="200"/>
  <img src=".github/assets/event.webp" width="200"/>
  <img src=".github/assets/options.webp" width="200"/>
  <img src=".github/assets/profile.webp" width="200"/>
</p>

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or newer  
- Kotlin 1.9+  
- Android SDK 34+

### Steps

1. Clone this repo:
   ```bash
   git clone https://github.com/Krtonia/Eventers.git
   ```
2. Open in Android Studio
3. Let Gradle sync and resolve dependencies
4. Run on an emulator or real device (API 21+)

## 🧩 Architecture

- MVVM pattern with clean separation of UI, ViewModel, and Repository
- Uses StateFlow / LiveData for reactive updates
- Organized modular code (UI → ViewModels)

## 🤝 Contributing
- Contributions, issues, and feature requests are welcome!

- Fork the repo

- Create your feature branch (git checkout -b feature/new-feature)
- Commit your changes (git commit -m 'Add new feature')
- Push to the branch (git push origin feature/new-feature)
- Open a Pull Request