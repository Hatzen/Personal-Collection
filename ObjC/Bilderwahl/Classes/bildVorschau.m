#import "bildVorschau.h"


@implementation bildVorschau


- (IBAction)clickBack:(id)sender
{
    [self dismissModalViewControllerAnimated:YES];
}

- (void) setValues:  (NSString*) text: (UIImage*) image
{
	[imageView setImage: image];
	[label setText: text];
}


@end
