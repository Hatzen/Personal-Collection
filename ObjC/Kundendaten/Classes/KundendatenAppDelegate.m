//
//  KundendatenAppDelegate.m
//  Kundendaten
//
//  Created by Kai Hartz on 29.07.13.
//  Copyright Orderbase 2013. All rights reserved.
//

#import "KundendatenAppDelegate.h"
#import "KundendatenViewController.h"

@implementation KundendatenAppDelegate

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
