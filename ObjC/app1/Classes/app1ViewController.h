#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>

@interface app1ViewController : UIViewController 
<UITextFieldDelegate>
{	
    IBOutlet id textanzeige;
    IBOutlet UITextField *textfield;
}
- (IBAction)anzeigen:(id)sender;
@end
