# AirCoroutineProject

[![](https://jitpack.io/v/mumayank/AirLocation.svg)](https://jitpack.io/#mumayank/AirLocation)

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
    implementation 'androidx.constraintlayout:constraintlayout:LATEST_VERSION'
}
```
where LATEST_VERSION is [![](https://jitpack.io/v/mumayank/AirLocation.svg)](https://jitpack.io/#mumayank/AirLocation)

## Usage

#### Setup for Activities
Suppose you want to use this library for `MainActivity`. Then you need to create a corresponding view model for this activity called `MainViewModel`:
```kotlin
import androidx.lifecycle.ViewModel
class MainViewModel : ViewModel() {
    // any variables which are needed in the background task
    var value = 0
}
```

#### Setup for Fragments
Note that all the fragments in a parent activity can make use of the same view model as that of the parent activity.

#### Actual usage
Using this library is as simple as this:
```kotlin
AirCoroutine.execute(AirCoroutine.getViewModel(activity as Activity, MainViewModel::class.java), object : AirCoroutine.Callback {

  override suspend fun doTaskInBg(viewModel: ViewModel): AirCoroutine.TaskResult? {
      // do your background task here in the background coroutine
      delay(100) // you can use coroutine methods here as this method is suspended
      // return the result of your task here
      return AirCoroutine.TaskResult.SUCCESS 
  }

  override fun getTaskType(): AirCoroutine.TaskType? {
      // return they type of the task here: IO or CALCULATIONS
      return AirCoroutine.TaskType.CALCULATIONS
  }

  override fun onSuccess(viewModel: ViewModel) {
      // do something on the UI thread when the task is successful
  }

  override fun onFailure(viewModel: ViewModel) {
      // do something on the UI thread when the task has failed
  }

})}
```
