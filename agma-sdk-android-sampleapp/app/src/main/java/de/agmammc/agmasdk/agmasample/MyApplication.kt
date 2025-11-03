package de.agmammc.agmasdk.agmasample

import android.app.Application
import de.agmammc.agmasdk.android.AgmaSdk
import org.prebid.mobile.Host
import org.prebid.mobile.PrebidMobile
import org.prebid.mobile.api.data.InitializationStatus
import android.util.Log
import de.agmammc.agmasdk.android.id5.Id5Response
import de.agmammc.agmasdk.android.id5.Id5ResponseListener
import de.agmammc.agmasdk.android.ortb2.App
import org.json.JSONObject
import org.prebid.mobile.PrebidEventDelegate
import org.prebid.mobile.TargetingParams

val consentString =
    "CQJrRgAQJrRgAAGABCENBUFgAP_gAAAAAAJwJegR5CpFTWFAYXhxQPNgGIQU0cAAAEQgBACBAiABAAMQYAQE00EyMASABAACAQAAIBABAAAECAAEAEAAAAAEAACAAAAAgAAIIAAAABEAQAIQAAgIAAAAAAAIAAABAAAAmAiACILAQEAAAAAACAAAACAAAIAAAAAAAAAAAAAAAAAAAAAAAAAgAIAAAAAAARAAgAAAAAAAAAAAIAAAEAQAEBX4SA8AAsACoAHAAQQAyADQAHgARAAmABvAD8AIQAQwArQBhgDKAHtAPwA_QCKAEagJEAkoBigDcAHEATsAocBR4C8wGSANXAbqEAEgAkACOARwAlIBOwErAK_AX-AyEMABAV-IAAgK_FAAQFDjAAIChx0CQABYAFQAOAAggBkAGgAPAAiABMgC4ALoAYgA3gB-AEMAK0AYYAygBogD2gH4AfsBFAEWgJEAkoBYgDFAG4AOIAi8BMgCdgFDgKPAXmAyQBlgDVwG6gOLHADgALgAkACOAQgAjIBHACUgE7ASsAr8Bf4DBAGHgMhIQBwAFgBcADEAG8AigBKQDFEAA4BGQCOAFiAV-AwQBh4EMyUA8ABYAHAAeABEACYAFwAMQAhgBWgD8AMUAi8BR4C8wGSEgBQAFwAjgBkAIyARwBKwC_wGHgMsAhmUgNgALAAqABwAEAANAAeABEACYAFIAMQAfgBDACtAGUANGAfgB-gEWAJKAbgBF4CdgFDgLzAZIAywBuoEMygBAABQAFwASAAyACOAFsAMgBHACRAEpALEAXUAxQCvwGCAMPLAAwCOAKHAYeAA.YAAAAAAAAAAA"

class MyApplication : Application(), PrebidEventDelegate, Id5ResponseListener {
    companion object {
        private const val TAG = "AgmaPrebidSample"
    }

    override fun onCreate() {
        super.onCreate()
        initPrebidSDK()
        initAgmaWithPrebidSDK()

        // if you do not use prebid, you can init the SDK with a static app object
        // initAgmaWithoutPrebidSDK()

        // optional, if you have an ID5 partner ID
        PrebidMobile.setEventDelegate(this)
    }

    private fun initPrebidSDK() {
        // taken form: https://github.com/prebid/prebid-mobile-android/blob/master/Example/PrebidDemoKotlin/src/main/java/org/prebid/mobile/prebidkotlindemo/CustomApplication.kt

        PrebidMobile.setPrebidServerAccountId("0689a263-318d-448b-a3d4-b02e8a709d9d")
        PrebidMobile.setPrebidServerHost(Host.createCustomHost("https://prebid-server-test-j.prebid.org/openrtb2/auction"))
        PrebidMobile.setCustomStatusEndpoint("https://prebid-server-test-j.prebid.org/status")
        PrebidMobile.initializeSdk(applicationContext) { status ->
            if (status == InitializationStatus.SUCCEEDED) {
                Log.d(TAG, "SDK initialized successfully!")
            } else {
                Log.e(TAG, "SDK initialization error: $status\n${status.description}")
            }
        }

        // Init the rest of your Prebid stack
        // ...
        TargetingParams.setGDPRConsentString(consentString)
        PrebidMobile.setShareGeoLocation(true)
        TargetingParams.setSubjectToGDPR(true)
        TargetingParams.setSendSharedId(true)

    }

    private fun initAgmaWithPrebidSDK() {
        AgmaSdk.configure(
            AgmaSdk.Config(
                code = "provided-by-agma-please-change",
                consentString = consentString,
                loggingEnabled = true, // good for debugging
                id5Config = AgmaSdk.Id5Config(
                    AgmaSdk.Id5Config.AppConfig(
                        "com.agmammc.agmasdk.android.testapp",
                        "1",
                        "My App"
                    ),
                    1234, // Please only use this with your own partner id
                    "com.example",
                )
            )
        )
        // can also be set later if needed
        // AgmaSdk.getInstance().setConsentString(consentString)

        AgmaSdk.getInstance().setId5ResponseListener(this)
    }
    private fun initAgmaWithoutPrebidSDK() {
        AgmaSdk.configure(
            AgmaSdk.Config(
                code = "provided-by-agma-please-change",
                loggingEnabled = true, // good for debugging
                consentString = consentString,
                // These are the ORTB2 Device Properties
                // see https://github.com/InteractiveAdvertisingBureau/openrtb2.x/blob/main/2.6.md#objectapp
                // and https://docs.agma-analytics.de/agma-sdk-android/
                // can be overwritten (e.g for sectionCat changes per Event basis)
                id5Config = AgmaSdk.Id5Config(
                    AgmaSdk.Id5Config.AppConfig(
                        "com.agmammc.agmasdk.android.testapp",
                        "1",
                        "My App"
                    ),
                    1234, // Please only use this with your own partner id
                    "com.example",
                ),
                app = App(
                    id = "app-defined-unique-id",
                    name = "My App Name",
                    bundle = "com.agmammc.agmasdk.android.testapp",
                    domain = "myapp.com",
                    storeUrl = "http://store-url",
                    cat = listOf("12"), // set App Taxonomie, eg. 12=News https://github.com/InteractiveAdvertisingBureau/Taxonomies/blob/main/Content%20Taxonomies/Content%20Taxonomy%201.0.tsv
                    sectionCat = null,
                    pageCat = null,
                    ver = "1.1.0",
                    privacyPolicy = 1,
                    paid = 0,
                    keywords = null,
                    ext = null
                )

            )
        )

    }

    override fun onBidResponse(request: JSONObject?, response: JSONObject?) {
        // print request

        AgmaSdk.getInstance().didReceivePrebidRequest(request);

        val agmaId = AgmaSdk.getInstance().getAgmaIdCompat() // or use getAgmaId when in a coroutine context
        if (agmaId != null) {
            // Send the agmaId to your first-party analytics system
            // Note: Your analytics system should automatically track when events are received
            // and operate on UTC to match the Helix dashboard's timezone
            Log.d("Analytics", "AgmaId: $agmaId")
            // yourAnalyticsService.trackEvent("agma_measurement", agmaId)
        }
    }

    // Optional, if you use ID5
    override fun onId5ResponseReceived(response: Id5Response) {
        Log.d(TAG, "onId5ResponseReceived $response")
    }
}
