package de.agmammc.agmasdk.agmasample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.agmammc.agmasdk.agmasample.ui.theme.AgmaSampleTheme
import de.agmammc.agmasdk.android.AgmaSdk
import de.agmammc.agmasdk.android.ortb2.User
import org.prebid.mobile.BannerAdUnit

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge()
        setContent {
            AgmaSampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Box to center the Column content
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Agma Prebid SDK for Android - SampleApp"
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(
                                onClick = { triggerEventWithoutPrebid() },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Send Manual Event (without Prebid)")
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { triggerEventWithPrebid() },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Simulate Ad-reload (with Prebid)")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun triggerEventWithPrebid() {
        // @todo: show the add itself, we only create a ad-request for simplicity
        // and fetch the demand
        var bannerAdUnit = BannerAdUnit("prebid-demo-banner-320-50", 320, 50)
        bannerAdUnit.fetchDemand() { result ->

        }
    }

    private fun triggerEventWithoutPrebid() {
        // triggerEvent can set user / app on a per Event basis
        // You are allowed to trigger this whenever the app potentially refreshes its ads.
        // see docs for more info

        // optional: when you use sharedId for prebid
        val sharedId = "E4D65283-171B-4559-89F1-9C70EF8F1476"

        val user = User(
            ext = mapOf( // optional: when you use prebid sharedId for verification and also trigger ad rotations without prebid
                "eids" to listOf(
                    mapOf(
                        "source" to "pubcid.org",
                        "uids" to listOf(
                            mapOf(
                                "id" to sharedId,
                                "atype" to 1
                            )
                        )
                    )
                )
            ),
            id = null,
            buyerUid = null,
            yob = null,
            gender = null,
            keywords = null,
            customData = null,
        )


        AgmaSdk.getInstance().triggerEvent(user = user)
    }

}
