//
//  SampleAppApp.swift
//  SampleApp
//

import AgmaSdkIos
import PrebidMobile
import SwiftUI

private let nativeStoredImpression = "prebid-demo-banner-native-styles"

@main
struct SampleAppApp: App {
    let eventDelegate = EventDelegate()
    
    init() {
        AgmaSdk.configure(config: AgmaSdk.Config(
            code: "provided-by-agma-please-change",
            loggingEnabled: true,
            id5Config: AgmaSdk.Id5Config(
                appConfig: AgmaSdk.Id5Config.AppConfig(bundleIdentifier: "my.bundle.identifier", appVersion: "1.0.0", appName: "my appname"),
                partner: 1234, // Please use your ID5 Config
                domain: "example.com",
            )
        ))
            
        // can run later
        #warning("!! FOR AGMA SDK TESTING PURPOSE ONLY !!")
        AgmaSdk.shared.setConsentString("CPyFwIAPyFwIACnABIDEDVCkAP_AAAAAAAYgJmJV9D7dbXFDcXx3SPt0OYwW1dBTKuQhAhSAA2AFVAOQ8JQA02EaMATAhiACEQIAolYBAAEEHAFUAEGQQIAEAAHsIgSEhAAKIABEEBEQAAIQAAoKAIAAEAAIgAABIgSAmBiQSdLkRUCAGIAwDgBYAqgBCIABAgMBBEAIABAIAIIIwygAAQBAAIIAAAAAARAAAgAAAAAAIAAAAABAAAASEgAwABBMwNABgACCZgiADAAEEzBUAGAAIJmDIAMAAQTMHQAYAAgmYQgAwABBMwlABgACCZhSADAAEEzA.f_gAAAAABcgAAAAA")

        /***
            This is taken from the PrebidMobile SDK's Sample App and only serves as a technical reference on how to integrate the AGMA SDK using PrebidMobile.
            Once the `prebidBidRequestDidFinish(_:,_:)` delegate function is called, it will onvoke the AGMA SDK's `didReceivePrebid(_:,_:)` func and will pass on the request and response payloads.
         */
        
        Prebid.shared.prebidServerAccountId = "0689a263-318d-448b-a3d4-b02e8a709d9d"
        try! Prebid.shared.setCustomPrebidServer(url: "https://prebid-server-test-j.prebid.org/openrtb2/auction")
        Prebid.shared.eventDelegate = eventDelegate

        // make sure you enable SharedId
        Targeting.shared.sendSharedId = true
    }
    
    var body: some Scene {
        WindowGroup {
            VStack(spacing: 20) {
                Button(action: {
                    createAd()
                }, label: { Text("Load Ad") })
                    .buttonStyle(.borderedProminent)
            }
        }
    }
    
    func createAd() {
        // Prebid
        let nativeRequestAssets: [NativeAsset] = {
            let image = NativeAssetImage(minimumWidth: 200, minimumHeight: 50, required: true)
            image.type = ImageAsset.Main
            
            let icon = NativeAssetImage(minimumWidth: 20, minimumHeight: 20, required: true)
            icon.type = ImageAsset.Icon
            
            let title = NativeAssetTitle(length: 90, required: true)
            let body = NativeAssetData(type: DataAsset.description, required: true)
            let cta = NativeAssetData(type: DataAsset.ctatext, required: true)
            let sponsored = NativeAssetData(type: DataAsset.sponsored, required: true)
            
            return [title, icon, image, sponsored, body, cta]
        }()
        
        // 1. Create a NativeRequest
        let nativeUnit = NativeRequest(configId: nativeStoredImpression, assets: nativeRequestAssets)
        
        // 2. Configure the NativeRequest
        nativeUnit.context = ContextType.Social
        nativeUnit.placementType = PlacementType.FeedContent
        nativeUnit.contextSubType = ContextSubType.Social
        nativeUnit.eventtrackers = [NativeEventTracker(event: EventType.Impression, methods: [EventTracking.Image, EventTracking.js])]
        nativeUnit.addUserData([.init(jsonDictionary: ["id": "123"])!]) // Add user data
        
        // 4. Make a bid request to Prebid Server
        nativeUnit.fetchDemand { _ in }
    }
}
