plugins {
    alias(libs.plugins.android)
    alias(libs.plugins.compose)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.test"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.test"
        minSdk = 27
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        // 编译工具：D8（只编译）、R8（编译+优化）
        release {
            optimization { enable = false }

            isMinifyEnabled = false
//          isMinifyEnabled = true   // 开启R8 （开启代码精简）
//          isShrinkResources = true // R8进一步检查（把用不到的资源文件在打包里剔除）
        }
    /* 开启R8的功效：
          代码缩减：移除未使用的代码
          代码混淆：缩短类、方法、字段名称
          方法内联、类合并 */
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)

    implementation(libs.bundles.kotlinx)
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.nav3)
    implementation(libs.bundles.coil)

    debugImplementation(libs.compose.tooling)
}