import AgmaSdkIos
import Foundation
import PrebidMobile

class EventDelegate: NSObject, @preconcurrency PrebidEventDelegate {
    @MainActor func prebidBidRequestDidFinish(requestData: Data?, responseData: Data?) {
        AgmaSdk.shared.didReceivePrebid(request: requestData, response: responseData)
    }
}
