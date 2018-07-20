//
//  KundendatenAppDelegate.h
//  Kundendaten
//
//  Created by Kai Hartz on 29.07.13.
//  Copyright Orderbase 2013. All rights reserved.
//

#import <UIKit/UIKit.h>

@class KundendatenViewController;

@interface KundendatenAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    KundendatenViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet KundendatenViewController *viewController;

@end

