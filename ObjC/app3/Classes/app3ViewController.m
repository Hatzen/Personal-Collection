#import "app3ViewController.h"

@implementation app3ViewController
- (void)viewDidLoad
{
    [super viewDidLoad];
	if (![UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera])
	{
		[kamera setAlpha :0.5f];
		[kamera setEnabled:NO];
	}
	if (![UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypePhotoLibrary])
	{
		[kamera setAlpha :0.5f];
		[galerie setEnabled:NO];
	}
	[imageview setContentMode : UIViewContentModeScaleAspectFit];
}

- (IBAction)clickGalerie:(id)sender {
    if( ![UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypePhotoLibrary] ) return;
	
    UIImagePickerController *imagePickerController = [[UIImagePickerController alloc] init];
    imagePickerController.delegate = self;
    imagePickerController.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
    imagePickerController.allowsImageEditing = YES;
    [self presentModalViewController:imagePickerController animated:YES];
}

- (IBAction)clickKamera:(id)sender {
    if( ![UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera] ) return;
	
    UIImagePickerController *imagePickerController = [[UIImagePickerController alloc] init];
    imagePickerController.delegate = self;
    imagePickerController.sourceType = UIImagePickerControllerSourceTypeCamera;
    imagePickerController.allowsImageEditing = YES;
    [self presentModalViewController:imagePickerController animated:YES];
}

- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingImage:(UIImage *)image editingInfo:(NSDictionary *)editingInfo {
    [imageview setImage:image];
	NSLog(@"%@", image);
    [[picker parentViewController] dismissModalViewControllerAnimated:YES];
}

- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker {
    [[picker parentViewController] dismissModalViewControllerAnimated:YES];
}

@end
