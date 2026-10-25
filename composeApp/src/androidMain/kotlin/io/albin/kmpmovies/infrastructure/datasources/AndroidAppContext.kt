package io.albin.kmpmovies.infrastructure.datasources

import android.content.Context

object AndroidAppContext {
    private var appContext: Context? = null

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    fun get(): Context? {
        if (appContext == null) {
            try {
                val activityThreadClass = Class.forName("android.app.ActivityThread")
                val method = activityThreadClass.getMethod("currentApplication")
                appContext = method.invoke(null) as? Context
            } catch (_: Exception) {}
        }
        return appContext
    }
}
