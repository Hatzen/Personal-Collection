#import "BilderwahlViewController.h"

@implementation BilderwahlViewController

- (void)viewDidLoad {
    [super viewDidLoad];
	[unterschrift setDelegate:self];
    [unterschrift setReturnKeyType:UIReturnKeyDone];
    [unterschrift addTarget:self action:@selector(textFieldFinished:) forControlEvents:UIControlEventEditingDidEndOnExit];
	if (![UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera])
	{
		[kamera setAlpha :0.5f];
		[kamera setEnabled:NO];
	}
	if (![UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypePhotoLibrary])
	{
		[galerie setAlpha :0.5f];
		[galerie setEnabled:NO];
	}
	
}

-(BOOL) textFieldShouldReturn:(UITextField *)textfield {
    [textfield resignFirstResponder];
    return YES;
}

//Bildauswahl
- (IBAction)clickGalerie:(id)sender {
    if( ![UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypePhotoLibrary] ) return;
	
    UIImagePickerController *imagePickerController = [[UIImagePickerController alloc] init];
    imagePickerController.delegate = self;
    imagePickerController.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
	[self presentModalViewController:imagePickerController animated:YES];
}

- (IBAction)clickKamera:(id)sender {
    if( ![UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera] ) return;
	
    UIImagePickerController *imagePickerController = [[UIImagePickerController alloc] init];
    imagePickerController.delegate = self;
    imagePickerController.sourceType = UIImagePickerControllerSourceTypeCamera;
    imagePickerController.allowsEditing = YES;
    [self presentModalViewController:imagePickerController animated:YES];
}

- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingImage:(UIImage *)image editingInfo:(NSDictionary *)editingInfo {
    [image retain];
	bild = image;
	[error setHidden:YES];
    [[picker parentViewController] dismissModalViewControllerAnimated:YES];
}

- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker {
    [[picker parentViewController] dismissModalViewControllerAnimated:YES];
}
//ENDE
- (void) randomTransition:(UIViewController* )view
{
	switch ( 1 + arc4random() % (4-1)) {
		case 1:
			view.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;
			break;
		case 2:
			view.modalTransitionStyle =UIModalTransitionStyleFlipHorizontal;
			break;
		case 3:
			view.modalTransitionStyle = UIModalTransitionStyleCoverVertical;
			break;
	}
}


- (IBAction)clickVorschau:(id)sender {
	if( bild != nil)
	{
		bildVorschau* screen = [[bildVorschau alloc] initWithNibName:nil bundle:nil];
		[self randomTransition: screen];
		[self presentModalViewController:screen animated: YES];
		
		[screen setValues: [unterschrift text] : bild];
		[screen release];
	}
	else {
		[error setText:@"Bitte erst ein Bild auswählen!"];
		[error setHidden:NO];
	}

}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (void)viewDidUnload {
}


- (void)dealloc {
    [super dealloc];
}

@end
