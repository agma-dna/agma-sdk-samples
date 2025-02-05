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
                AgmaSdk.shared.setId5Config(AgmaSdk.Id5Config(partner: 1437, hem: "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08", phone: "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08", domain: nil))
            }, label: { Text("Trigger Manual Event") })
        }
    }
}
