//
//  app1AppDelegate.h
//  app1
//
//  Created by Kai Hartz on 15.07.13.
//  Copyright Orderbase 2013. All rights reserved.
//

#import <UIKit/UIKit.h>

@class app1ViewController;

@interface app1AppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    app1ViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet app1ViewController *viewController;

@end

