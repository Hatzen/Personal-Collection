#import <UIKit/UIKit.h>
#import "bildVorschau.h"

@interface BilderwahlViewController : UIViewController <UITextFieldDelegate,UINavigationBarDelegate, UINavigationControllerDelegate, UIImagePickerControllerDelegate >{
	IBOutlet id error;
	IBOutlet id vorschau;
	IBOutlet id kamera;
	IBOutlet id galerie;
	IBOutlet id unterschrift;
	
	UIImage* bild;
	
}

- (IBAction)clickGalerie:(id)sender;
- (IBAction)clickKamera:(id)sender;
- (IBAction)clickVorschau:(id)sender;

@end

