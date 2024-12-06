package de.agmammc.agmasdk.agmasample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.agmammc.agmasdk.agmasample.ui.theme.AgmaSampleTheme
import de.agmammc.agmasdk.android.AgmaSdk
import org.json.JSONObject

val consentString =
    "CPyFwIAPyFwIACnABIDEDVCkAP_AAAAAAAYgJmJV9D7dbXFDcXx3SPt0OYwW1dBTKuQhAhSAA2AFVAOQ8JQA02EaMATAhiACEQIAolYBAAEEHAFUAEGQQIAEAAHsIgSEhAAKIABEEBEQAAIQAAoKAIAAEAAIgAABIgSAmBiQSdLkRUCAGIAwDgBYAqgBCIABAgMBBEAIABAIAIIIwygAAQBAAIIAAAAAARAAAgAAAAAAIAAAAABAAAASEgAwABBMwNABgACCZgiADAAEEzBUAGAAIJmDIAMAAQTMHQAYAAgmYQgAwABBMwlABgACCZhSADAAEEzA.f_gAAAAABcgAAAAA"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AgmaSampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(horizontal = 12.dp)
                    ) {
                        Text(
                            text = "Agma Prebid SDK for Android - SampleApp"
                        )

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                AgmaSdk.getInstance(applicationContext).triggerEvent()
                            }
                        ) { Text("Send Manual Event") }

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                val jsonObject =
                                    JSONObject(
                                        "{\"imp\":[{\"id\":\"a498d978-7039-4df1-9cef-b0136ac124c0\",\"instl\":1,\"clickbrowser\":0,\"secure\":1,\"banner\":{\"pos\":7,\"format\":[{\"w\":384,\"h\":757}]},\"ext\":{\"prebid\":{\"storedrequest\":{\"id\":\"prebid-demo-display-interstitial-320-480\"}}}}],\"id\":\"a498d978-7039-4df1-9cef-b0136ac124c0\",\"app\":{\"name\":\"Prebid Java Demo\",\"bundle\":\"org.prebid.mobile.javademo\",\"ver\":\"1.0.0\",\"publisher\":{\"id\":\"0689a263-318d-448b-a3d4-b02e8a709d9d\"},\"ext\":{\"prebid\":{\"source\":\"prebid-mobile\",\"version\":\"2.2.3\"}}},\"device\":{\"ua\":\"Mozilla\\/5.0 (Linux; Android 14; SM-S928B Build\\/UP1A.231005.007; wv) AppleWebKit\\/537.36 (KHTML, like Gecko) Version\\/4.0 Chrome\\/130.0.6723.107 Mobile Safari\\/537.36\",\"lmt\":0,\"devicetype\":4,\"make\":\"samsung\",\"model\":\"SM-S928B\",\"os\":\"Android\",\"osv\":\"14\",\"hwv\":\"Samsung SM-S928B\",\"language\":\"de\",\"carrier\":\"o2 - de\",\"mccmnc\":\"262-03\",\"h\":2340,\"w\":1080,\"connectiontype\":2,\"pxratio\":2.8125},\"regs\":{\"ext\":{\"gdpr\":0}},\"user\":{},\"source\":{\"tid\":\"a498d978-7039-4df1-9cef-b0136ac124c0\"},\"ext\":{\"prebid\":{\"storedrequest\":{\"id\":\"0689a263-318d-448b-a3d4-b02e8a709d9d\"},\"cache\":{\"bids\":{}},\"targeting\":{}}}}"
                                    )
                                AgmaSdk.getInstance(applicationContext).didReceivePrebidRequest(
                                    jsonObject
                                )
                            }
                        ) { Text("Simulate Prebid Response Received") }
                    }
                }
            }
        }
    }
}