# AirCoroutineProject

[![](https://jitpack.io/v/mumayank/AirCoroutineProject.svg)](https://jitpack.io/#mumayank/AirCoroutineProject)

This library will help you to use the power of ViewModel and Coroutines to do background tasks with confidence!

## Setup

In root `build.gradle`:
```gradle
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
  ```
  
In app-level `build.gradle`:
```gradle
android {
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
dependencies {
    implementation 'com.github.mumayank:AirCoroutineProject:LATEST_VERSION'
}
```
where LATEST_VERSION is [![](https://jitpack.io/v/mumayank/AirCoroutineProject.svg)](https://jitpack.io/#mumayank/AirCoroutineProject)
