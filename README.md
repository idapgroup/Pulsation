# Pulsation
Android compose pulsation (waving, pulsating, reveal) animation
## Setup
Please, add to repositories jitpack:
```groovy
repositories {
  mavenCentral()
  ...
  maven { url 'https://jitpack.io' }
}
```
Add to your module next dependency:
```groovy
dependencies {
  implementation 'com.github.idapgroup:Pulsation:0.1.0'
}
```
`Note:` Do not forget to add compose dependencies 🙃

## Usage sample
Animation duplicates provided content and animates it on given content background.

There is 2 base types to use.

### Linear
Creates base `Linear` animation for provided content:
```kotlin
        Pulsation(
            enabled = true,
            type = PulsationType.Linear(duration = 2000, delayBetweenRepeats = 1000)
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Red, shape = CircleShape)
                    .size(100.dp)
            )
        }
```
  or example with real content:
  ```kotlin
        Pulsation(
            enabled = true,
            type = PulsationType.Linear(duration = 3000, delayBetweenRepeats = 1000)
        ) {
          Image(painter = painterResource(id = R.drawable.ic_launcher_round), contentDescription = null)
        }
```
[device-2023-05-18-154157.webm](https://github.com/idapgroup/Pulsation/assets/12797421/8180b21c-3d82-411e-b981-970b8f8ac691)

### Iterative
Uses animation cycles and add possibility to add delay between them:
```kotlin
        Pulsation(
            enabled = true,
            type = PulsationType.Iterative(
                iterations = 3,
                iterationDelay = 0,
                iterationDuration = 500,
                delayBetweenRepeats = 1000
            )
        ) {
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.ic_launcher_round),
                contentDescription = null
            )
        }
```
[device-2023-05-18-155303.webm](https://github.com/idapgroup/Pulsation/assets/12797421/633f5176-936f-485b-9122-cbcb1dc766a3)


