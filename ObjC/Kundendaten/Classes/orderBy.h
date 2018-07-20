#import <UIKit/UIKit.h>
#import "KundendatenViewController.h"


@interface orderBy : UIViewController <UIPickerViewDelegate, UIPickerViewDataSource>
{
	IBOutlet UITextField* element;
	IBOutlet UISegmentedControl* reihenfolge;
	NSMutableArray* titelStrings;
}

- (IBAction)clickBack:(id)sender;
- (void) setValues: (NSMutableArray*) titels;

@end
