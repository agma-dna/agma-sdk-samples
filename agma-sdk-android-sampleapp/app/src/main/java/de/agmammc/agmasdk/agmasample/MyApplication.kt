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
import java.net.URI

val consentString =
    "CPyFwIAPyFwIACnABIDEDVCkAP_AAAAAAAYgJmJV9D7dbXFDcXx3SPt0OYwW1dBTKuQhAhSAA2AFVAOQ8JQA02EaMATAhiACEQIAolYBAAEEHAFUAEGQQIAEAAHsIgSEhAAKIABEEBEQAAIQAAoKAIAAEAAIgAABIgSAmBiQSdLkRUCAGIAwDgBYAqgBCIABAgMBBEAIABAIAIIIwygAAQBAAIIAAAAAARAAAgAAAAAAIAAAAABAAAASEgAwABBMwNABgACCZgiADAAEEzBUAGAAIJmDIAMAAQTMHQAYAAgmYQgAwABBMwlABgACCZhSADAAEEzA.f_gAAAAABcgAAAAA"

class MyApplication : Application(), PrebidEventDelegate, Id5ResponseListener {
    companion object {
        private const val TAG = "AgmaPrebidSample"
    }

    override fun onCreate() {
        super.onCreate()
        initPrebidSDK()
        // initAgmaWithPrebidSDK()

        // if you do not use prebid, you can init the SDK with a static app object
        initAgmaWithoutPrebidSDK()

        // optional, if you have an ID5 partner ID
        initAgmaID5()
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
        PrebidMobile.setShareGeoLocation(true)
        TargetingParams.setSubjectToGDPR(true)
        TargetingParams.setSendSharedId(true)

    }

    private fun initAgmaWithPrebidSDK() {
        AgmaSdk.getInstance(this).setConfig(
            AgmaSdk.Config(
                code = "provided-by-agma-please-change",
                flushThreshold = 3, // set according to how often you refresh your ads to get a good batching balance, default is 15
                loggingEnabled = true, // good for debugging
            )
        )

    }

    // Optional: Include YOUR ID5 Config
    private fun initAgmaID5() {
        // If you use ID5, you need to manual set the consent string
        AgmaSdk.getInstance(this).setConsentString(consentString)
        AgmaSdk.getInstance(this).setId5Config(
            AgmaSdk.Id5Config(
                AgmaSdk.Id5Config.AppConfig(
                    "com.agmammc.agmasdk.android.testapp",
                    "1",
                    "My App"
                ),
                1234, // Please only use this with your own partner id
                "com.example",
            )
        )

        PrebidMobile.setEventDelegate(this)
    }

    private fun initAgmaWithoutPrebidSDK() {
        AgmaSdk.getInstance(this).setConfig(
            AgmaSdk.Config(
                code = "provided-by-agma-please-change",
                flushThreshold = 3, // set according to how often you refresh your ads to get a good batching balance, default is 15
                loggingEnabled = true, // good for debugging
                // These are the ORTB2 Device Properties
                // see https://github.com/InteractiveAdvertisingBureau/openrtb2.x/blob/main/2.6.md#objectapp
                // and https://docs.agma-analytics.de/agma-sdk-android/
                // can be overwritten (e.g for sectionCat changes per Event basis)
                app = App(
                    id = "app-defined-unique-id",
                    name = "My App Name",
                    bundle = "com.agmammc.agmasdk.android.testapp",
                    domain = "myapp.com",
                    storeUrl = "http://store-url",
                    cat = null,
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
        // If you do not use prebid you need to manual set your consent string
        AgmaSdk.getInstance(this).setConsentString(consentString)

    }

    override fun onBidResponse(request: JSONObject?, response: JSONObject?) {
        AgmaSdk.getInstance(applicationContext).didReceivePrebidRequest(request);
    }

    // Optional, if you use ID5
    override fun onId5ResponseReceived(response: Id5Response) {
        Log.d(TAG, "onId5ResponseReceived ${response.toString()}")
    }
}