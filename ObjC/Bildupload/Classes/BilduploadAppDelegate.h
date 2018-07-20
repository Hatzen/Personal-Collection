//
//  BilduploadAppDelegate.h
//  Bildupload
//
//  Created by Kai Hartz on 17.07.13.
//  Copyright Orderbase 2013. All rights reserved.
//

#import <UIKit/UIKit.h>

@class BilduploadViewController;

@interface BilduploadAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    BilduploadViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet BilduploadViewController *viewController;

@end

