#import <UIKit/UIKit.h>


@interface tabellenAuswahl : UIViewController < UITableViewDelegate, UITableViewDataSource> {

	NSMutableArray* elemente;
}

- (void) setValues: (NSMutableArray*) elemente2;
- (IBAction)clickBack:(id)sender;

@end
