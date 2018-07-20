//
//  app2AppDelegate.h
//  app2
//
//  Created by Kai Hartz on 15.07.13.
//  Copyright Orderbase 2013. All rights reserved.
//

#import <UIKit/UIKit.h>

@class app2ViewController;

@interface app2AppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    app2ViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet app2ViewController *viewController;

@end

