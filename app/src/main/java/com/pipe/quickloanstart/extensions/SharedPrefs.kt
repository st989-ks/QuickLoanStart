package com.pipe.quickloanstart.extensions

import android.content.Context
import android.content.SharedPreferences
import com.pipe.quickloanstart.BuildConfig

class SharedPrefs(context: Context) {

    companion object {
        private const val PREF = BuildConfig.APPLICATION_ID
        private const val PREF_TOKEN = "user_token"
        private const val PREF_ID = "loan_id"
        private const val PREF_USER_INFO = "user_info"
        private const val IS_FIRST_ENTRY = "is_login"
        private const val LANG = "lang_app"
        private const val TEXT_PUSH = "text_push_message"
        private const val FRAGMENT_STATS_LOOK = "fragment_stats_look"
        private const val LAST_PUSH = "last_push"
        private const val SIZE_LIST = "size_list"
        private const val TEN_THOUSAND_SYSTEM_TIME = "last_system_time"
    }

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    fun checkTenThousandMillisecond(): Boolean {
        val nawSystemTime = System.currentTimeMillis()
        val oldSystemTime = getSystemMillisecond()

        val difference = nawSystemTime - oldSystemTime
        return if (10000 < difference) {
            putSystemTenThousandMillisecond(nawSystemTime)
            true
        } else {
            false
        }
    }

    private fun putSystemTenThousandMillisecond(millisecondSystem: Long) {
        put(TEN_THOUSAND_SYSTEM_TIME, millisecondSystem)
    }

    private fun getSystemMillisecond(): Long =
        get(TEN_THOUSAND_SYSTEM_TIME, Long::class.java)


    fun setOnBackPressed(value: Boolean) {
        put(FRAGMENT_STATS_LOOK, value)
    }

    fun getOnBackPressed(): Boolean = get(FRAGMENT_STATS_LOOK, Boolean::class.java)


    fun setLanguage(code: String) {
        put(LANG, code)
    }

    fun getLanguage(): String {
        val lang = get(LANG, String::class.java)
        if (lang.isEmpty())
            return "En"
        return lang
    }


    fun getTextPush(): String = get(TEXT_PUSH, String::class.java)
    fun setTextPush(code: String) {
        put(TEXT_PUSH, code)
    }

    fun getSizeList(): Int =
        get(SIZE_LIST, Int::class.java)

    fun putSizeList(value: Int) {
        put(SIZE_LIST, value)
    }


    fun setNoFirstEntry(value: Boolean) {
        put(IS_FIRST_ENTRY, value)
    }

    fun getNoFirstEntry() = get(IS_FIRST_ENTRY, Boolean::class.java)


    fun saveToken(token: String) {
        put(PREF_TOKEN, token)
    }

    fun getToken() = get(PREF_TOKEN, String::class.java)
    fun clearToken() {
        put(PREF_TOKEN, "")
    }

    fun setIdLoan(id: Int) {
        put(PREF_ID, id)
    }

    fun toZeroIdLoan() {
        put(PREF_ID, 0)
    }

    fun getIdLoan() = get(PREF_ID, Int::class.java)

    fun clearUserInfo() {
        put(PREF_USER_INFO, "")
    }

    private fun <T> get(key: String, clazz: Class<T>) =
        when (clazz) {
            String::class.java -> sharedPref.getString(key, "")
            Boolean::class.java -> sharedPref.getBoolean(key, false)
            Float::class.java -> sharedPref.getFloat(key, 0.0f)
            Double::class.java -> sharedPref.getFloat(key, 0.0f)
            Int::class.java -> sharedPref.getInt(key, 0)
            Long::class.java -> sharedPref.getLong(key, 0)
            else -> null
        } as T

    private fun <T> put(key: String, data: T) {
        val editor = sharedPref.edit()

        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)

        }
        editor.apply()
    }
}

