import AgmaSdkIos
import SwiftUI

@main
struct SampleAppApp: App {
    init() {
        // optional: when you use sharedId for prebid
        let sharedId = "E4D65283-171B-4559-89F1-9C70EF8F1476"

        AgmaSdk.configure(config: AgmaSdk.Config(
            code: "provided-by-agma-please-change",
            // This should match your Prebid Ortb2 App Config
            app: AgmaSdkIos.Ortb2.App(
                id: nil,
                name: "SampleApp",
                bundle: Bundle.main.bundleIdentifier,
                domain: "app.domain",
                storeurl: nil,
                cat: nil,
                sectioncat: nil,
                pagecat: nil,
                ver: Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String,
                privacypolicy: nil,
                paid: nil,
                keywords: nil,
                ext: nil
            ), user: AgmaSdkIos.Ortb2.User(
                id: nil,
                buyeruid: nil,
                yob: nil,
                gender: nil,
                keywords: nil,
                customdata: nil,
                ext: AnyCodable([ // optional: when you use prebid sharedId for verification and also trigger ad rotations without prebid
                    "eids": [
                        [
                            "source": "pubcid.org",
                            "uids": [
                                [
                                    "atype": 1,
                                    "id": sharedId
                                ]
                            ]
                        ]
                    ]
                ])
            )
        ))

        #warning("!! FOR AGMA SDK TESTING PURPOSE ONLY !!")
        AgmaSdk.shared.setConsentString("CPyFwIAPyFwIACnABIDEDVCkAP_AAAAAAAYgJmJV9D7dbXFDcXx3SPt0OYwW1dBTKuQhAhSAA2AFVAOQ8JQA02EaMATAhiACEQIAolYBAAEEHAFUAEGQQIAEAAHsIgSEhAAKIABEEBEQAAIQAAoKAIAAEAAIgAABIgSAmBiQSdLkRUCAGIAwDgBYAqgBCIABAgMBBEAIABAIAIIIwygAAQBAAIIAAAAAARAAAgAAAAAAIAAAAABAAAASEgAwABBMwNABgACCZgiADAAEEzBUAGAAIJmDIAMAAQTMHQAYAAgmYQgAwABBMwlABgACCZhSADAAEEzA.f_gAAAAABcgAAAAA")
    }

    var body: some Scene {
        WindowGroup {
            Button(action: {
                // Please check docs,triggerEvent is only needed when not used with prebid
                AgmaSdk.shared.triggerEvent()
            }, label: { Text("Trigger Manual Event") })
        }
    }
}
