//
//  PushService.swift
//  iosApp
//
//  Created by SentsovSA on 20.09.2024.
//  Copyright © 2024 group.ost. All rights reserved.
//

import Foundation
import UIKit
import FirebaseMessaging
import Firebase

class PushService: NSObject, UNUserNotificationCenterDelegate {

    func setup(
        for application: UIApplication
    ) {
        UNUserNotificationCenter.current().delegate = self
        let authOptions: UNAuthorizationOptions = [.alert, .badge, .sound]
        
        UNUserNotificationCenter.current().requestAuthorization(
            options: authOptions,
            completionHandler: { _,_ in })
        
        application.registerForRemoteNotifications()
        
        
    }
}
