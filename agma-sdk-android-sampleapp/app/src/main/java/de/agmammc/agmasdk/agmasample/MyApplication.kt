package de.agmammc.agmasdk.agmasample

import android.app.Application
import de.agmammc.agmasdk.android.AgmaSdk

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AgmaSdk.getInstance(this).setConfig(
            AgmaSdk.Config(
                code = "provided-by-agma-please-change",
                consentString = consentString,
                flushThreshold = 3,
                loggingEnabled = true
            )
        )
    }

    override fun onTerminate() {
        super.onTerminate()
        AgmaSdk.getInstance(this).onTerminate()
    }
}