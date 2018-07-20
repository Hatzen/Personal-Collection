//
//  InternetAppAppDelegate.h
//  InternetApp
//
//  Created by Kai Hartz on 17.07.13.
//  Copyright Orderbase 2013. All rights reserved.
//

#import <UIKit/UIKit.h>

@class InternetAppViewController;

@interface InternetAppAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    InternetAppViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet InternetAppViewController *viewController;

@end

