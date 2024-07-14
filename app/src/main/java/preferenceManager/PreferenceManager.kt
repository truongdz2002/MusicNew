// PreferenceManager.kt
package preferenceManager

import android.content.Context

object PreferenceManager {
    private const val PREFERENCES_NAME = "MyAppPreferences"
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private fun getSharedPreferences() = appContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        getSharedPreferences().edit().putString(key, value).apply()
    }

    fun getData(key: String, defaultValue: String = ""): String {
        return getSharedPreferences().getString(key, defaultValue) ?: defaultValue
    }
}
