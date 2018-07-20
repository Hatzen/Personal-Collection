#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>
#import "objekt.h"

@interface bildanzeige : UIViewController {
    IBOutlet id imageview;
    IBOutlet id textview;
    IBOutlet id titel;
	int identi;
	UIAlertView* alert;
}
- (IBAction)zuruck:(id)sender;
- (IBAction)remove:(id)sender;
- (void) setAnzeige:(objekt*) anzeige;

@end
