#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>

@interface bildadd : UIViewController
<UITextFieldDelegate,UINavigationBarDelegate, UINavigationControllerDelegate, UIImagePickerControllerDelegate > {
    IBOutlet id galerie;
    IBOutlet id kamera;
    IBOutlet id textField;
    IBOutlet id textView;
	UIButton* upload;
	IBOutlet UIScrollView* scrollView;
	UIImage* bild;
	UIAlertView *alert;
	
	UITextField* activeField;
}
- (IBAction)clickGalerie:(id)sender;
- (IBAction)clickKamera:(id)sender;
- (IBAction)clickUpload:(id)sender;
- (IBAction)clickVorschau:(id)sender;
- (IBAction)clickBack:(id)sender;
@end
