#import "Statics.h"


@implementation Statics

static const NSString* pass = @"e22a63fb76874c99488435f26b117e37";

+ (NSString*) pass
{
    return pass;
}

+ (void) randomTransition:(UIViewController* )view
{
	switch ( 1 + arc4random() % (4-1)) {
		case 1:
			view.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;
			break;
		case 2:
			view.modalTransitionStyle =UIModalTransitionStyleFlipHorizontal;
			break;
		case 3:
			view.modalTransitionStyle = UIModalTransitionStyleCoverVertical;
			break;
	}
}

+ (void) showError:(NSString*) fehler
{
	UIAlertView *alert = [[[UIAlertView alloc] initWithTitle:@"Fehler." message:fehler delegate:self cancelButtonTitle:@"OK" otherButtonTitles: nil] autorelease];
	[alert show];
}
@end
