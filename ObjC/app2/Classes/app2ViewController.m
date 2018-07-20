#import "app2ViewController.h"

@implementation app2ViewController
- (IBAction)weiter:(id)sender {
    secondView* screen = [[secondView alloc] initWithNibName:nil bundle:nil];
	screen.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;
	[self presentModalViewController:screen animated: YES];
	NSLog(@"hallo");
	NSLog(@"%@", screen);
	[screen release];
	NSLog(@"%@", screen);
}
@end
