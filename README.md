# drawimage-demo-gitpack-publish
Sebuah Demo-Library untuk App-Tracking yang menyediakan functions untuk melakukan validasi terhadap action atau transaction yang terjadi selama proses tracking pada App, dan mengirimnya ke API.


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
    gpr.usr=YOUR_USERNAME //Change with your username and acces key
    gpr.key=YOUR_KEY  //Change with your personal acces token before
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
#### With previews:

MainActivity.kt
```kotlin
private lateinit var sessionManager: SessionManager
private lateinit var authInterceptor: AuthInterceptor
private lateinit var transactionReport: TransactionReport

public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    
    transactionReport = TransactionReport(this)
    sessionManager = SessionManager(this)
    authInterceptor = AuthInterceptor(this)
    
    val token = sessionManager.fetchAuthToken()
    transactionReport.isActionValid("application", "open")
    
    binding.apply {
            btnCheckSaldo.setOnClickListener {
            transactionReport.isActionValid("check saldo", "click")
     }      
}
```
