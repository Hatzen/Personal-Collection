#import "imageZoomed.h"


@implementation imageZoomed

- (void) setImage: (UIImage*) image
{
	[scrollView setContentSize: [image size]];
	imageView = [[UIImageView alloc] initWithImage:image];
	imageView.frame = CGRectMake(0, 0, [image size].width , [image size].height );
	[scrollView addSubview:imageView];
}

- (IBAction)back:(id)sender {
    [self dismissModalViewControllerAnimated:YES];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
	return YES;
}


@end
