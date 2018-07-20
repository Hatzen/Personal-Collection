//
//  app1AppDelegate.m
//  app1
//
//  Created by Kai Hartz on 15.07.13.
//  Copyright Orderbase 2013. All rights reserved.
//

#import "app1AppDelegate.h"
#import "app1ViewController.h"

@implementation app1AppDelegate

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
