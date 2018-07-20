#import <UIKit/UIKit.h>
#import "dbConnect.h"
#import "editView.h"
#import "orderBy.h"
#import "tabellenAuswahl.h"

@interface KundendatenViewController : UIViewController < UITableViewDelegate, UITableViewDataSource, UITabBarDelegate, UIAlertViewDelegate> {
	NSMutableArray* contentStrings;
	NSMutableArray* titelStrings;
	NSMutableArray* cellWidths;
	
	IBOutlet id tabelle;
	IBOutlet id scrollView;
	IBOutlet id head;
	IBOutlet id tabBar;
	
	UITextField* sortierenNach;
	UIAlertView* anzeigeOptionen;
	
	int selected;
}

- (void) calculateCellWidth;
- (int) sum:(int) to;
+ (dbConnect*) connect;
- (void) refresh;
- (void) refreshViews;
- (void) refreshTitelBar;
- (void) refreshCell:(UITableViewCell*) cell: (NSIndexPath *)indexPath;


@end

