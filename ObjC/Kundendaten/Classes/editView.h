#import <UIKit/UIKit.h>
#import "KundendatenViewController.h"

@interface editView : UIViewController 
<UITextFieldDelegate>{
	NSMutableArray* titels;
	NSMutableArray* daten;
	
	IBOutlet UIScrollView* scrollView;
	IBOutlet id head;
	UITextField* activeField;
}

- (void) setValues:(NSMutableArray*) titels_tmp: (NSMutableArray*) daten_tmp;

@end
