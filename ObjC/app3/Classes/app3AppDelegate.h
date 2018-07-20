//
//  app3AppDelegate.h
//  app3
//
//  Created by Kai Hartz on 15.07.13.
//  Copyright Orderbase 2013. All rights reserved.
//

#import <UIKit/UIKit.h>

@class app3ViewController;

@interface app3AppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    app3ViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet app3ViewController *viewController;

@end

