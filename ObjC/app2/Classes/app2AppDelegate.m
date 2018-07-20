//
//  app2AppDelegate.m
//  app2
//
//  Created by Kai Hartz on 15.07.13.
//  Copyright Orderbase 2013. All rights reserved.
//

#import "app2AppDelegate.h"
#import "app2ViewController.h"

@implementation app2AppDelegate

@synthesize window;
@synthesize viewController;


- (void)applicationDidFinishLaunching:(UIApplication *)application {    
    
    // Override point for customization after app launch    
    [window addSubview:viewController.view];
    [window makeKeyAndVisible];
}


- (void)dealloc {
    [viewController release];
    [window release];
    [super dealloc];
}


@end
