# MBooking 🎬

A **native Android** movie-browsing app built with **Jetpack Compose**, backed by the TMDB API. Production-grade practices: dependency injection, typed networking, image caching, skeleton loaders, and release signing.

> **Stack:** Kotlin 2.0 · Jetpack Compose · Ktor 3 · Koin 4 · Coil 3 · Lottie · Navigation Compose · kotlinx.serialization · Material 3

## Why this project

This is my flagship native Android project. I built it to go deep on the parts of the Android stack that make a real shipped app feel solid: typed API contracts, a Koin-wired dependency graph, careful loading states, and a release build that's actually minified and proguarded — not just a debug demo.

## Highlights

- **Ktor 3 + Resources** — typed-routing API client (`client.get(TMDBMovie.NowPlaying(...))`), so endpoints and query params are compile-time-safe, not stringly-typed URL building.
- **kotlinx.serialization** — clean, annotation-driven JSON models.
- **Koin DI 4** — a version-catalog-managed dependency graph (`KoinModules.kt`) with scoped repositories and clients.
- **Coil 3** — async image loading with network support (`coil-network-ktor3`).
- **Repository pattern** — clean `data/network` repos (Movie, Genres, Search) abstracting the API behind typed resources.
- **Skeleton loaders** — branded `Skeleton*` composables (carousel, slider, cast cards) for a polished loading experience.
- **Debounce** — a reusable `RememberDebounce` composable for the search UI; no thundering herring of requests per keystroke.
- **Lottie + Lucide icons** — motion and a consistent icon set.
- **Release-ready** — R8/proguard + minify enabled, explicit signing config in the build. This is a build you'd be comfortable shipping.
- **Version catalog** — `libs.versions.toml` centralizes all versions (AGP 8.6.1, Kotlin 2.0.20, Compose BOM 2024.09).

## Architecture

```
app/src/main/java/com/skylissh/mbooking/
├── data/
│   ├── models/        # kotlinx.serialization DTOs (Movie, Detail, Credits, Genre, Paginated)
│   └── network/       # Ktor Resources + repositories (Movie, Genres, Search)
├── ui/
│   ├── composables/
│   │   ├── blocks/    # feature widgets (MovieCarousel, MovieInfoCard, SearchTopBar, ...)
│   │   └── core/      # reusable primitives (Carousel, Input, Skeleton, RememberDebounce)
│   ├── screens/       # HomeScreen, ErrorScreen, etc.
│   ├── extensions/    # LazyListStateExtension etc.
│   └── MockMovies.kt  # offline/dev data
└── KoinModules.kt     # dependency wiring
```

## Getting started

```bash
# add your TMDB key to local.properties / build config
./gradlew assembleRelease
```

Requires Android Studio with JDK 17+ and the Android SDK.

## What it demonstrates

- Modern native Android (Compose-first, Kotlin 2.0)
- Typed networking, DI, and image loading done right
- Polish: skeletons, debounce, error screens, release hardening
