# App Tracker SDK-Library (DEMO)
A Demo-Library for App-Tracking that provides functions to validate actions or transactions that occur during the tracking process on the App, and send them to the API.

## Requirements
* Android Gradle Plugin (AGP) 7.3.1

## Preparation
### 1. Generate GitHub Access Token
* Login to GitHub
* Go to Setting > Developer Settings > Personal Access Tokens > Generate new token
* Make sure you select the following scopes:

![Access Token](https://github.com/NeoRevolt/drawimage-demo-gitpack-publish/blob/master/acces_scope_github_token.PNG?raw=true)

### 2. Create ``github.properties`` file within the root project
```properties
    gpr.usr=YOUR_USERNAME //Change with your username
    gpr.key=YOUR_KEY  //Change with your personal access token before
```
Then add this properties to ``.gitignore``

## Usage / Implementation

### Gradle (settings.gradle)

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS) // Set to PREFER_SETTINGS
    repositories {
        google()
        mavenCentral()
        
        // Set target packages repo
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/NeoRevolt/TrackLibrary")
            credentials {
                username = 'gpr.usr'
                password = 'gpr.key'
            }
        }
    }
}
```

### Gradle (build.gradle/App)

```groovy
plugins {
    id 'kotlin-kapt'
}

android {
    compileSdk 33
    
    defaultConfig {
        targetSdk 33
    }
}

dependencies {
    implementation 'com.github.neorevolt:apptrack-gitpack:1.0.0'
}
```

# Sample Code
#### Save Token - Validate and Send Action to API:

```kotlin
// Initialize
private lateinit var sessionManager: SessionManager // Manage Auth Token 
private lateinit var transactionReport: TransactionReport // Validate and Send Action/Transaction to API

public override fun onCreate(savedInstanceState: Bundle?) {
   ....
    
    // Get Instance with Context
    transactionReport = TransactionReport(this)
    sessionManager = SessionManager(this)
    
    // Save Auth Token so Library can use it
    sessionManager.saveAuthToken(TOKEN)
    
    // Validate Action when App is opened
    transactionReport.isActionValid("application", "open")
    
    binding.apply {
            btnCheckSaldo.setOnClickListener {
            transactionReport.isActionValid("check saldo", "click") // Validate Action when Button clicked
     }      
}
```

#### Set Custom API Server IP Address (only for version 2.0.0) :

```kotlin
//Initialize
private lateinit var apiManager: ApiManager // Manage Api Server Base URL

public override fun onCreate(savedInstanceState: Bundle?) {
   ....
   
    // Get Instance with Context
    apiManager = ApiManager(this)
    
    binding.apply {
            btnCheckSaldo.setOnClickListener {
            apiManager.setBaseUrl(SERVER_IP) // Set API Server API
     }      
}

```
