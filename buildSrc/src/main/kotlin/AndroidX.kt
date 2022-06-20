@file:Suppress("SpellCheckingInspection")

object AndroidX {

    object Lifecycle {
        /**
         * @see <a href="https://developer.android.com/jetpack/androidx/releases/lifecycle"> Lifecycle Doc</a>
         */
        private const val version = "2.4.1"
        const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx::$version"
        const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
        const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
    }

    object Fragment {
        /**
         * @see <a href="https://developer.android.com/jetpack/androidx/releases/fragment"> Fragment Doc</a>
         */

        private const val version = "1.4.1"
        const val fragment = "androidx.fragment:fragment-ktx:$version"
    }

    object Activity {
        /**
         * @see <a href="https://developer.android.com/jetpack/androidx/releases/activity"> Activity Doc</a>
         */

        private const val version = "1.4.0"
        const val activity = "androidx.activity:activity-ktx:$version"
    }

    object Core {
        /**
         * @see <a href="https://developer.android.com/jetpack/androidx/releases/core"> Core Doc</a>
         */

        private const val version = "1.8.0"
        const val core = "androidx.core:core-ktx:$version"
    }

    object Appcompat {
        /**
         * @see <a href="https://developer.android.com/jetpack/androidx/releases/appcompat"> Appcompat Doc</a>
         */

        private const val version = "1.4.2"
        const val appcompat = "androidx.appcompat:appcompat:$version"
    }

    object ConstraintLayout {
        /**
         * @see <a href="https://developer.android.com/jetpack/androidx/releases/constraintlayout"> Appcompat Doc</a>
         */

        private const val version = "2.1.4"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:$version"
    }

    object AndroidWorkManager {
        /**
         * @see <a href="https://developer.android.com/jetpack/androidx/releases/work"> Android Work Manager Doc</a>
         */
        private const val work_version = "2.7.1"
        const val workManager = "androidx.work:work-runtime-ktx:$work_version"
    }

    object AndroidLifecycleWorkManagerHilt {

        /**
         * @see <a href="https://developer.android.com/jetpack/androidx/releases/work"> Android Lifecycle WorkManager Hilt Doc</a>
         */
        private const val hilt_work_version = "1.0.0"
        const val hiltWork = "androidx.hilt:hilt-work:$hilt_work_version"
        const val hiltCompiler = "androidx.hilt:hilt-compiler:$hilt_work_version"
    }
}