// Copyright © 2024 Müller & Sohn Digitalmanufaktur GmbH. All rights reserved.

import SwiftUI
import AgmaSdkIos

@main
struct SampleAppApp: App {
        
    init() {
        AgmaSdk.shared.setConfig(AgmaSdk.Config(code: "provided-by-agma-please-change"))
        
#warning("!! FOR AGMA SDK TESTING PURPOSE ONLY !!")
        AgmaSdk.shared.setConsentString("CPyFwIAPyFwIACnABIDEDVCkAP_AAAAAAAYgJmJV9D7dbXFDcXx3SPt0OYwW1dBTKuQhAhSAA2AFVAOQ8JQA02EaMATAhiACEQIAolYBAAEEHAFUAEGQQIAEAAHsIgSEhAAKIABEEBEQAAIQAAoKAIAAEAAIgAABIgSAmBiQSdLkRUCAGIAwDgBYAqgBCIABAgMBBEAIABAIAIIIwygAAQBAAIIAAAAAARAAAgAAAAAAIAAAAABAAAASEgAwABBMwNABgACCZgiADAAEEzBUAGAAIJmDIAMAAQTMHQAYAAgmYQgAwABBMwlABgACCZhSADAAEEzA.f_gAAAAABcgAAAAA")
    }
    
    var body: some Scene {
        WindowGroup {
            Button(action: {
                AgmaSdk.shared.triggerEvent()
            }, label: { Text("Trigger Manual Event") })
        }
    }
}
