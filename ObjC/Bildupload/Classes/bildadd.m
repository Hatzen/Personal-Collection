#import "bildadd.h"
#import "bildanzeige.h"
#import "objekt.h"
#import "Statics.h"

@implementation bildadd

- (void)viewDidLoad
{
    [super viewDidLoad];
	
	//Textfield Done-Button
	[textField setDelegate:self];
    [textField setReturnKeyType:UIReturnKeyDone];
    [textField addTarget:self action:@selector(textFieldFinished:) forControlEvents:UIControlEventEditingDidEndOnExit];
	//ENDE
	//ISNT AVAIABLE DISABLE
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
	//ENDE
	[scrollView setContentSize:CGSizeMake(scrollView.frame.size.width, upload.frame.size.height+upload.frame.origin.y+15)];
	UITapGestureRecognizer *recognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(touch)];
	[recognizer setNumberOfTapsRequired:2];
	[recognizer setNumberOfTouchesRequired:1];
	[scrollView addGestureRecognizer:recognizer];
	
	[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWasShown:) name:UIKeyboardDidShowNotification object:nil];
	[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillBeHidden:) name:UIKeyboardWillHideNotification object:nil];

}

- (void)keyboardWasShown:(NSNotification*)aNotification
{
    NSDictionary* info = [aNotification userInfo];
    CGSize kbSize = [[info objectForKey:UIKeyboardFrameBeginUserInfoKey] CGRectValue].size;
	
    UIEdgeInsets contentInsets = UIEdgeInsetsMake(0.0, 0.0, kbSize.height, 0.0);
    scrollView.contentInset = contentInsets;
    scrollView.scrollIndicatorInsets = contentInsets;
	
    // If active text field is hidden by keyboard, scroll it so it's visible
    CGRect aRect = self.view.frame; //ungenau da view noch {30px} titelleiste hat
    aRect.size.height -= kbSize.height;
    if (!CGRectContainsPoint(aRect, CGPointMake(activeField.frame.origin.x ,activeField.frame.origin.y+100)) ) {
        CGPoint scrollPoint = CGPointMake(0.0, activeField.frame.origin.y-kbSize.height+60);
        [scrollView setContentOffset:scrollPoint animated:YES];
    }
}

- (void)keyboardWillBeHidden:(NSNotification*)aNotification
{
    UIEdgeInsets contentInsets = UIEdgeInsetsZero;
    scrollView.contentInset = contentInsets;
    scrollView.scrollIndicatorInsets = contentInsets;
}

//Autorotate view
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
	NSLog(@"hallo");
	//NSLog(@"%f & %f", self.view.bounds.size.width, self.view.bounds.size.height);
	CGRect screenRect = [[UIScreen mainScreen] bounds];
	CGFloat screenWidth = screenRect.size.width;
	CGFloat screenHeight = screenRect.size.height;
	if (interfaceOrientation==UIInterfaceOrientationLandscapeLeft || interfaceOrientation==UIInterfaceOrientationLandscapeRight) {
        
		//screenWidth = screenRect.size.height;
		//screenHeight = screenRect.size.width;
		//[self.view setFrame:CGRectMake(0,0, 320, 440)];
		//[self.view.bounds size].width = ;
		//NSLog(@"positionY:%f",[upload frame].origin.y+25);
	}
	//NSLog(@"%f & %f", screenWidth, screenHeight);
	[scrollView setContentSize: CGSizeMake(screenWidth, screenHeight) ];
	return YES;
}
//ENDE

//Tap escapes every Keyboard
-(void)touch
{
	[[self view] endEditing:YES];
	[scrollView endEditing:YES];
}
//END


- (void)textFieldDidBeginEditing:(UITextField *)textField { 
	activeField = textField;
}

//Textfield Done-Button
-(BOOL) textFieldShouldReturn:(UITextField *)textfield {
    [textfield resignFirstResponder];
	activeField = nil;
    return YES;
}
//ENDE

- (IBAction)clickBack:(id)sender
{
    [self dismissModalViewControllerAnimated:YES];
}

//Bildauswahl
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
    [image retain];
	bild = image;
	NSLog(@"%@", image);
    [[picker parentViewController] dismissModalViewControllerAnimated:YES];
}

- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker {
    [[picker parentViewController] dismissModalViewControllerAnimated:YES];
}
//ENDE

- (IBAction)clickVorschau:(id)sender {
	objekt* obj = [[[objekt alloc] initWithContent: 1 : [textField text] : bild :  [textView text]] autorelease];
	if(obj != nil)
	{
	bildanzeige* screen = [[bildanzeige alloc] initWithNibName:nil bundle:nil];
	[[Statics class] randomTransition: screen];
	[self presentModalViewController:screen animated: YES];
	
	[screen setAnzeige: obj];
	[screen release];	
	}
	else
		NSLog(@"obj == nil .O");
}

- (IBAction)clickUpload:(id)sender {
	alert = [[[UIAlertView alloc] initWithTitle:@"Daten werden Hochgeladen\nBitte warten..." message:nil delegate:self cancelButtonTitle:nil otherButtonTitles: nil] autorelease];
	[alert show];
	
	UIActivityIndicatorView *indicator = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhiteLarge];
	
	// Adjust the indicator so it is up a few pixels from the bottom of the alert
	indicator.center = CGPointMake(alert.bounds.size.width / 2, alert.bounds.size.height - 50);
	[indicator startAnimating];
	[alert addSubview:indicator];
	[indicator release];
	
	// Dictionary that holds post parameters. You can set your post parameters that your server accepts or programmed to accept.
	NSMutableDictionary* _params = [[NSMutableDictionary alloc] init];
	[_params setObject:[NSString stringWithString:[[Statics class] pass]] forKey:[NSString stringWithString:@"pass"]];
	[_params setObject:[NSString stringWithString:[textField text]] forKey:[NSString stringWithString:@"name"]];
	[_params setObject:[NSString stringWithString:[textView text]] forKey:[NSString stringWithString:@"text"]];
	
	// the boundary string : a random string, that will not repeat in post data, to separate post data fields.
	NSString *BoundaryConstant = [NSString stringWithString:@"-------------V2ymHFg03jsadnjsdlkJASFJAnasfaehbqgZCaKO6jy"];
	
	// string constant for the post parameter 'file'. My server uses this name: `file`. Your's may differ 
	NSString* FileParamConstant = [NSString stringWithString:@"file"];
	
	// the server url to which the image (or the media) is uploaded. Use your server url here
	NSURL* requestURL = [NSURL URLWithString:@"http://hartzkai.freehostia.com/createObjekt.php"]; 
    // create request
	NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];                                    
	[request setCachePolicy:NSURLRequestReloadIgnoringLocalCacheData];
	[request setHTTPShouldHandleCookies:NO];
	[request setTimeoutInterval:30];
	[request setHTTPMethod:@"POST"];
	
	// set Content-Type in HTTP header
	NSString *contentType = [NSString stringWithFormat:@"multipart/form-data; boundary=%@", BoundaryConstant];
	[request setValue:contentType forHTTPHeaderField: @"Content-Type"];
	
	// post body
	NSMutableData *body = [NSMutableData data];
	
	// add params (all params are strings)
	for (NSString *param in _params) {
		[body appendData:[[NSString stringWithFormat:@"--%@\r\n", BoundaryConstant] dataUsingEncoding:NSUTF8StringEncoding]];
		[body appendData:[[NSString stringWithFormat:@"Content-Disposition: form-data; name=\"%@\"\r\n\r\n", param] dataUsingEncoding:NSUTF8StringEncoding]];
		[body appendData:[[NSString stringWithFormat:@"%@\r\n", [_params objectForKey:param]] dataUsingEncoding:NSUTF8StringEncoding]];
	}
	
	// add image data
	NSData *imageData = UIImageJPEGRepresentation(bild , 1.0);
	if (imageData) {
		[body appendData:[[NSString stringWithFormat:@"--%@\r\n", BoundaryConstant] dataUsingEncoding:NSUTF8StringEncoding]];
		[body appendData:[[NSString stringWithFormat:@"Content-Disposition: form-data; name=\"%@\"; filename=\"image.jpg\"\r\n", FileParamConstant] dataUsingEncoding:NSUTF8StringEncoding]];
		[body appendData:[[NSString stringWithString:@"Content-Type: image/jpeg\r\n\r\n"] dataUsingEncoding:NSUTF8StringEncoding]];
		[body appendData:imageData];
		[body appendData:[[NSString stringWithFormat:@"\r\n"] dataUsingEncoding:NSUTF8StringEncoding]];
	}
	
	[body appendData:[[NSString stringWithFormat:@"--%@--\r\n", BoundaryConstant] dataUsingEncoding:NSUTF8StringEncoding]];
	
	// setting the body of the post to the reqeust
	[request setHTTPBody:body];
	
	// set the content-length
	NSString *postLength = [NSString stringWithFormat:@"%d", [body length]];
	[request setValue:postLength forHTTPHeaderField:@"Content-Length"];
	
	// set URL
	[request setURL:requestURL];
	
	NSURLConnection *urlConnection = [[NSURLConnection alloc] initWithRequest:request delegate:self startImmediately:YES];
	if(urlConnection)
	{
		
	}
	else
	{
		[[Statics class] showError:@"Upload Fehlgeschlagen :("];
	}
	
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection
{
	[alert dismissWithClickedButtonIndex:0 animated:YES];
    [self dismissModalViewControllerAnimated:YES];
	
}

- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error
{
	[[Statics class] showError:@"Upload Fehlgeschlagen :("];
	NSLog(@"%@" ,error);
	//[[Statics class] showError:[NSString stringWithString:[@"%@", error ]]];
}

@end
