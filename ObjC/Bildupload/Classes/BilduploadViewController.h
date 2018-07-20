#import <UIKit/UIKit.h>
#import "store.h"

@interface BilduploadViewController : UIViewController
<UITableViewDelegate, UITableViewDataSource>{
	store* lager;
	NSMutableArray* daten;
	NSArray* array;
	UIAlertView* alert;
	IBOutlet id table;
}

- (IBAction)add:(id)sender;
- (IBAction)refresh:(id)sender;
- (void) loadData;
- (void) performBackground;
- (void) exit;

@end

