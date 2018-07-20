#import <UIKit/UIKit.h>


@interface bildVorschau : UIViewController {
	IBOutlet id imageView;
	IBOutlet id label;
}

- (IBAction)clickBack:(id)sender;

- (void) setValues:(NSString*) text : (UIImage*) image;

@end
