import SwiftUI
import app
import FirebaseCore
import FirebaseMessaging

class AppDelegate: UIResponder, UIApplicationDelegate, UNUserNotificationCenterDelegate, MessagingDelegate {
    var initialDeeplinkUrl: String?
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        setupFirebase()
        
            UNUserNotificationCenter.current().delegate = self
            registerForPushNotifications()
            
            return true
        }
        
        func registerForPushNotifications() {
            UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .sound, .badge]) { granted, error in
                print("Permission granted: \(granted)")
                guard granted else { return }
                DispatchQueue.main.async {
                    print("Attempting to register for remote notifications...")
                    UIApplication.shared.registerForRemoteNotifications()
                }
            }
        }
        
        func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
            print("Device Token: \(deviceToken.map { String(format: "%02.2hhx", $0) }.joined())")
            Messaging.messaging().apnsToken = deviceToken
        }
        
        func application(_ application: UIApplication, didFailToRegisterForRemoteNotificationsWithError error: Error) {
            print("Failed to register: \(error)")
        }
    
    func userNotificationCenter(_ center: UNUserNotificationCenter, willPresent notification: UNNotification, withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
            let userInfo = notification.request.content.userInfo
            print("Foreground Notification Received: \(userInfo)")
            
            completionHandler([.banner, .badge, .sound])
        }
    
    func messaging(_ messaging: Messaging, didReceiveRegistrationToken fcmToken: String?) {
           guard let fcmToken = fcmToken else {
               print("Failed to retrieve FCM token.")
               return
           }
           print("FCM Token: \(fcmToken)")
       }
    
    private func setupFirebase() {
        FirebaseApp.configure()
        Messaging.messaging().delegate = self
    }

}

@main
struct iOSApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
