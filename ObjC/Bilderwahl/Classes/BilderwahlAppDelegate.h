//
//  BilderwahlAppDelegate.h
//  Bilderwahl
//
//  Created by Kai Hartz on 31.07.13.
//  Copyright 2013 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class BilderwahlViewController;

@interface BilderwahlAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    BilderwahlViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet BilderwahlViewController *viewController;

@end

