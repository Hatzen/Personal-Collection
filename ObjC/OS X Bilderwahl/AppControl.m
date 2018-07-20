#import "AppControl.h"

@implementation AppControl
- (IBAction)auswahl:(id)sender {
    NSOpenPanel* openDlg = [NSOpenPanel openPanel];
	[openDlg setCanChooseFiles:YES];
	[openDlg setAllowsMultipleSelection:NO];
	[openDlg setCanChooseDirectories:NO];
	[openDlg setTitle:@"Bild auswahl"];
	//[openDlg setAllowedFileTypes:[NSArray arrayWithObjects:@"png",@"jpg",@"gif",nil]]; // Funktioniert erst bei neueren versionen?
	if ( [openDlg runModalForTypes:[NSArray arrayWithObjects:@"png",@"jpg",@"gif",nil]] == NSOKButton )
	{
		[datei setStringValue:[[openDlg filenames] objectAtIndex:0]];
		//NSLog(@"%@",path);
	}    
}

- (IBAction)upload:(id)sender {
	if([[datei stringValue] length] != 0 && [[bildunterschrift stringValue] length] != 0)
	{
		[fehler setHidden:YES];
		[label setStringValue:[bildunterschrift stringValue]];
	
		//NSLog(@"%@",[datei stringValue]);
		NSImage* background = [[NSImage alloc] initWithContentsOfFile:[datei stringValue]];	
		if(background == nil)
		{
			[fehler setHidden:NO];
			[fehler setStringValue:@"Kein Bild!"];
		}
		else
		{
			[fehler setHidden:YES];
			//NSLog(@"%@",background);
			[image setImage:background];
			[frame orderFront:sender];
		}   
	}    	
	else
	{
		[fehler setHidden:NO];
		[fehler setStringValue:@"Feld(er) Leer!"];
	}    	
}
@end
