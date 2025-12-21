import AgmaSdkIos
import SwiftUI

@main
struct SampleAppApp: App {
    init() {
        AgmaSdk.configure(config: AgmaSdk.Config(
            code: "provided-by-agma-please-change",
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
            ),
            user: AgmaSdkIos.Ortb2.User(
                id: nil,
                buyeruid: nil,
                yob: nil,
                gender: nil,
                keywords: nil,
                customdata: nil,
                ext: nil
            ),
            loggingEnabled: true,
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
