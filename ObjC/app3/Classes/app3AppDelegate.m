//
//  app3AppDelegate.m
//  app3
//
//  Created by Kai Hartz on 15.07.13.
//  Copyright Orderbase 2013. All rights reserved.
//

#import "app3AppDelegate.h"
#import "app3ViewController.h"

@implementation app3AppDelegate

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
