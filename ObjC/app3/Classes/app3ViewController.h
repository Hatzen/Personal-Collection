#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>

@interface app3ViewController : UIViewController 
<UINavigationBarDelegate> {
    IBOutlet id galerie;
    IBOutlet id imageview;
    IBOutlet id kamera;
}
- (IBAction)clickGalerie:(id)sender;
- (IBAction)clickKamera:(id)sender;
@end
