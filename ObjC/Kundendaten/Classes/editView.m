#import "editView.h"

@implementation editView

- (void)viewDidLoad {
    [super viewDidLoad];
	for(int i = 0 ; i < [titels count]; i++)
	{
		CGRect rect = CGRectMake(15, 15+80*i, [self view].frame.size.width-30 , 18);
		UILabel* label = [[UILabel alloc] initWithFrame:rect];
		[label setText: [titels objectAtIndex:i]];
		
		CGRect rect2 = CGRectMake(18, 45+80*i, [self view].frame.size.width-36 , 30);
		UITextField* textField = [[UITextField alloc] initWithFrame:rect2];
		[textField setDelegate:self];
		[textField setReturnKeyType:UIReturnKeyDone];
		[textField setBorderStyle:UITextBorderStyleRoundedRect];
		if(daten != nil)
		{
			[textField setText: [daten objectAtIndex:i]];
		}
		
		[scrollView addSubview:textField];
		[scrollView addSubview:label];
	}UIButton *save = [UIButton buttonWithType:UIButtonTypeRoundedRect];
	[save addTarget:self action:@selector(goSave) forControlEvents:UIControlEventTouchDown];
	[save setTitle:@"Speichern" forState:UIControlStateNormal];
	save.frame = CGRectMake(15, 15+80*[titels count], [self view].frame.size.width-30 , 40);
	[scrollView addSubview:save];
	UIButton *back = [UIButton buttonWithType:UIButtonTypeRoundedRect];
	[back addTarget:self action:@selector(goBack) forControlEvents:UIControlEventTouchUpInside];
	[back setTitle:@"Zurück" forState:UIControlStateNormal];
	back.frame = CGRectMake(15, 60+80*[titels count], [self view].frame.size.width-30 , 40);
	[scrollView addSubview:back];
	[scrollView setContentSize:CGSizeMake(scrollView.frame.size.width, back.frame.size.height+back.frame.origin.y+15)];
	UITapGestureRecognizer *recognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(touch)];
	[recognizer setNumberOfTapsRequired:2];
	[recognizer setNumberOfTouchesRequired:1];
	[scrollView addGestureRecognizer:recognizer];
	
	if(daten == nil || [daten count] == 0)
	{
		[head setTitle:@"Hinzufügen"];
		for(id x in [scrollView subviews]){
			if([x isKindOfClass:[UITextField class]])
			{
				[x setEnabled:YES];
				break;
			}
		}	
	}
	else {
		[head setTitle:@"Bearbeiten"];
		for(id x in [scrollView subviews]){
			if([x isKindOfClass:[UITextField class]])
			{
				[x setEnabled:NO];
				break;
			}
		}		
	}
	
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWasShown:) name:UIKeyboardDidShowNotification object:nil];
	[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillBeHidden:) name:UIKeyboardWillHideNotification object:nil];
}

- (void) goBack
{
	[self dismissModalViewControllerAnimated:YES];
}

-(void) fillData
{
	daten = [[NSMutableArray alloc ] init];
	for(id x in [scrollView subviews]){
        if([x isKindOfClass:[UITextField class]])
		{
            [daten addObject:[x text]];
		}
    }
}

-(void) goSave
{
	if(daten == nil || [daten count] == 0)
	{
		[self fillData];
		if(![[[KundendatenViewController class] connect] insertInto:titels :daten])
		{
			UIAlertView *message = [[UIAlertView alloc] initWithTitle:@"Fehler beim Eintragen"
															  message:@"Überprüfen sie ggf. den Inhalt der Felder."
														 delegate:nil
												cancelButtonTitle:@"Okay"
												otherButtonTitles:nil];
			[message show];
		}
		else {
			[self dismissModalViewControllerAnimated:YES];
		}
	}
	else {
		[self fillData];
		if(![[[KundendatenViewController class] connect] update:titels :daten])
		{
			UIAlertView *message = [[UIAlertView alloc] initWithTitle:@"Fehler beim Eintragen"
															  message:@"Überprüfen sie ggf. den Inhalt der Felder."
															 delegate:nil
													cancelButtonTitle:@"Okay"
													otherButtonTitles:nil];
			[message show];
		}
		else {
			[self dismissModalViewControllerAnimated:YES];
		}
	}
}

- (void) setValues:(NSMutableArray*) titels_tmp: (NSMutableArray*) daten_tmp
{ 
	titels = titels_tmp;
	daten = daten_tmp;
}

//Tap escapes every Keyboard
-(void)touch
{
	[[self view] endEditing:YES];// this will do the trick
	[scrollView endEditing:YES];
}
//END

//Textfield Done-Button
-(BOOL) textFieldShouldReturn:(UITextField *)textfield { 
	activeField = nil;
    [textfield resignFirstResponder];
    return YES;
}
//ENDE

- (void)textFieldDidBeginEditing:(UITextField *)textField { 
	activeField = textField;
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




/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations.
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
*/

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (void)viewDidUnload {
    [super viewDidUnload];
}


- (void)dealloc {
    [super dealloc];
}


@end
