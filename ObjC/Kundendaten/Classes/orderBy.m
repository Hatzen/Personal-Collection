#import "orderBy.h"


@implementation orderBy

- (void)viewDidLoad {
    [super viewDidLoad];
	UIPickerView* picker = [[UIPickerView alloc] initWithFrame:CGRectMake(0, 480, 320, 270)];
	picker.delegate = self;
	picker.dataSource = self;
	picker.showsSelectionIndicator = YES;
	
	[element becomeFirstResponder];
	element.inputView = picker;
	element.contentVerticalAlignment = UIControlContentVerticalAlignmentCenter;
	element.textAlignment = UITextAlignmentCenter;
	[element setText:[titelStrings objectAtIndex:0]];
}

- (NSString*) createOrder
{
	if(![[element text] isEqualToString:@"" ])
	{
		NSString* aufab;
		if(reihenfolge.selectedSegmentIndex == 0)
			aufab = [[NSString alloc] initWithString:@" ASC"];
		else {
			aufab = [[NSString alloc] initWithString:@" DESC"];
		}
	
		NSString* order = [[NSString alloc] initWithFormat:@" ORDER BY %@ %@",[element text], aufab];
		[aufab release];
		return order;
	}
	return @"";
}


- (void) setValues: (NSMutableArray*) titels
{
	titelStrings = titels;
}

- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)thePickerView {
	
	return 1;
}

- (NSString *)pickerView:(UIPickerView *)thePickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component {
	return [titelStrings objectAtIndex:row];
}

- (void)pickerView:(UIPickerView *)thePickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component {
	[element setText:[titelStrings objectAtIndex:row]];
}

- (NSInteger)pickerView:(UIPickerView *)thePickerView numberOfRowsInComponent:(NSInteger)component {
	
	return [titelStrings count];
}

- (IBAction)clickBack:(id)sender
{
	[[[KundendatenViewController class] connect] orderBy:[self createOrder]];
	[self dismissModalViewControllerAnimated:YES];
}

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
