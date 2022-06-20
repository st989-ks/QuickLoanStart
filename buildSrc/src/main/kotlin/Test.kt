@file:Suppress("SpellCheckingInspection")
object Test {

    object Mockito {
        private const val versionMockito = "4.0.0"
        const val mockitoMockito = "org.mockito:mockito-core:$versionMockito"
        const val mockitoKotlinMockito = "org.mockito.kotlin:mockito-kotlin:$versionMockito"

        private const val versionMockitoAndroid = "4.6.1"
        const val mockitoinline = "org.mockito:mockito-inline:$versionMockitoAndroid"

    }

    object Androidx {
        private const val versionAndroidx = "1.4.0"
        const val core = "androidx.test:core:$versionAndroidx"
        const val rules = "androidx.test:rules:$versionAndroidx"

        private const val versionArch = "2.1.0"
        const val arch ="androidx.arch.core:core-testing:$versionArch"

        private const val versionfragmentTesting = "1.5.0-rc01"
        const val fragmentTesting ="androidx.fragment:fragment-testing:$versionfragmentTesting"


        private const val versionJunit = "1.1.2"
        const val junitEtx = "androidx.test.ext:junit-ktx:$versionJunit"

        private const val versionEspressoCore = "3.4.0"
        const val espressoCore = "androidx.test.espresso:espresso-core:$versionEspressoCore"
    }
    object Kaspersky {
        private const val versionKaspresso = "1.4.0"
        const val kaspresso = "com.kaspersky.android-components:kaspresso:$versionKaspresso"
    }
    object Coroutines {
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${KotlinX.Coroutines.version}"
    }

    object Hilt {
        const val test = "com.google.dagger:hilt-android-testing:${HiltDagger.versionDaggerHilt}"
    }

    object Junit {
        private const val version = "4.13.2"
        const val junit = "junit:junit:$version"

        private const val versionJupiter= "5.8.2"
        const val jupiter = "org.junit.jupiter:junit-jupiter:$versionJupiter"
    }
}