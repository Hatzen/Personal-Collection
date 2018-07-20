#import "bildanzeige.h"
#import "Statics.h"
#import "imageZoomed.h"

@implementation bildanzeige

-(void)touchesBegan:(NSSet*)touches withEvent:(UIEvent*)event //here enable the touch       
{
	// get touch event
	UITouch *touch = [[event allTouches] anyObject];
	
	CGPoint touchLocation = [touch locationInView:self.view];
	if (CGRectContainsPoint([imageview frame], touchLocation))
	{
		
		imageZoomed* screen = [[imageZoomed alloc] initWithNibName:nil bundle:nil];
		[[Statics class] randomTransition: screen];
		[self presentModalViewController:screen animated: YES];
		
		[screen setImage: [imageview image]];
		[screen release];	
	}
}

- (IBAction)remove:(id)sender {
	alert = [[[UIAlertView alloc] initWithTitle:@"Objekt wird gelöscht\nBitte warten..." message:nil delegate:self cancelButtonTitle:nil otherButtonTitles: nil] autorelease];
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
	[_params setObject:[NSString stringWithFormat:@"%i",identi] forKey:[NSString stringWithString:@"id"]];
	
	// the boundary string : a random string, that will not repeat in post data, to separate post data fields.
	NSString *BoundaryConstant = [NSString stringWithString:@"-------------V2ymHFg03jsadnjsdlkJASFJAnasfaehbqgZCaKO6jy"];
	
	// the server url to which the image (or the media) is uploaded. Use your server url here
	NSURL* requestURL = [NSURL URLWithString:@"http://hartzkai.freehostia.com/delete.php"]; 
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

- (IBAction)zuruck:(id)sender {
    [self dismissModalViewControllerAnimated:YES];
}

- (void) setAnzeige:(objekt*) anzeige
{
	[imageview setContentMode : UIViewContentModeScaleAspectFit];
	[titel setTitle:[anzeige titel]];
	[textview setText:[anzeige text]];
	[imageview setImage:[anzeige bild]];
	identi = [anzeige ident];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
	return YES;
}
@end
